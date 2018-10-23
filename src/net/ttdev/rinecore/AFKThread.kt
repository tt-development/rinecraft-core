package net.ttdev.rinecore

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

const val MAX_TIME = 300 // 5 minutes before flagged as AFK

class AFKThread : BukkitRunnable() {

    private val locationMap = mutableMapOf<UUID, Triple<Boolean, Location, Int>>()

    override fun run() {

        val players = Bukkit.getOnlinePlayers()

        players.forEach {
            /* Add any players not in the AFK check map
            and continue to the next iteration if the player
            was just added to the map
             */
            val previous = locationMap.putIfAbsent(it.uniqueId, Triple(false, it.location, 0)) ?: return

            /* Increase the number of seconds the player was AFK for by 1.
            * if the players seconds is greater than or equal to MAX_TIME
            * then flag them as AFK */
            if (it.location == previous.second) {

                val time = previous.third + 1

                locationMap[it.uniqueId] =
                        if (time >= MAX_TIME) Triple(true, it.location, MAX_TIME)
                        else Triple(false, it.location, time)
            }

        }
    }

}