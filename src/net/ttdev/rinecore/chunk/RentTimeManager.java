package net.ttdev.rinecore.chunk;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Keeps track of all rented chunks on the server and decreasing
 * the time of each rent every ten seconds.
 */
public final class RentTimeManager {

    private final SortedSet<RentedChunk> rentedChunks = Collections.synchronizedSortedSet(new TreeSet<>());

    public RentTimeManager(JavaPlugin plugin) {
        new RentTimeRunnable().runTaskTimer(plugin, RentTimeRunnable.DELAY_TICKS, RentTimeRunnable.DELAY_TICKS);
    }

    protected SortedSet<RentedChunk> getRentedChunks() {
        return rentedChunks;
    }

    public void add(RentedChunk rentedChunk) {
        rentedChunks.add(rentedChunk);
    }

    public void remove(RentedChunk rentedChunk) {
        rentedChunks.remove(rentedChunk);
    }
}