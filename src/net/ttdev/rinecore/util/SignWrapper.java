package net.ttdev.rinecore.util;

import org.bukkit.block.Sign;

public final class SignWrapper {

    private Sign sign;

    public SignWrapper(Sign sign) {
        this.sign = sign;
    }

    public void setLines(String... lines) {

        for (int i = 0; i < lines.length; i++) {
            sign.setLine(i, lines[i]);
        }

        sign.update();
    }

    public void setLine(int index, String text) {
        sign.setLine(index, text);
        sign.update();
    }

    public String getLine(int index) {
        return sign.getLine(index);
    }
}
