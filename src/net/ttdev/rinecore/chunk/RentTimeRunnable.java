package net.ttdev.rinecore.chunk;

import net.ttdev.rinecore.Main;
import net.ttdev.rinecore.file.Serializer;
import net.ttdev.rinecore.util.FileDirectories;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.SortedSet;

public final class RentTimeRunnable extends BukkitRunnable {

    public static final int DELAY_SECONDS = 10;
    public static final int DELAY_TICKS = 20 * DELAY_SECONDS;

    @Override
    public void run() {

        final SortedSet<RentedChunk> rentedChunks = Main.getRentTimeManager().getRentedChunks();
        rentedChunks.forEach(chunk -> {

            chunk.changeDuration(-DELAY_SECONDS);

            if (!chunk.hasExpired()) return;

            final Player owner;
            try {
                owner = Bukkit.getPlayer(chunk.getOwner());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            owner.sendMessage(ChatColor.RED + "The rent duration for property " + chunk.getName() + " has expired.");
        });

        Serializer.saveChunks(FileDirectories.CHUNKS, rentedChunks);
    }
}
