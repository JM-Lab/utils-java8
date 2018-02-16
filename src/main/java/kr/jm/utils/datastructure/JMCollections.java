
package kr.jm.utils.datastructure;

import kr.jm.utils.helper.JMOptional;
import kr.jm.utils.helper.JMStream;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static kr.jm.utils.helper.JMStream.buildTokenStream;
import static kr.jm.utils.helper.JMString.LINE_SEPARATOR;

/**
 * The Class JMCollections.
 */
public class JMCollections {

    /**
     * If not null or getEmptyStringArray consume.
     *
     * @param <T>        the generic type
     * @param <C>        the generic type
     * @param collection the collection
     * @param consumer   the consumer
     */
    public static <T, C extends Collection<T>> void
    ifNotNullOrEmptyConsume(C collection, Consumer<C> consumer) {
        JMOptional.getOptional(collection).ifPresent(consumer);
    }

    /**
     * Checks if is not null or getEmptyStringArray.
     *
     * @param collection the collection
     * @return true, if is not null or getEmptyStringArray
     */
    public static boolean isNotNullOrEmpty(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    /**
     * Checks if is null or getEmptyStringArray.
     *
     * @param collection the collection
     * @return true, if is null or getEmptyStringArray
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * Gets the last.
     *
     * @param <T>  the generic type
     * @param <L>  the generic type
     * @param list the list
     * @return the last
     */
    public static <T, L extends List<T>> T getLast(List<T> list) {
        return isNullOrEmpty(list) ? null : list.get(list.size() - 1);
    }

    /**
     * Sort.
     *
     * @param <T>  the generic type
     * @param list the list
     * @return the list
     */
    public static <T extends Comparable<T>> List<T> sort(List<T> list) {
        Collections.sort(list);
        return list;
    }

    /**
     * Sort.
     *
     * @param <T>        the generic type
     * @param list       the list
     * @param comparator the comparator
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
     * @param <E>     the element type
     * @param objects the objects
     * @return the list
     */
    @SafeVarargs
    public static <E> List<E> buildList(E... objects) {
        return Arrays.asList(objects);
    }

    /**
     * Builds the list.
     *
     * @param <E>      the element type
     * @param iterable the iterable
     * @return the list
     */
    public static <E> List<E> buildList(Iterable<E> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(toList());
    }

    /**
     * Build merged list list.
     *
     * @param <E>         the type parameter
     * @param collection1 the collection 1
     * @param collection2 the collection 2
     * @return the list
     */
    public static <E> List<E> buildMergedList(
            Collection<E> collection1, Collection<E> collection2) {
        List<E> mergedList = new ArrayList<>(collection1);
        mergedList.addAll(collection2);
        return mergedList;
    }

    /**
     * Builds the list from csv.
     *
     * @param csvString the csv string
     * @return the list
     */
    public static List<String> buildListFromCsv(String csvString) {
        return buildList(JMArrays.buildArrayFromCsv(csvString));
    }

    /**
     * Builds the list with delimiter.
     *
     * @param stringWithDelimiter the string with delimiter
     * @param delimiter           the delimiter
     * @return the list
     */
    public static List<String> buildListWithDelimiter(
            String stringWithDelimiter, String delimiter) {
        return buildTokenStream(stringWithDelimiter, delimiter)
                .collect(toList());
    }

    /**
     * Builds the list by line.
     *
     * @param stringByLine the string by line
     * @return the list
     */
    public static List<String> buildListByLine(String stringByLine) {
        return buildListWithDelimiter(stringByLine, LINE_SEPARATOR);
    }

    /**
     * Split into sub list.
     *
     * @param <E>        the element type
     * @param list       the list
     * @param targetSize the target size
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
     * @param <T>        the generic type
     * @param collection the collection
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
     * @param <T>               the generic type
     * @param <R>               the generic type
     * @param collection        the collection
     * @param transformFunction the transform function
     * @return the list
     */
    public static <T, R> List<R> transformList(Collection<T> collection,
            Function<T, R> transformFunction) {
        return collection.stream().map(transformFunction).collect(toList());
    }

    /**
     * New synchronized list list.
     *
     * @param <T> the type parameter
     * @return the list
     */
    public static <T> List<T> newSynchronizedList() {
        return Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * New set set.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @return the set
     */
    public static <T> Set<T> newSet(Collection<T> collection) {
        return new HashSet<>(collection);
    }

    /**
     * New list list.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @return the list
     */
    public static <T> List<T> newList(Collection<T> collection) {
        return new ArrayList<>(collection);
    }

    /**
     * New map map.
     *
     * @param <K>        the type parameter
     * @param <V>        the type parameter
     * @param collection the collection
     * @return the map
     */
    public static <K, V> Map<K, V> newMap(
            Collection<Map.Entry<K, V>> collection) {
        return collection.stream()
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * New map map.
     *
     * @param <K>     the type parameter
     * @param <V>     the type parameter
     * @param entries the entries
     * @return the map
     */
    @SafeVarargs
    public static <K, V> Map<K, V> newMap(Map.Entry<K, V>... entries) {
        return newMap(Arrays.asList(entries));
    }
}
