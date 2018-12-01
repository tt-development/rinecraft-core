package net.ttdev.rinecore.handler;

import net.ttdev.rinecore.chunk.LandType;
import net.ttdev.rinecore.chunk.event.LandCreateEvent;
import net.ttdev.rinecore.chunk.sign.BuySign;
import net.ttdev.rinecore.chunk.sign.RentSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public final class SignChangeEventHandler implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {

        if (event.getLines().length == 0) {
            return;
        }

        final Chunk chunk = event.getBlock().getChunk();

        if (RentSign.isValid(event.getLines())) {
            event.setLine(0, ChatColor.YELLOW + RentSign.HEADER);
            Bukkit.getServer().getPluginManager().callEvent(new LandCreateEvent(LandType.RENT, chunk, event.getPlayer()));
        }

        if (BuySign.isValid(event.getLines())) {
            event.setLine(0, ChatColor.GREEN + BuySign.HEADER);
            Bukkit.getServer().getPluginManager().callEvent(new LandCreateEvent(LandType.BUY, chunk, event.getPlayer()));
        }
    }
}
