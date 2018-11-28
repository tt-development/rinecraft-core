package net.ttdev.rinecore.land;

import java.util.function.Function;

public final class InteractiveSign<T, U, V> {

    private final String header;
    private final Function<String, T> firstParser;
    private final Function<String, U> secondParser;
    private final Function<String, V> thirdParser;

    public InteractiveSign(String header,
                           Function<String, T> firstParser,
                           Function<String, U> secondParser,
                           Function<String, V> thirdParser) {
        this.header = header;
        this.firstParser = firstParser;
        this.secondParser = secondParser;
        this.thirdParser = thirdParser;
    }

    public String getHeader() {
        return header;
    }

    public Function<String, T> getFirstParser() {
        return firstParser;
    }

    public Function<String, U> getSecondParser() {
        return secondParser;
    }

    public Function<String, V> getThirdParser() {
        return thirdParser;
    }
}
