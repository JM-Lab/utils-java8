
package kr.jm.utils.helper;

import java.util.function.Consumer;

/**
 * The Class JMConsumer.
 */
public class JMConsumer {

    /**
     * Gets the System.out.println Consumer.
     *
     * @param <T> the generic type
     * @return the sopl
     */
    public static <T> Consumer<T> getSOPL() {
		return System.out::println;
	}

    /**
     * Gets the System.out.print Consumer.
     *
     * @param <T> the generic type
     * @return the sop
     */
    public static <T> Consumer<T> getSOP() {
		return System.out::print;
	}

    /**
     * Gets the consumer.
     *
     * @param <T>      the generic type
     * @param runnable the runnable
     * @return the consumer
     */
    public static <T> Consumer<T> getConsumer(Runnable runnable) {
		return t -> runnable.run();
	}

    /**
     * Gets the consumer.
     *
     * @param <T>            the generic type
     * @param returnConsumer the return consumer
     * @return the consumer
     */
    public static <T> Consumer<T> getConsumer(Consumer<T> returnConsumer) {
		return returnConsumer;
	}

}
