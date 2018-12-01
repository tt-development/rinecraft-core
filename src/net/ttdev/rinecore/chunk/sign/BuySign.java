package net.ttdev.rinecore.chunk.sign;

import org.bukkit.ChatColor;
import org.bukkit.event.block.SignChangeEvent;

public final class BuySign extends InteractiveSign<String, Integer, Void> {

    public BuySign(String... lines) throws UnsupportedSignException {
        super("[Buy]", String::toString, Integer::parseInt, null, lines);
    }

    public BuySign(SignChangeEvent event) throws UnsupportedSignException {
        this(event.getLines());

        event.setLine(0, ChatColor.GREEN + super.header);
    }

    public String getName() {
        return firstValue;
    }

    public int getCost() {
        return secondValue;
    }
}
