package net.ttdev.rinecore.land;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.SortedSet;
import java.util.TreeSet;

public final class RentManager {

    private final SortedSet<RentedLandChunk> rentedLandChunks = new TreeSet<>();

    public RentManager(JavaPlugin plugin) {
        new RentRunnable().runTaskTimer(plugin, RentRunnable.DELAY, RentRunnable.DELAY);
    }

    public void add(RentedLandChunk rentedLandChunk) {
        rentedLandChunks.add(rentedLandChunk);
    }

    public void remove(RentedLandChunk rentedLandChunk) {
        rentedLandChunks.remove(rentedLandChunk);
    }
}
