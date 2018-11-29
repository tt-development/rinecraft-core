package net.ttdev.rinecore.chunk;

import net.ttdev.rinecore.Main;
import net.ttdev.rinecore.util.FileDirectories;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.SortedSet;

public final class RentTimeRunnable extends BukkitRunnable {

    public static final int DELAY_SECONDS = 10;
    public static final int DELAY_TICKS = 20 * DELAY_SECONDS;

    @Override
    public void run() {

        final SortedSet<RentedChunk> rentedChunks = Main.getRentTimeManager().getRentedChunks();
        rentedChunks.forEach(landChunk -> landChunk.adjustTime(-DELAY_SECONDS));

        Serializer.saveChunks(FileDirectories.LAND_CHUNKS, rentedChunks);
    }
}
