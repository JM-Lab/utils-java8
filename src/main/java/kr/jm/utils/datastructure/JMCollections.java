package kr.jm.utils.datastructure;

import kr.jm.utils.helper.JMOptional;
import kr.jm.utils.helper.JMStream;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;
import static kr.jm.utils.helper.JMStream.buildTokenStream;
import static kr.jm.utils.helper.JMString.LINE_SEPARATOR;

/**
 * The type Jm collections.
 */
public class JMCollections {

    /**
     * If not null or empty consume.
     *
     * @param <T>        the type parameter
     * @param <C>        the type parameter
     * @param collection the collection
     * @param consumer   the consumer
     */
    public static <T, C extends Collection<T>> void
    ifNotNullOrEmptyConsume(C collection, Consumer<C> consumer) {
        JMOptional.getOptional(collection).ifPresent(consumer);
    }

    /**
     * Is not null or empty boolean.
     *
     * @param collection the collection
     * @return the boolean
     */
    public static boolean isNotNullOrEmpty(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    /**
     * Is null or empty boolean.
     *
     * @param collection the collection
     * @return the boolean
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * Gets last.
     *
     * @param <T>  the type parameter
     * @param <L>  the type parameter
     * @param list the list
     * @return the last
     */
    public static <T, L extends List<T>> T getLast(List<T> list) {
        return isNullOrEmpty(list) ? null : list.get(list.size() - 1);
    }

    /**
     * Sort list.
     *
     * @param <T>  the type parameter
     * @param list the list
     * @return the list
     */
    public static <T extends Comparable<T>> List<T> sort(List<T> list) {
        Collections.sort(list);
        return list;
    }

    /**
     * Sort list.
     *
     * @param <T>        the type parameter
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
     * Build list list.
     *
     * @param <E>     the type parameter
     * @param objects the objects
     * @return the list
     */
    @SafeVarargs
    public static <E> List<E> buildList(E... objects) {
        return Arrays.asList(objects);
    }

    /**
     * Build list list.
     *
     * @param <E>      the type parameter
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
     * Build list from csv list.
     *
     * @param csvString the csv string
     * @return the list
     */
    public static List<String> buildListFromCsv(String csvString) {
        return buildList(JMArrays.buildArrayFromCsv(csvString));
    }

    /**
     * Build list with delimiter list.
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
     * Build list by line list.
     *
     * @param stringByLine the string by line
     * @return the list
     */
    public static List<String> buildListByLine(String stringByLine) {
        return buildListWithDelimiter(stringByLine, LINE_SEPARATOR);
    }

    /**
     * Split into sub list list.
     *
     * @param <E>        the type parameter
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
     * Gets reversed.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @return the reversed
     */
    public static <T> List<T> getReversed(Collection<T> collection) {
        List<T> reversedList = new ArrayList<>(collection);
        Collections.reverse(reversedList);
        return reversedList;
    }

    /**
     * Transform list list.
     *
     * @param <T>               the type parameter
     * @param <R>               the type parameter
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

    /**
     * Add and get t.
     *
     * @param <T>        the type parameter
     * @param collection the collection
     * @param item       the item
     * @return the t
     */
    public static <T> T addAndGet(Collection<T> collection, T item) {
        collection.add(item);
        return item;
    }

    /**
     * Build new list list.
     *
     * @param <T>               the type parameter
     * @param <R>               the type parameter
     * @param collection        the collection
     * @param transformFunction the transform function
     * @return the list
     */
    public static <T, R> List<R> buildNewList(Collection<T> collection,
            Function<T, R> transformFunction) {
        return collection.stream().map(transformFunction)
                .collect(Collectors.toList());
    }

    /**
     * New map map.
     *
     * @param <K>     the type parameter
     * @param <V>     the type parameter
     * @param initMap the init map
     * @return the map
     */
    public static <K, V> Map<K, V> newMap(Map<K, V> initMap) {
        return new HashMap<>(initMap);
    }
}
