
package kr.jm.utils.helper;

import java.util.function.Consumer;

/**
 * The type Jm consumer.
 */
public class JMConsumer {

    /**
     * Gets sopl.
     *
     * @param <T> the type parameter
     * @return the sopl
     */
    public static <T> Consumer<T> getSOPL() {
		return System.out::println;
	}

    /**
     * Gets sop.
     *
     * @param <T> the type parameter
     * @return the sop
     */
    public static <T> Consumer<T> getSOP() {
		return System.out::print;
	}

    /**
     * Gets consumer.
     *
     * @param <T>      the type parameter
     * @param runnable the runnable
     * @return the consumer
     */
    public static <T> Consumer<T> getConsumer(Runnable runnable) {
		return t -> runnable.run();
	}

    /**
     * Gets consumer.
     *
     * @param <T>            the type parameter
     * @param returnConsumer the return consumer
     * @return the consumer
     */
    public static <T> Consumer<T> getConsumer(Consumer<T> returnConsumer) {
		return returnConsumer;
	}

}
