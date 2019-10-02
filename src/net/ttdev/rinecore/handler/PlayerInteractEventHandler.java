package net.ttdev.rinecore.handler;

import net.ttdev.rinecore.chunk.OwnedLand;
import net.ttdev.rinecore.chunk.RentedLand;
import net.ttdev.rinecore.chunk.event.LandBuyEvent;
import net.ttdev.rinecore.chunk.event.LandRentEvent;
import net.ttdev.rinecore.chunk.sign.BuySign;
import net.ttdev.rinecore.chunk.sign.OwnedSign;
import net.ttdev.rinecore.chunk.sign.RentSign;
import net.ttdev.rinecore.chunk.sign.event.OwnedSignInteractEvent;
import net.ttdev.rinecore.player.RPlayer;
import net.ttdev.rinecore.util.ActionScheduler;
import net.ttdev.rinecore.util.Permissions;
import net.ttdev.rinecore.util.SignWrapper;
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
        //FIXME Possible that Material.LEGACY_SIGN_POST isn't the right type
        if (block.getType() != Material.LEGACY_SIGN_POST) {
            return;
        }

        final Player player = event.getPlayer();
        final Chunk chunk = block.getLocation().getChunk();

        Sign sign = (Sign) block.getState();

        if (OwnedSign.isValid(sign.getLines())) {
            Bukkit.getPluginManager().callEvent(new OwnedSignInteractEvent(new OwnedSign(sign), player));
        }

        if (RentSign.isValid(sign.getLines())) {

            final RentSign rentSign = new RentSign(sign.getLines());

            if (!player.hasPermission(Permissions.LAND_RENT)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return;
            }

            player.sendMessage("Renting property...");

            final RPlayer rPlayer = new RPlayer(player.getUniqueId());
            if (rPlayer.getBalance() < rentSign.getCost()) {
                ActionScheduler.sendMessageLater(ChatColor.RED + "You don't have enough money for this purchase.", ActionScheduler.TWO_SECONDS, player);
            } else if (rPlayer.ownsChunk(chunk)) {
                ActionScheduler.sendMessageLater(ChatColor.RED + "You already own this chunk.", ActionScheduler.TWO_SECONDS, player);
            } else if (rPlayer.ownsChunkWithName(rentSign.getName())) {
                ActionScheduler.sendMessageLater(ChatColor.RED + "You already own a chunk with this name.", ActionScheduler.TWO_SECONDS, player);
            } else {
                final RentedLand rentedChunk = new RentedLand(rPlayer.getUUID(), rentSign.getName(), chunk.getX(), chunk.getZ(), rentSign.getRentTime());
                rPlayer.addChunk(rentedChunk);
                rPlayer.changeBalance(-rentSign.getCost());
                Bukkit.getPluginManager().callEvent(new LandRentEvent(rentedChunk, player));

                ActionScheduler.doLater(() -> {

                    final SignWrapper signWrapper = new SignWrapper(sign);
                    signWrapper.setLines(ChatColor.RED + OwnedSign.HEADER, player.getName(), null, null);

                }, ActionScheduler.TWO_SECONDS);
            }
        }

        if (BuySign.isValid(sign.getLines())) {

            final BuySign buySign = new BuySign(sign.getLines());

            if (!player.hasPermission(Permissions.LAND_BUY)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return;
            }

            player.sendMessage("Buying property...");

            final RPlayer rPlayer = new RPlayer(player.getUniqueId());
            if (rPlayer.getBalance() < buySign.getCost()) {
                ActionScheduler.sendMessageLater(ChatColor.RED + "You don't have enough money for this purchase.", ActionScheduler.TWO_SECONDS, player);
            } else if (rPlayer.ownsChunk(chunk)) {
                ActionScheduler.sendMessageLater(ChatColor.RED + "You already own this chunk.", ActionScheduler.TWO_SECONDS, player);
            } else if (rPlayer.ownsChunkWithName(buySign.getName())) {
                ActionScheduler.sendMessageLater(ChatColor.RED + "You already own a chunk with this name.", ActionScheduler.TWO_SECONDS, player);
            } else {
                final OwnedLand ownedChunk = new OwnedLand(rPlayer.getUUID(), buySign.getName(), chunk.getX(), chunk.getZ());
                rPlayer.addChunk(ownedChunk);
                rPlayer.changeBalance(-buySign.getCost());
                Bukkit.getPluginManager().callEvent(new LandBuyEvent(ownedChunk, player));

                ActionScheduler.doLater(() -> {

                    final SignWrapper signWrapper = new SignWrapper(sign);
                    signWrapper.setLines(ChatColor.RED + OwnedSign.HEADER, player.getName(), null, null);

                }, ActionScheduler.TWO_SECONDS);
            }
        }
    }
}
