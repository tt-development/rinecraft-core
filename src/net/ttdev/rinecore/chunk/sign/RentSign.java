package net.ttdev.rinecore.chunk.sign;

import net.ttdev.rinecore.chunk.RentTime;
import org.bukkit.ChatColor;

public final class RentSign extends InteractiveSign<String, Integer, RentTime> {

    public static final String HEADER = "[Rent]";

    public RentSign(String... lines) {
        super("[Rent]", String::toString, Integer::parseInt, RentTime::valueOf, lines);
    }

    public String getName() {
        return firstValue;
    }

    public int getCost() {
        return secondValue;
    }

    public RentTime getRentTime() {
        return thirdValue;
    }

    public static boolean isValid(String... lines) {
        return ChatColor.stripColor(lines[0]).equals(HEADER);
    }
}
