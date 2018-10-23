package net.ttdev.rinecore.playtime

import getMap
import net.ttdev.rinecore.main
import setMap
import java.util.*

/**
 * Get the list of playtimes from the config.yml.
 * Each entry in the list looks like this:
 * `UUID->PLAYTIME (in seconds)`
 */
fun getPlaytimeEntries() = main.config.getMap<UUID, Long>("playtimes", { UUID.fromString(it) }, { it.toString().toLong() })

/**
 * Save `entries` to configuration, replacing
 * all previous existing entries.
 */
fun setPlaytimeEntries(entries: MutableMap<UUID, Long>) {
    val remapped = mutableMapOf<String, Long>()
    entries.mapKeysTo(remapped) { it.key.toString() }
    main.config.setMap("playtimes", remapped)
    main.saveConfig()
}

/**
 * All numbers are returned as their superscript
 * counterparts.
 * E.g. 1234 becomes ¹²³⁴
 */
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