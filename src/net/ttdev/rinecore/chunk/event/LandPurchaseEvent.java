package net.ttdev.rinecore.chunk.event;

import net.ttdev.rinecore.chunk.AbstractLand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class LandPurchaseEvent<T extends AbstractLand> extends Event {

    private static HandlerList handlerList = new HandlerList();

    private T chunk;
    private Player player;

    LandPurchaseEvent(T chunk, Player player) {
        this.chunk = chunk;
        this.player = player;
    }

    public T getChunk() {
        return chunk;
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
