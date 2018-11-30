package net.ttdev.rinecore.chunk.sign;

public final class BuySign extends InteractiveSign<String, Integer, Void> {

    public BuySign(String... lines) throws UnsupportedSignException {
        super("[Buy]", String::toString, Integer::parseInt, null, lines);
    }

    public String getName() {
        return firstValue;
    }

    public int getCost() {
        return secondValue;
    }
}
