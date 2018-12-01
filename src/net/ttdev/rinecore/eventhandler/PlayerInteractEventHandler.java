package net.ttdev.rinecore.eventhandler;

import net.ttdev.rinecore.chunk.OwnedLand;
import net.ttdev.rinecore.chunk.RentedLand;
import net.ttdev.rinecore.chunk.event.LandBuyEvent;
import net.ttdev.rinecore.chunk.event.LandRentEvent;
import net.ttdev.rinecore.chunk.sign.BuySign;
import net.ttdev.rinecore.chunk.sign.RentSign;
import net.ttdev.rinecore.chunk.sign.UnsupportedSignException;
import net.ttdev.rinecore.player.RPlayer;
import net.ttdev.rinecore.util.MessageScheduler;
import net.ttdev.rinecore.util.Permissions;
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

public final class PlayerInteractEventHandler implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block.getType() != Material.SIGN_POST) {
            return;
        }

        final Player player = event.getPlayer();
        final Chunk chunk = block.getLocation().getChunk();

        Sign sign = (Sign) block.getState();

        final RentSign rentSign;
        try {

            rentSign = new RentSign(sign.getLines());

            if (!player.hasPermission(Permissions.LAND_RENT)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return;
            }

            player.sendMessage("Renting property...");

            final RPlayer rPlayer = new RPlayer(player.getUniqueId());
            if (rPlayer.getBalance() < rentSign.getCost()) {
                MessageScheduler.sendLater(ChatColor.RED + "You don't have enough money for this purchase.", MessageScheduler.TWO_SECONDS, player);
            } else if (rPlayer.ownsChunk(chunk)) {
                MessageScheduler.sendLater(ChatColor.RED + "You already own this chunk.", MessageScheduler.TWO_SECONDS, player);
            } else if (rPlayer.ownsChunkWithName(rentSign.getName())) {
                MessageScheduler.sendLater(ChatColor.RED + "You already own a chunk with this name.", MessageScheduler.TWO_SECONDS, player);
            } else {
                final RentedLand rentedChunk = new RentedLand(rPlayer.getUUID(), rentSign.getName(), chunk.getX(), chunk.getZ(), rentSign.getRentTime());
                rPlayer.addChunk(rentedChunk);
                rPlayer.changeBalance(-rentSign.getCost());
                Bukkit.getPluginManager().callEvent(new LandRentEvent(rentedChunk, player));
            }
        } catch (UnsupportedSignException e) { }

        final BuySign buySign;
        try {

            buySign = new BuySign(sign.getLines());

            if (!player.hasPermission(Permissions.LAND_BUY)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return;
            }

            player.sendMessage("Buying property...");

            final RPlayer rPlayer = new RPlayer(player.getUniqueId());
            if (rPlayer.getBalance() < buySign.getCost()) {
                MessageScheduler.sendLater(ChatColor.RED + "You don't have enough money for this purchase.", MessageScheduler.TWO_SECONDS, player);
            } else if (rPlayer.ownsChunk(chunk)) {
                MessageScheduler.sendLater(ChatColor.RED + "You already own this chunk.", MessageScheduler.TWO_SECONDS, player);
            } else if (rPlayer.ownsChunkWithName(buySign.getName())) {
                MessageScheduler.sendLater(ChatColor.RED + "You already own a chunk with this name.", MessageScheduler.TWO_SECONDS, player);
            } else {
                final OwnedLand ownedChunk = new OwnedLand(rPlayer.getUUID(), buySign.getName(), chunk.getX(), chunk.getZ());
                rPlayer.addChunk(ownedChunk);
                rPlayer.changeBalance(-buySign.getCost());
                Bukkit.getPluginManager().callEvent(new LandBuyEvent(ownedChunk, player));
            }
        } catch (UnsupportedSignException e) { }
    }
}
