package net.ttdev.rinecore.chunk;

import net.ttdev.rinecore.chunk.event.LandExpireEvent;
import net.ttdev.rinecore.file.Serializer;
import net.ttdev.rinecore.player.RPlayer;
import net.ttdev.rinecore.util.FileDirectories;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public final class RentTimeDeductor extends BukkitRunnable {

    public static final int DELAY_SECONDS = 10;
    public static final int DELAY_TICKS = 20 * DELAY_SECONDS;

    @Override
    public void run() {

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {

            final RPlayer rPlayer = new RPlayer(player.getUniqueId());
            final Collection<RentedLand> rentedChunks = rPlayer.getRentedLandChunks();

            rentedChunks.forEach(chunk -> {
                chunk.changeDuration(-DELAY_SECONDS);
                if (chunk.hasExpired()) Bukkit.getServer().getPluginManager().callEvent(new LandExpireEvent(chunk));
            });

            Serializer.saveChunks(rentedChunks, FileDirectories.CHUNKS);
        }
    }
}
