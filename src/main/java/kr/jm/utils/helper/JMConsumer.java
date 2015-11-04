package kr.jm.utils.helper;

import java.util.function.Consumer;

public class JMConsumer {

	public static <T> Consumer<T> getSOPL() {
		return System.out::println;
	}

	public static <T> Consumer<T> getSOP() {
		return System.out::print;
	}

}
