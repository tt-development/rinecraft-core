package net.ttdev.rinecore.chunk.sign;

import net.ttdev.rinecore.chunk.RentTime;
import org.bukkit.ChatColor;
import org.bukkit.event.block.SignChangeEvent;

public final class RentSign extends InteractiveSign<String, Integer, RentTime> {

    public RentSign(String... lines) throws UnsupportedSignException {
        super("[Rent]", String::toString, Integer::parseInt, RentTime::valueOf, lines);
    }

    public RentSign(SignChangeEvent event) throws UnsupportedSignException {
        this(event.getLines());

        event.setLine(0, ChatColor.YELLOW + super.header);
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
}
