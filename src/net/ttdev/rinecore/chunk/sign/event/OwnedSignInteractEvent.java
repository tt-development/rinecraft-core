package net.ttdev.rinecore.chunk.sign.event;

import net.ttdev.rinecore.chunk.sign.OwnedSign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OwnedSignInteractEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private OwnedSign sign;
    private Player player;

    public OwnedSignInteractEvent(OwnedSign sign, Player player) {
        this.sign = sign;
        this.player = player;
    }

    public OwnedSign getSign() {
        return sign;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
