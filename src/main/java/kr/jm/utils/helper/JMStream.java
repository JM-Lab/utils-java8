package kr.jm.utils.helper;

import java.util.Collection;
import java.util.function.IntPredicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class JMStream {

	public static IntStream numberRange(int startInclusive, int endExclusive,
			int interval) {
		return numberRange(startInclusive, endExclusive, interval,
				n -> n < endExclusive);
	}

	public static IntStream numberRangeClosed(int startInclusive,
			int endInclusive, int interval) {
		return numberRange(startInclusive, endInclusive, interval,
				n -> n <= endInclusive);
	}

	private static IntStream numberRange(int start, int end, int interval,
			IntPredicate predicate) {
		return IntStream.iterate(start, n -> n + interval)
				.limit((end - start) / interval + 1).filter(predicate);
	}

	public static <N extends Number> IntStream buildIntStream(
			Collection<N> numberCollection) {
		return numberCollection.stream().mapToInt(Number::intValue);
	}

	public static <N extends Number> LongStream buildLongStream(
			Collection<N> numberCollection) {
		return numberCollection.stream().mapToLong(Number::longValue);
	}

	public static <N extends Number> DoubleStream buildDoubleStream(
			Collection<N> numberCollection) {
		return numberCollection.stream().mapToDouble(Number::doubleValue);
	}

}
