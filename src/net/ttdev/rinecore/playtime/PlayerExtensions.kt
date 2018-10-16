package net.ttdev.rinecore.playtime

import org.bukkit.entity.Player
import java.time.Duration


var Player.playtime: Long
get() = getPlaytimeSeconds()
set(value) = setPlaytimeSeconds(value)

/**
 * The players playtime in seconds.
 */
fun Player.getPlaytimeSeconds(): Long {
    val playTimeList = getPlaytimeEntries()
    val filteredList = playTimeList.filter { it.contains(uniqueId.toString()) }

    /* this player doesn't have a playtime yet even though they're
        playing on the server. that's not right, so return -1 to
        indicate an error has occurred.
         */
    if (filteredList.isEmpty()) return -1

    return filteredList[0].split("->")[1].toLong()
}

fun Player.setPlaytimeSeconds(seconds: Long) {
    val playtimeList = getPlaytimeEntries()
    val filteredList = playtimeList.filter { it.contains(uniqueId.toString()) }

    if (filteredList.isEmpty()) {
        playtimeList.add("$uniqueId->0")
        setPlaytimeEntries(playtimeList)
        return
    }

    val newEntry = "$uniqueId->$seconds"
    playtimeList.removeAll(filteredList) // Remove old entries
    playtimeList.add(newEntry)
    setPlaytimeEntries(playtimeList)
}

/**
 * Get the playtime of a player in hours.
 * This performs a conversion of `Player.playtime`
 * from seconds to hours, then returns the result.
 */
fun Player.getPlaytimeHours(): Long {
    return Duration.ofSeconds(getPlaytimeSeconds()).toHours()
}

/**
 * The formatted playtime is in
 * `HOUR hours, MINUTE minutes` format.
 * Seconds are discarded and aren't displayed.
 */
fun Player.getFormattedPlaytime(): String {
    var seconds = getPlaytimeSeconds()
    val hours = seconds / (60 * 60)
    seconds -= hours * (60 * 60)
    val minutes = seconds / 60
    return "$hours hours, and $minutes minutes"
}