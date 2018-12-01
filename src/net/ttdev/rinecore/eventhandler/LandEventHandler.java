package net.ttdev.rinecore.eventhandler;

import net.ttdev.rinecore.chunk.AbstractLand;
import net.ttdev.rinecore.chunk.event.LandBuyEvent;
import net.ttdev.rinecore.chunk.event.LandCreateEvent;
import net.ttdev.rinecore.chunk.event.LandExpireEvent;
import net.ttdev.rinecore.chunk.event.LandRentEvent;
import net.ttdev.rinecore.util.MessageScheduler;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class LandEventHandler implements Listener {

    @EventHandler
    public void onChunkCreate(LandCreateEvent event) {

        final Location chunkY = event.getSignLocation().subtract(0, 1, 0);
        final Chunk chunk = chunkY.getChunk();
        final World world = chunk.getWorld();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = chunkY.getBlockY() - 10; y < chunkY.getBlockY(); y++) {
                    final int chunkX = (chunk.getX() * 16) + x;
                    final int chunkZ = (chunk.getZ() * 16) + z;
                    final Location location = new Location(world, chunkX, y, chunkZ);
                    location.getBlock().setType(Material.STONE);
                }
            }
        }

        event.getPlayer().sendMessage(event.getLandType() + " plot created.");
    }

    @EventHandler
    public void onChunkRent(LandRentEvent event) {
        MessageScheduler.sendLater(ChatColor.GREEN + "Property rent successful!", MessageScheduler.TWO_SECONDS, event.getPlayer());
    }

    @EventHandler
    public void onChunkBuy(LandBuyEvent event) {
        MessageScheduler.sendLater(ChatColor.GREEN + "Property purchase successful!", MessageScheduler.TWO_SECONDS, event.getPlayer());
    }

    @EventHandler
    public void onChunkExpire(LandExpireEvent event) {

        final AbstractLand chunk = event.getChunk();

        final Player owner;
        try {
            owner = Bukkit.getPlayer(chunk.getOwner());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        owner.sendMessage(ChatColor.RED + "The rent duration for property " + chunk.getName() + " has expired.");
    }
}
