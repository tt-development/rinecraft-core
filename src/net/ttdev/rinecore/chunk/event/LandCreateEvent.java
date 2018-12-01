package net.ttdev.rinecore.chunk.event;

import net.ttdev.rinecore.chunk.LandType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class LandCreateEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private LandType landType;
    private Player player;

    public LandCreateEvent(LandType landType, Player player) {
        this.landType = landType;
        this.player = player;
    }

    public LandType getLandType() {
        return landType;
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
