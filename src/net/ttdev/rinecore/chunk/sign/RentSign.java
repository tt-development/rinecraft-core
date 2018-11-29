package net.ttdev.rinecore.chunk.sign;

import net.ttdev.rinecore.chunk.RentTime;

public class RentSign extends InteractiveSign<String, Integer, RentTime> {

    public RentSign(String... lines) throws UnsupportedSignException {
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
}
