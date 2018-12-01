package net.ttdev.rinecore.chunk.event;

import net.ttdev.rinecore.chunk.OwnedLand;
import net.ttdev.rinecore.chunk.sign.InteractiveSign;
import org.bukkit.entity.Player;

public class LandBuyEvent extends LandPurchaseEvent<OwnedLand> {

    private InteractiveSign sign;

    public LandBuyEvent(OwnedLand chunk, InteractiveSign sign, Player player) {
        super(chunk, player);
        this.sign = sign;
    }

    public InteractiveSign getSign() {
        return sign;
    }
}
