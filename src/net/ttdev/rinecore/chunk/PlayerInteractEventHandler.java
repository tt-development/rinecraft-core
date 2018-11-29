package net.ttdev.rinecore.chunk;

import net.ttdev.rinecore.chunk.sign.RentSign;
import net.ttdev.rinecore.chunk.sign.UnsupportedSignException;
import net.ttdev.rinecore.player.RPlayer;
import net.ttdev.rinecore.util.MessageScheduler;
import org.bukkit.ChatColor;
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

        final Player player = event.getPlayer();

        player.sendMessage("Renting property...");

        final RPlayer rPlayer = new RPlayer(player.getUniqueId());
        if (rPlayer.getBalance() < rentSign.getCost()) {
            MessageScheduler.sendLater(ChatColor.RED + "You don't have enough money for this purchase.", MessageScheduler.TWO_SECONDS, player);
            return;
        }

        if (rPlayer.ownsChunk(block.getLocation().getChunk())) {
            MessageScheduler.sendLater(ChatColor.RED + "You already own this chunk.", MessageScheduler.TWO_SECONDS, player);
            return;
        }

        if (rPlayer.ownsChunkWithName(rentSign.getName())) {
            MessageScheduler.sendLater(ChatColor.RED + "You already own a chunk with this name.", MessageScheduler.TWO_SECONDS, player);
            return;
        }

        MessageScheduler.sendLater(ChatColor.GREEN + "Rent successful!", MessageScheduler.TWO_SECONDS, player);
    }
}
