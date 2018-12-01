package net.ttdev.rinecore

import net.ttdev.rinecore.Main.getPlaytimeEntries
import net.ttdev.rinecore.Main.setPlaytimeEntries
import org.bukkit.entity.Player
import java.time.Duration


var Player.playtime: Long
    get() = getPlaytimeSeconds()
    set(value) = setPlaytimeSeconds(value)

/**
 * The players playtime in seconds.
 */
fun Player.getPlaytimeSeconds(): Long {
    /* If the entry exists, then return the playtime
    * associated with that entry, otherwise return 0
    * indicating that there is no entry for that UUID */
    val entries = getPlaytimeEntries()

    if (entries[uniqueId] == null) {
        println("entry is null")
        entries[uniqueId] = 0
    }

    return entries[uniqueId] ?: 0
}

/**
 * Set the playtime for this player in seconds.
 */
fun Player.setPlaytimeSeconds(seconds: Long) {
    val entries = getPlaytimeEntries()

    /* Create a new entry for the player with the new
    * playtime */
    entries[uniqueId] = seconds

    /* Replace players old entry with new entry
     */
    setPlaytimeEntries(entries)
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