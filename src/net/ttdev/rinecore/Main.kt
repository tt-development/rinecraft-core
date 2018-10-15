package net.ttdev.rinecore

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.time.Duration

/**
 * This is the main class. Currently all the code
 * in here belongs to the playtime component of
 * RineCore and should be moved to another location
 * as soon as possible.
 */
class Main : JavaPlugin(), Listener {

    override fun onEnable() {
        saveDefaultConfig()

        server.pluginManager.registerEvents(this, this)

        /* create a task to increase the players playtime by
        1 every second.
         */
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach { it.playtime += 1 }
            }
        }.runTaskTimer(this, 20, 20)

        logger.info("$name enabled.")
    }

    override fun onDisable() {
        logger.info("$name disabled.")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (label != "playtime") {
            return false
        }

        /* if the sender doesn't provide any arguments they
         * are checking their own playtime. */
        if (args.isEmpty()) {
            if (sender !is Player) return true
            sender.sendMessage("Playtime: ${sender.getFormattedPlaytime()}.")
        } else {
            /* if the sender provides 1 argument it's assumed
             * they're checking the playtime of another player. */
            val player = Bukkit.getPlayer(args[0])
            sender.sendMessage(
                    if (player == null) "Couldn't find ${args[0]}."
                    else "${player.name}'s playtime: ${player.getFormattedPlaytime()}."
            )
        }

        return true
    }

    fun getPlaytimeList(): MutableList<String> {
        return config.getStringList("playtimes")
    }

    fun setPlaytimes(playtimes: MutableList<String>) {
        config.set("playtimes", playtimes)
        saveConfig()
    }

    fun Player.getPlaytimeHours(): Long {
        return Duration.ofSeconds(playtime).toHours()
    }

    fun Player.getFormattedPlaytime(): String {
        var seconds = playtime
        val hours = seconds / (60 * 60)
        seconds -= hours * (60 * 60)
        val minutes = seconds / 60
        return "$hours hours, and $minutes minutes"
    }

    fun superscriptNumbers(numbers: Long): String {
        val superscriptMap = mapOf(
                '1' to '\u00B9',
                '2' to '\u00B2',
                '3' to '\u00B3',
                '4' to '\u2074',
                '5' to '\u2075',
                '6' to '\u2076',
                '7' to '\u2077',
                '8' to '\u2078',
                '9' to '\u2079',
                '0' to '\u2070'
        )

        var newSequence = numbers.toString()
        for ((k, v) in superscriptMap) {
            newSequence = newSequence.replace(k, v)
        }

        return newSequence
    }

    var Player.playtime: Long
        get() {
            val playTimeList = getPlaytimeList()
            val filteredList = playTimeList.filter { it.contains(uniqueId.toString()) }

            /* this player doesn't have a playtime yet even though they're
            playing on the server. that's not right, so return -1 to
            indicate an error has occurred.
             */
            if (filteredList.isEmpty()) return -1

            return filteredList[0].split("->")[1].toLong()
        }
        set(value) {
            val playtimeList = getPlaytimeList()
            val filteredList = playtimeList.filter { it.contains(uniqueId.toString()) }

            if (filteredList.isEmpty()) {
                playtimeList.add("$uniqueId->0")
                setPlaytimes(playtimeList)
                return
            }

            val newEntry = "$uniqueId->$value"
            playtimeList.removeAll(filteredList) // Remove old entries
            playtimeList.add(newEntry)
            setPlaytimes(playtimeList)
        }

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        event.apply {
            format = "${player.displayName}${superscriptNumbers(player.getPlaytimeHours())}: $message"
        }
    }

}