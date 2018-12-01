package net.ttdev.rinecore.handler;

import net.ttdev.rinecore.chunk.sign.event.OwnedSignInteractEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LandSignEventHandler implements Listener {

    @EventHandler
    public void onOwnedSignInteract(OwnedSignInteractEvent event) {
        event.getPlayer().sendMessage(ChatColor.RED + "This property is owned by " + event.getSign().getOwner() + ".");
    }
}
