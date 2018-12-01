package net.ttdev.rinecore.chunk.sign;

import org.bukkit.ChatColor;

public final class BuySign extends InteractiveSign<String, Integer, Void> {

    public static final String HEADER = "[Buy]";

    public BuySign(String... lines) {
        super(HEADER, String::toString, Integer::parseInt, null, lines);
    }

    public String getName() {
        return firstValue;
    }

    public int getCost() {
        return secondValue;
    }

    public static boolean isValid(String... lines) {
        return ChatColor.stripColor(lines[0]).equals(HEADER);
    }
}
