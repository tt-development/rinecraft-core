package net.ttdev.rinecore.chunk.sign;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

public class OwnedSign extends InteractiveSign<String, Void, Void> {

    public static final String HEADER = "[Owned]";

    public OwnedSign(String... lines) {
        super(HEADER, String::toString, null, null, lines);
    }

    public OwnedSign(Sign sign) {
        this(sign.getLines());
    }

    public String getOwner() {
        return firstValue;
    }

    public static boolean isValid(String... lines) {
        return ChatColor.stripColor(lines[0]).equals(HEADER);
    }
}
