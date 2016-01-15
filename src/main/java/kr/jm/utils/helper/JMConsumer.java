
package kr.jm.utils.helper;

import java.util.function.Consumer;

/**
 * The Class JMConsumer.
 */
public class JMConsumer {

	/**
	 * Gets the System.out.println Consumer.
	 *
	 * @param <T>
	 *            the generic type
	 * @return the sopl
	 */
	public static <T> Consumer<T> getSOPL() {
		return System.out::println;
	}

	/**
	 * Gets the System.out.print Consumer.
	 *
	 * @param <T>
	 *            the generic type
	 * @return the sop
	 */
	public static <T> Consumer<T> getSOP() {
		return System.out::print;
	}

	/**
	 * Gets the runnable Consumer that ignores the input parameter.
	 *
	 * @param <T>
	 *            the generic type
	 * @param runnable
	 *            the runnable
	 * @return the runnable
	 */
	public static <T> Consumer<T> getRunnable(Runnable runnable) {
		return t -> runnable.run();
	}

}
