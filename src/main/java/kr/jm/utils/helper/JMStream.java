package kr.jm.utils.helper;

import kr.jm.utils.datastructure.JMCollections;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.IntPredicate;
import java.util.stream.*;

/**
 * The Class JMStream.
 */
public class JMStream {

    /**
     * Number range.
     *
     * @param startInclusive the start inclusive
     * @param endExclusive   the end exclusive
     * @param interval       the interval
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
     * @param startInclusive the start inclusive
     * @param endInclusive   the end inclusive
     * @param interval       the interval
     * @return the int stream
     */
    public static IntStream numberRangeClosed(int startInclusive,
            int endInclusive, int interval) {
        return numberRange(startInclusive, endInclusive, interval,
                n -> n <= endInclusive);
    }

    private static IntStream numberRange(int start, int end, int interval,
            IntPredicate predicate) {
        int maxSize = (end - start) / interval + 1;
        return IntStream.iterate(start, n -> n + interval).limit(maxSize)
                .filter(predicate);
    }

    /**
     * Number range with count.
     *
     * @param start    the start
     * @param interval the interval
     * @param count    the count
     * @return the double stream
     */
    public static DoubleStream numberRangeWithCount(float start, float interval,
            int count) {
        return DoubleStream.iterate(start, n -> n + interval).limit(count);
    }

    /**
     * Number range with count.
     *
     * @param start    the start
     * @param interval the interval
     * @param count    the count
     * @return the long stream
     */
    public static LongStream numberRangeWithCount(int start, int interval,
            int count) {
        return LongStream.iterate(start, n -> n + interval).limit(count);
    }

    /**
     * Builds the int stream.
     *
     * @param <N>              the number type
     * @param numberCollection the number collection
     * @return the int stream
     */
    public static <N extends Number> IntStream
    buildIntStream(Collection<N> numberCollection) {
        return numberCollection.stream().mapToInt(Number::intValue);
    }

    /**
     * Builds the long stream.
     *
     * @param <N>              the number type
     * @param numberCollection the number collection
     * @return the long stream
     */
    public static <N extends Number> LongStream
    buildLongStream(Collection<N> numberCollection) {
        return numberCollection.stream().mapToLong(Number::longValue);
    }

    /**
     * Builds the double stream.
     *
     * @param <N>              the number type
     * @param numberCollection the number collection
     * @return the double stream
     */
    public static <N extends Number> DoubleStream
    buildDoubleStream(Collection<N> numberCollection) {
        return numberCollection.stream().mapToDouble(Number::doubleValue);
    }

    /**
     * Builds the reversed stream.
     *
     * @param <T>        the generic type
     * @param collection the collection
     * @return the stream
     */
    public static <T> Stream<T> buildReversedStream(Collection<T> collection) {
        return JMCollections.getReversed(collection).stream();
    }

    /**
     * Builds the stream.
     *
     * @param <T>   the generic type
     * @param array the array
     * @return the stream
     */
    @SafeVarargs
    public static <T> Stream<T> buildStream(T... array) {
        return Arrays.stream(array);
    }

    /**
     * Builds the stream.
     *
     * @param <T>      the generic type
     * @param iterable the iterable
     * @return the stream
     */
    public static <T> Stream<T> buildStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    /**
     * Builds the stream.
     *
     * @param <T>      the generic type
     * @param iterator the iterator
     * @return the stream
     */
    public static <T> Stream<T> buildStream(Iterator<T> iterator) {
        return buildStream(() -> iterator);
    }

    /**
     * Builds the stream.
     *
     * @param <T>         the generic type
     * @param enumeration the enumeration
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

    public static Stream<String> buildTokenStream(String text,
            String delimiter) {
        return JMStream.buildStream(delimiter == null ? new StringTokenizer(
                text) : new StringTokenizer(text, delimiter))
                .map(o -> (String) o);
    }

    public static Stream<String> buildTokenStream(String text) {
        return buildTokenStream(text, null);
    }

    /**
     * Builds the stream.
     *
     * @param <T>        the generic type
     * @param isParallel the is parallel
     * @param collection the collection
     * @return the stream
     */
    public static <T> Stream<T> buildStream(boolean isParallel,
            Collection<T> collection) {
        return isParallel ? collection.parallelStream() : collection.stream();
    }

    /**
     * Builds the concat stream.
     *
     * @param <T>     the generic type
     * @param sample1 the sample 1
     * @param sample2 the sample 2
     * @return the stream
     */
    public static <T> Stream<T> buildConcatStream(List<T> sample1,
            List<T> sample2) {
        return Stream.concat(sample1.stream(), sample2.stream());
    }

    /**
     * Builds the random number stream.
     *
     * @param count the count
     * @return the double stream
     */
    public static DoubleStream buildRandomNumberStream(int count) {
        return IntStream.range(0, count).mapToDouble(i -> Math.random());
    }

    public static <T> Stream<T> buildCollectionAsOptStream(
            Optional<Collection<T>> collectionAsOpt) {
        return collectionAsOpt.map(Collection::stream).orElseGet(Stream::empty);
    }

    public static <K, V> Stream<Entry<K, V>> buildMapStream(
            Optional<Map<K, V>> mapAsOpt) {
        return buildCollectionAsOptStream(mapAsOpt.map(Map::entrySet));
    }

}
