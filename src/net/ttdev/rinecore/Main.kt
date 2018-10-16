package net.ttdev.rinecore

import net.ttdev.rinecore.playtime.getFormattedPlaytime
import net.ttdev.rinecore.playtime.getPlaytimeHours
import net.ttdev.rinecore.playtime.startPlaytimeClock
import net.ttdev.rinecore.playtime.superscriptNumbers
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * This is the main class. Currently all the code
 * in here belongs to the playtime component of
 * RineCore and should be moved to another location
 * as soon as possible.
 */

lateinit var main: Main

class Main : JavaPlugin(), Listener {

    override fun onEnable() {
        main = this
        saveDefaultConfig()
        server.pluginManager.registerEvents(this, this)
        startPlaytimeClock()
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

    /**
     * Changes the format of a player chat message
     * to append the playtime as superscripted numbers
     * in front of the players displayname.
     */
    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        event.apply {
            format = "${player.displayName}${superscriptNumbers(player.getPlaytimeHours())}: $message"
        }
    }

}