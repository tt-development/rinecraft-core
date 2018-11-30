package net.ttdev.rinecore.chunk.event;

import net.ttdev.rinecore.chunk.AbstractChunk;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChunkExpireEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private AbstractChunk chunk;

    public ChunkExpireEvent(AbstractChunk chunk) {
        this.chunk = chunk;
    }

    public AbstractChunk getChunk() {
        return chunk;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
