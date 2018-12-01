package net.ttdev.rinecore.eventhandler;

import net.ttdev.rinecore.chunk.LandType;
import net.ttdev.rinecore.chunk.event.LandCreateEvent;
import net.ttdev.rinecore.chunk.sign.BuySign;
import net.ttdev.rinecore.chunk.sign.RentSign;
import net.ttdev.rinecore.chunk.sign.UnsupportedSignException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public final class SignChangeEventHandler implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {

        if (event.getLines().length == 0) {
            return;
        }

        try {
            new RentSign(event.getLines());
            final Location location = event.getBlock().getLocation();
            Bukkit.getServer().getPluginManager().callEvent(new LandCreateEvent(LandType.RENT, location, event.getPlayer()));
        } catch (UnsupportedSignException e) { }

        try {
            new BuySign(event.getLines());
            final Location location = event.getBlock().getLocation();
            Bukkit.getServer().getPluginManager().callEvent(new LandCreateEvent(LandType.BUY, location, event.getPlayer()));
        } catch (UnsupportedSignException e) { }

    }
}
