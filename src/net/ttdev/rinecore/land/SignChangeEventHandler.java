package net.ttdev.rinecore.land;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public final class SignChangeEventHandler implements Listener {

    private final InteractiveSign<String, Integer, RentTime> rentSign =
            new InteractiveSign<>("[Rent]", String::toString, Integer::parseInt, RentTime::valueOf);

    @EventHandler
    public void onSignChange(SignChangeEvent event) {

        final Player player = event.getPlayer();

        if (event.getLine(0).equals(rentSign.getHeader())) {

            final String rentName;
            final Integer rentCost;
            final RentTime rentTime;

            try {
                rentName = rentSign.getFirstParser().apply(event.getLine(1));
                rentCost = rentSign.getSecondParser().apply(event.getLine(2));
                rentTime = rentSign.getThirdParser().apply(event.getLine(3));
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(ChatColor.RED + "Error while creating sign.");
                player.sendMessage(ChatColor.RED + "Error: " + e.getMessage());
                return;
            }

            event.setLine(0, ChatColor.DARK_GRAY + event.getLine(0));

            player.sendMessage(ChatColor.GREEN + "Rent plot created:");
            player.sendMessage("Type: " + rentSign.getHeader());
            player.sendMessage("Name: " + rentName);
            player.sendMessage("Time: " + rentTime.getId());
            player.sendMessage("Cost: " + rentCost);
        }

    }
}
