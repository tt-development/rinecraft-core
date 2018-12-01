package net.ttdev.rinecore.chunk.event;

import net.ttdev.rinecore.chunk.RentedLand;
import org.bukkit.entity.Player;

public final class LandRentEvent extends LandPayEvent<RentedLand> {

    public LandRentEvent(RentedLand chunk, Player player) {
        super(chunk, player);
    }
}
