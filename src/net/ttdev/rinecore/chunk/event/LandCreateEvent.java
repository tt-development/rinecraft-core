package net.ttdev.rinecore.chunk.event;

import net.ttdev.rinecore.chunk.LandType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LandCreateEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private LandType landType;
    private Location signLocation;
    private Player player;

    public LandCreateEvent(LandType landType, Location signLocation, Player player) {
        this.landType = landType;
        this.signLocation = signLocation;
        this.player = player;
    }

    public LandType getLandType() {
        return landType;
    }

    public Location getSignLocation() {
        return signLocation;
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
