package net.ttdev.rinecore.chunk;

import net.ttdev.rinecore.api.sign.RentSignCreatedEvent;
import net.ttdev.rinecore.chunk.sign.RentSign;
import net.ttdev.rinecore.chunk.sign.UnsupportedSignException;

import org.bukkit.Bukkit;
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

        final RentSign rentSign;
        try {
            rentSign = new RentSign(event.getLines());
        } catch (UnsupportedSignException e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.RED + "Error while creating sign.");
            player.sendMessage(ChatColor.RED + "Error: " + e.getMessage());
            return;
        }
        
        // RentSignCreatedEvent
        RentSignCreatedEvent rentSignCreatedEvent = new RentSignCreatedEvent(rentSign, player);
        Bukkit.getPluginManager().callEvent(rentSignCreatedEvent);
        
        if (rentSignCreatedEvent.isCancelled()) {
        	event.setCancelled(true);
        	return;
        }

        player.sendMessage(ChatColor.GREEN + "Rent plot created:");
        player.sendMessage("Name: " + rentSign.getName());
        player.sendMessage("Cost: " + rentSign.getCost());
        player.sendMessage("Time: " + rentSign.getRentTime());

    }
}
