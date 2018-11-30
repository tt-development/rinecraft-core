package net.ttdev.rinecore.eventhandler;

import net.ttdev.rinecore.chunk.OwnedChunk;
import net.ttdev.rinecore.chunk.RentedChunk;
import net.ttdev.rinecore.chunk.sign.BuySign;
import net.ttdev.rinecore.chunk.sign.RentSign;
import net.ttdev.rinecore.chunk.sign.UnsupportedSignException;
import net.ttdev.rinecore.player.RPlayer;
import net.ttdev.rinecore.util.MessageScheduler;
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
                MessageScheduler.sendLater(ChatColor.GREEN + "Property rent successful!", MessageScheduler.TWO_SECONDS, player);
            }
        } catch (UnsupportedSignException e) { }

        final BuySign buySign;
        try {

            buySign = new BuySign(sign.getLines());

            player.sendMessage("Buying property...");

            final RPlayer rPlayer = new RPlayer(player.getUniqueId());
            if (rPlayer.getBalance() < buySign.getCost()) {
                MessageScheduler.sendLater(ChatColor.RED + "You don't have enough money for this purchase.", MessageScheduler.TWO_SECONDS, player);
            } else if (rPlayer.ownsChunk(chunk)) {
                MessageScheduler.sendLater(ChatColor.RED + "You already own this chunk.", MessageScheduler.TWO_SECONDS, player);
            } else if (rPlayer.ownsChunkWithName(buySign.getName())) {
                MessageScheduler.sendLater(ChatColor.RED + "You already own a chunk with this name.", MessageScheduler.TWO_SECONDS, player);
            } else {
                rPlayer.addChunk(new OwnedChunk(rPlayer.getUUID(), buySign.getName(), chunk.getX(), chunk.getZ()));
                rPlayer.changeBalance(-buySign.getCost());
                MessageScheduler.sendLater(ChatColor.GREEN + "Property purchase successful!", MessageScheduler.TWO_SECONDS, player);
            }
        } catch (UnsupportedSignException e) { }
    }
}
