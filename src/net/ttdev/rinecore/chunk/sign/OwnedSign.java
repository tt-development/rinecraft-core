package net.ttdev.rinecore.chunk.sign;

import org.bukkit.block.Sign;

public class OwnedSign extends InteractiveSign<String, Void, Void> {

    public OwnedSign(String... lines) throws UnsupportedSignException {
        super("[Owned]", String::toString, null, null, lines);
    }

    public OwnedSign(Sign sign) throws UnsupportedSignException {
        this(sign.getLines());
    }

    public String getOwner() {
        return firstValue;
    }
}
