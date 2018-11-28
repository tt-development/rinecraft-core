package net.ttdev.rinecore.land;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public final class RentTimeManager {

    private final SortedSet<RentedLandChunk> rentedLandChunks = Collections.synchronizedSortedSet(new TreeSet<>());

    public RentTimeManager(JavaPlugin plugin) {
        new RentTimeRunnable().runTaskTimer(plugin, RentTimeRunnable.DELAY_TICKS, RentTimeRunnable.DELAY_TICKS);
    }

    protected SortedSet<RentedLandChunk> getRentedLandChunks() {
        return rentedLandChunks;
    }

    public void add(RentedLandChunk rentedLandChunk) {
        rentedLandChunks.add(rentedLandChunk);
    }

    public void remove(RentedLandChunk rentedLandChunk) {
        rentedLandChunks.remove(rentedLandChunk);
    }
}
