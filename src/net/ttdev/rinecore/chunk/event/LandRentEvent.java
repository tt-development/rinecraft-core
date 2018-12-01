package net.ttdev.rinecore.chunk.event;

import net.ttdev.rinecore.chunk.RentedLand;
import net.ttdev.rinecore.chunk.sign.InteractiveSign;
import org.bukkit.entity.Player;

public class LandRentEvent extends LandPurchaseEvent<RentedLand> {

    private InteractiveSign sign;

    public LandRentEvent(RentedLand chunk, InteractiveSign sign, Player player) {
        super(chunk, player);
        this.sign = sign;
    }

    public InteractiveSign getSign() {
        return sign;
    }
}
