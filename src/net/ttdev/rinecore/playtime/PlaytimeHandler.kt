package net.ttdev.rinecore.playtime

import net.ttdev.rinecore.main
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

/* create a task to increase the players playtime by
   1 every second. */
fun startPlaytimeClock() {
    object : BukkitRunnable() {
        override fun run() {
            Bukkit.getOnlinePlayers().forEach { it.playtime += 1 }
        }
    }.runTaskTimer(main, 20, 20)
}
