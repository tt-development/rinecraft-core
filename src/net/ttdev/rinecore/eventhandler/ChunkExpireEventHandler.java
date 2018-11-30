package net.ttdev.rinecore.eventhandler;

import net.ttdev.rinecore.chunk.AbstractChunk;
import net.ttdev.rinecore.chunk.event.ChunkExpireEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChunkExpireEventHandler implements Listener {

    @EventHandler
    public void onChunkExpire(ChunkExpireEvent event) {

        final AbstractChunk chunk = event.getChunk();

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
