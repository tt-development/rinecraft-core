package net.ttdev.rinecore.event;

import net.ttdev.rinecore.chunk.sign.BuySign;
import net.ttdev.rinecore.chunk.sign.RentSign;
import net.ttdev.rinecore.chunk.sign.UnsupportedSignException;
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
            player.sendMessage(ChatColor.GREEN + "Rent plot " + rentSign.getName() + " created.");
            return;
        } catch (UnsupportedSignException e) { }

        final BuySign buySign;
        try {
            buySign = new BuySign(event.getLines());
            player.sendMessage(ChatColor.GREEN + "Buy plot " + buySign.getName() + " created.");
        } catch (UnsupportedSignException e) { }

    }
}
