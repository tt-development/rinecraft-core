package net.ttdev.rinecore.chunk;

import java.util.function.Function;

/**
 * A sign that can be right-clicked. An
 * interactive sign must contain a header describing
 * the type of sing it is, and three <code>Function</code>'s
 * that can convert the sign lines into their preferred types
 * for easy use.
 * @param <T>
 * @param <U>
 * @param <V>
 */
public final class InteractiveSign<T, U, V> {

    public static final InteractiveSign<String, Integer, RentTime> RENT =
            new InteractiveSign<>("[Rent]", String::toString, Integer::parseInt, RentTime::valueOf);

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
