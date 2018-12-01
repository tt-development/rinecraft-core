package net.ttdev.rinecore.eventhandler;

import net.ttdev.rinecore.chunk.AbstractLand;
import net.ttdev.rinecore.chunk.event.LandBuyEvent;
import net.ttdev.rinecore.chunk.event.LandCreateEvent;
import net.ttdev.rinecore.chunk.event.LandExpireEvent;
import net.ttdev.rinecore.chunk.event.LandRentEvent;
import net.ttdev.rinecore.util.MessageScheduler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class LandEventHandler implements Listener {

    @EventHandler
    public void onLandCreate(LandCreateEvent event) {
        event.getPlayer().sendMessage(event.getLandType() + " plot created.");
    }

    @EventHandler
    public void onLandRent(LandRentEvent event) {
        MessageScheduler.sendLater(ChatColor.GREEN + "Property rent successful!", MessageScheduler.TWO_SECONDS, event.getPlayer());
    }

    @EventHandler
    public void onLandBuy(LandBuyEvent event) {
        MessageScheduler.sendLater(ChatColor.GREEN + "Property purchase successful!", MessageScheduler.TWO_SECONDS, event.getPlayer());
    }

    @EventHandler
    public void onLandExpire(LandExpireEvent event) {

        final AbstractLand chunk = event.getChunk();

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
