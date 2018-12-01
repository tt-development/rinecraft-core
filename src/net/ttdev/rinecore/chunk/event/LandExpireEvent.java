package net.ttdev.rinecore.chunk.event;

import net.ttdev.rinecore.chunk.AbstractLand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LandExpireEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private AbstractLand chunk;

    public LandExpireEvent(AbstractLand chunk) {
        this.chunk = chunk;
    }

    public AbstractLand getChunk() {
        return chunk;
    }

    public Player getOwner() {
        return Bukkit.getPlayer(chunk.getOwner());
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
