
package kr.jm.utils.helper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.function.IntPredicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import kr.jm.utils.datastructure.JMCollections;

/**
 * The Class JMStream.
 */
public class JMStream {

	/**
	 * Number range.
	 *
	 * @param startInclusive
	 *            the start inclusive
	 * @param endExclusive
	 *            the end exclusive
	 * @param interval
	 *            the interval
	 * @return the int stream
	 */
	public static IntStream numberRange(int startInclusive, int endExclusive,
			int interval) {
		return numberRange(startInclusive, endExclusive, interval,
				n -> n < endExclusive);
	}

	/**
	 * Number range closed.
	 *
	 * @param startInclusive
	 *            the start inclusive
	 * @param endInclusive
	 *            the end inclusive
	 * @param interval
	 *            the interval
	 * @return the int stream
	 */
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

	/**
	 * Builds the int stream.
	 *
	 * @param <N>
	 *            the number type
	 * @param numberCollection
	 *            the number collection
	 * @return the int stream
	 */
	public static <N extends Number> IntStream
			buildIntStream(Collection<N> numberCollection) {
		return numberCollection.stream().mapToInt(Number::intValue);
	}

	/**
	 * Builds the long stream.
	 *
	 * @param <N>
	 *            the number type
	 * @param numberCollection
	 *            the number collection
	 * @return the long stream
	 */
	public static <N extends Number> LongStream
			buildLongStream(Collection<N> numberCollection) {
		return numberCollection.stream().mapToLong(Number::longValue);
	}

	/**
	 * Builds the double stream.
	 *
	 * @param <N>
	 *            the number type
	 * @param numberCollection
	 *            the number collection
	 * @return the double stream
	 */
	public static <N extends Number> DoubleStream
			buildDoubleStream(Collection<N> numberCollection) {
		return numberCollection.stream().mapToDouble(Number::doubleValue);
	}

	/**
	 * Gets the reversed stream.
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @return the reversed stream
	 */
	public static <T> Stream<T> getReversedStream(Collection<T> collection) {
		return JMCollections.getReversed(collection).stream();
	}

	/**
	 * Builds the stream.
	 *
	 * @param <T>
	 *            the generic type
	 * @param array
	 *            the array
	 * @return the stream
	 */
	public static <T> Stream<T> buildStream(T[] array) {
		return Arrays.stream(array);
	}

	/**
	 * Builds the stream.
	 *
	 * @param <T>
	 *            the generic type
	 * @param iterable
	 *            the iterable
	 * @return the stream
	 */
	public static <T> Stream<T> buildStream(Iterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}

	/**
	 * Builds the stream.
	 *
	 * @param <T>
	 *            the generic type
	 * @param iterator
	 *            the iterator
	 * @return the stream
	 */
	public static <T> Stream<T> buildStream(Iterator<T> iterator) {
		return buildStream(() -> iterator);
	}

	/**
	 * Builds the stream.
	 *
	 * @param <T>
	 *            the generic type
	 * @param enumeration
	 *            the enumeration
	 * @return the stream
	 */
	public static <T> Stream<T> buildStream(Enumeration<T> enumeration) {
		return buildStream(new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return enumeration.hasMoreElements();
			}

			@Override
			public T next() {
				return enumeration.nextElement();
			}
		});
	}

}
