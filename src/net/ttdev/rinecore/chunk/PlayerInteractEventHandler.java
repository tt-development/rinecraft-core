package net.ttdev.rinecore.chunk;

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

        if (sign.getLines().length == 0) {
            return;
        }

        final Player player = event.getPlayer();

        if (sign.getLine(0).equals(InteractiveSign.RENT.getHeader())) {

            player.sendMessage("Attempting to rent property...");

            //TODO Do balance check and other things.

            MessageScheduler.sendLater(ChatColor.GREEN + "Rent successful!", player, 20 * 2);
        }
    }
}
