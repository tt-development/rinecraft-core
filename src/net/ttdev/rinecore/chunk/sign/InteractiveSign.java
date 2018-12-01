package net.ttdev.rinecore.chunk.sign;

import java.util.function.Function;

/**
 * A sign that can be right-clicked. An
 * interactive sign must contain a header describing
 * the type of sign it is, and three <code>Function</code>'s
 * that can convert the sign lines into their preferred types
 * for easy use.
 *
 * @param <A>
 * @param <B>
 * @param <C>
 */
public abstract class InteractiveSign<A, B, C> {

    final A firstValue;
    final B secondValue;
    final C thirdValue;

    InteractiveSign(String header, Function<String, A> firstParser, Function<String, B> secondParser, Function<String, C> thirdParser, String... lines) throws UnsupportedSignException {

        if (!lines[0].equals(header)) throw new UnsupportedSignException();

        firstValue = firstParser != null ? firstParser.apply(lines[1]) : null;
        secondValue = secondParser != null ? secondParser.apply(lines[2]) : null;
        thirdValue = thirdParser != null ? thirdParser.apply(lines[3]) : null;
    }
}
