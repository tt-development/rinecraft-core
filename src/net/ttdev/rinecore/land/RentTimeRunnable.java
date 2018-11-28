package net.ttdev.rinecore.land;

import net.ttdev.rinecore.Main;
import net.ttdev.rinecore.util.FileDirectories;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.SortedSet;

public class RentTimeRunnable extends BukkitRunnable {

    public static final int DELAY_SECONDS = 10;
    public static final int DELAY_TICKS = 20 * DELAY_SECONDS;

    @Override
    public void run() {

        final SortedSet<RentedLandChunk> rentedLandChunks = Main.getRentTimeManager().getRentedLandChunks();
        rentedLandChunks.forEach(landChunk -> landChunk.adjustTimeLeft(-DELAY_SECONDS));

        Serializer.serializeLandChunks(FileDirectories.LAND_CHUNKS, rentedLandChunks);
    }
}
