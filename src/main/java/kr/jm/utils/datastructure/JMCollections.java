
package kr.jm.utils.datastructure;

import kr.jm.utils.helper.JMOptional;
import kr.jm.utils.helper.JMStream;
import kr.jm.utils.helper.JMString;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * The Class JMCollections.
 */
public class JMCollections {

	/**
	 * If not null or empty consume.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <C>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param consumer
	 *            the consumer
	 */
	public static <T, C extends Collection<T>> void
			ifNotNullOrEmptyConsume(C collection, Consumer<C> consumer) {
		JMOptional.getOptional(collection).ifPresent(consumer);
	}

	/**
	 * Checks if is not null or empty.
	 *
	 * @param collection
	 *            the collection
	 * @return true, if is not null or empty
	 */
	public static boolean isNotNullOrEmpty(Collection<?> collection) {
		return !isNullOrEmpty(collection);
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param collection
	 *            the collection
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}

	/**
	 * Gets the last.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <L>
	 *            the generic type
	 * @param list
	 *            the list
	 * @return the last
	 */
	public static <T, L extends List<T>> T getLast(List<T> list) {
		return isNullOrEmpty(list) ? null : list.get(list.size() - 1);
	}

	/**
	 * Sort.
	 *
	 * @param <T>
	 *            the generic type
	 * @param list
	 *            the list
	 * @return the list
	 */
	public static <T extends Comparable<T>> List<T> sort(List<T> list) {
		Collections.sort(list);
		return list;
	}

	/**
	 * Sort.
	 *
	 * @param <T>
	 *            the generic type
	 * @param list
	 *            the list
	 * @param comparator
	 *            the comparator
	 * @return the list
	 */
	public static <T> List<T> sort(List<T> list,
			Comparator<? super T> comparator) {
		list.sort(comparator);
		return list;
	}

	/**
	 * Builds the list.
	 *
	 * @param <E>
	 *            the element type
	 * @param objects
	 *            the objects
	 * @return the list
	 */
	@SafeVarargs
	public static <E> List<E> buildList(E... objects) {
		return Arrays.asList(objects);
	}

	/**
	 * Builds the list.
	 *
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @return the list
	 */
	public static <E> List<E> buildList(Iterable<E> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false)
				.collect(toList());
	}

	/**
	 * Builds the list from csv.
	 *
	 * @param csvString
	 *            the csv string
	 * @return the list
	 */
	public static List<String> buildListFromCsv(String csvString) {
		return buildList(JMArrays.buildArrayFromCsv(csvString));
	}

	/**
	 * Builds the list with delimiter.
	 *
	 * @param stringWithDelimiter
	 *            the string with delimiter
	 * @param delimiter
	 *            the delimiter
	 * @return the list
	 */
	public static List<String> buildListWithDelimiter(
			String stringWithDelimiter, String delimiter) {
		return JMStream
				.buildStream(
						new StringTokenizer(stringWithDelimiter, delimiter))
				.map(o -> (String) o).collect(toList());
	}

	/**
	 * Builds the list by line.
	 *
	 * @param stringByLine
	 *            the string by line
	 * @return the list
	 */
	public static List<String> buildListByLine(String stringByLine) {
		return buildListWithDelimiter(stringByLine, JMString.LINE_SEPARATOR);
	}

	/**
	 * Split into sub list.
	 *
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param targetSize
	 *            the target size
	 * @return the list
	 */
	public static <E> List<List<E>> splitIntoSubList(List<E> list,
			int targetSize) {
		int listSize = list.size();
		return JMStream.numberRange(0, listSize, targetSize)
				.mapToObj(index -> list.subList(index,
						Math.min(index + targetSize, listSize)))
				.collect(toList());
	}

	/**
	 * Gets the reversed.
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @return the reversed
	 */
	public static <T> List<T> getReversed(Collection<T> collection) {
		List<T> reversedList = new ArrayList<>(collection);
		Collections.reverse(reversedList);
		return reversedList;
	}

	/**
	 * Transform list.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <R>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param transformFunction
	 *            the transform function
	 * @return the list
	 */
	public static <T, R> List<R> transformList(Collection<T> collection,
			Function<T, R> transformFunction) {
		return collection.stream().map(transformFunction).collect(toList());
	}
}
