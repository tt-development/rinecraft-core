package net.ttdev.rinecore.chunk;

import net.ttdev.rinecore.api.sign.RentSignClickedEvent;
import net.ttdev.rinecore.chunk.sign.RentSign;
import net.ttdev.rinecore.chunk.sign.UnsupportedSignException;
import net.ttdev.rinecore.player.RPlayer;
import net.ttdev.rinecore.util.MessageScheduler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractEventHandler implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block.getType() != Material.SIGN_POST) {
            return;
        }
        
        Sign sign = (Sign) block.getState();

        final RentSign rentSign;
        try {
            rentSign = new RentSign(sign.getLines());
        } catch (UnsupportedSignException e) {
            e.printStackTrace();
            return;
        }
        
        // RentSignClickedEvent
        final Player player = event.getPlayer();
        RentSignClickedEvent rentSignClickedEvent = new RentSignClickedEvent(rentSign, player);
        Bukkit.getPluginManager().callEvent(rentSignClickedEvent);
        
        if (rentSignClickedEvent.isCancelled()) {
        	event.setCancelled(true);
        	return;
        }

        final Chunk chunk = block.getLocation().getChunk();

        player.sendMessage("Renting property...");

        final RPlayer rPlayer = new RPlayer(player.getUniqueId());

        if (rPlayer.getBalance() < rentSign.getCost()) {
            MessageScheduler.sendLater(ChatColor.RED + "You don't have enough money for this purchase.", MessageScheduler.TWO_SECONDS, player);
        } else if (rPlayer.ownsChunk(chunk)) {
            MessageScheduler.sendLater(ChatColor.RED + "You already own this chunk.", MessageScheduler.TWO_SECONDS, player);
        } else if (rPlayer.ownsChunkWithName(rentSign.getName())) {
            MessageScheduler.sendLater(ChatColor.RED + "You already own a chunk with this name.", MessageScheduler.TWO_SECONDS, player);
        } else {
            rPlayer.addChunk(new RentedChunk(rPlayer.getUUID(), rentSign.getName(), chunk.getX(), chunk.getZ(), rentSign.getRentTime()));
            rPlayer.changeBalance(-rentSign.getCost());
            MessageScheduler.sendLater(ChatColor.GREEN + "Rent successful!", MessageScheduler.TWO_SECONDS, player);
        }
    }
}
