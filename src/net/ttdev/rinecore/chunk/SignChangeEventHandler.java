package net.ttdev.rinecore.chunk;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public final class SignChangeEventHandler implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {

        if (event.getLines().length == 0) {
            return;
        }

        final Player player = event.getPlayer();

        if (event.getLine(0).equals(InteractiveSign.RENT.getHeader())) {

            final String rentName;
            final Integer rentCost;
            final RentTime rentTime;

            try {
                rentName = InteractiveSign.RENT.getFirstParser().apply(event.getLine(1));
                rentCost = InteractiveSign.RENT.getSecondParser().apply(event.getLine(2));
                rentTime = InteractiveSign.RENT.getThirdParser().apply(event.getLine(3));
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(ChatColor.RED + "Error while creating sign.");
                player.sendMessage(ChatColor.RED + "Error: " + e.getMessage());
                return;
            }

            event.setLine(0, ChatColor.DARK_GRAY + event.getLine(0));

            player.sendMessage(ChatColor.GREEN + "Rent plot created:");
            player.sendMessage("Type: " + InteractiveSign.RENT.getHeader());
            player.sendMessage("Name: " + rentName);
            player.sendMessage("Time: " + rentTime.getId());
            player.sendMessage("Cost: " + rentCost);
        }

    }
}
