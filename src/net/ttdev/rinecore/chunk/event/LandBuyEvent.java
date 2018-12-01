package net.ttdev.rinecore.chunk.event;

import net.ttdev.rinecore.chunk.OwnedLand;
import org.bukkit.entity.Player;

public class LandBuyEvent extends LandPayEvent<OwnedLand> {

    public LandBuyEvent(OwnedLand chunk, Player player) {
        super(chunk, player);
    }
}
