package net.ttdev.rinecore.chunk.sign;

import java.util.function.Function;

/**
 * A sign that can be right-clicked. An
 * interactive sign must contain a header describing
 * the type of sing it is, and three <code>Function</code>'s
 * that can convert the sign lines into their preferred types
 * for easy use.
 *
 * @param <T>
 * @param <U>
 * @param <V>
 */
public abstract class InteractiveSign<T, U, V> {

    protected final T firstValue;
    protected final U secondValue;
    protected final V thirdValue;

    InteractiveSign(String header, Function<String, T> firstParser, Function<String, U> secondParser, Function<String, V> thirdParser, String... lines) throws UnsupportedSignException {

        if (!lines[0].equals(header)) throw new UnsupportedSignException();

        this.firstValue = firstParser.apply(lines[1]);
        this.secondValue = secondParser.apply(lines[2]);
        this.thirdValue = thirdParser.apply(lines[3]);
    }
}
