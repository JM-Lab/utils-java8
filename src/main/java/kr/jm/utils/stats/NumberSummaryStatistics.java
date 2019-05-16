package kr.jm.utils.stats;

import java.util.*;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.*;

/**
 * The type Number summary statistics.
 */
public class NumberSummaryStatistics {

    private final List<Number> numberList;
    private int oldSize;
    private DoubleSummaryStatistics doubleSummaryStatistics;

    private NumberSummaryStatistics() {
        this.numberList = Collections.synchronizedList(new ArrayList<>());
        this.oldSize = -1;
    }

    /**
     * Of number summary statistics.
     *
     * @return the number summary statistics
     */
    public static NumberSummaryStatistics of() {
        return new NumberSummaryStatistics();
    }

    /**
     * Of number summary statistics.
     *
     * @param numberCollection the number collection
     * @return the number summary statistics
     */
    public static NumberSummaryStatistics of(
            Collection<Number> numberCollection) {
        return of().addAll(numberCollection);
    }

    /**
     * Add all number summary statistics.
     *
     * @param numberCollection the number collection
     * @return the number summary statistics
     */
    public NumberSummaryStatistics addAll(Collection<Number> numberCollection) {
        this.numberList.addAll(numberCollection);
        return this;
    }

    /**
     * Add all number summary statistics.
     *
     * @param <N>          the type parameter
     * @param numberStream the number stream
     * @return the number summary statistics
     */
    public <N extends Number> NumberSummaryStatistics addAll(
            Stream<N> numberStream) {
        return addAll(numberStream.collect(toList()));
    }

    /**
     * Add all number summary statistics.
     *
     * @param ints the ints
     * @return the number summary statistics
     */
    public NumberSummaryStatistics addAll(int[] ints) {
        return addAll(IntStream.of(ints).boxed());
    }

    /**
     * Add all number summary statistics.
     *
     * @param longs the longs
     * @return the number summary statistics
     */
    public NumberSummaryStatistics addAll(long[] longs) {
        return addAll(LongStream.of(longs).boxed());
    }

    /**
     * Add all number summary statistics.
     *
     * @param doubles the doubles
     * @return the number summary statistics
     */
    public NumberSummaryStatistics addAll(double[] doubles) {
        return addAll(DoubleStream.of(doubles).boxed());
    }

    /**
     * Gets double summary statistics.
     *
     * @return the double summary statistics
     */
    public DoubleSummaryStatistics getDoubleSummaryStatistics() {
        return getDoubleSummaryStatistics(this.numberList.size());
    }

    private DoubleSummaryStatistics getDoubleSummaryStatistics(int size) {
        return this.oldSize != size ? initDoubleSummaryStatistics(
                size) : this.doubleSummaryStatistics;
    }

    private DoubleSummaryStatistics initDoubleSummaryStatistics(int size) {
        this.oldSize = size;
        return this.doubleSummaryStatistics = getDoubleStream()
                .summaryStatistics();
    }

    private DoubleStream getDoubleStream() {
        return this.numberList.stream().mapToDouble(Number::doubleValue);
    }

    /**
     * Gets sum.
     *
     * @return the sum
     */
    public Number getSum() {
        return getDoubleSummaryStatistics().getSum();
    }

    /**
     * Gets min.
     *
     * @return the min
     */
    public Number getMin() {
        return getDoubleSummaryStatistics().getMin();
    }

    /**
     * Gets max.
     *
     * @return the max
     */
    public Number getMax() {
        return getDoubleSummaryStatistics().getMax();
    }

    /**
     * Gets average.
     *
     * @return the average
     */
    public Number getAverage() {
        return getDoubleSummaryStatistics().getAverage();
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public Number getCount() {
        return getDoubleSummaryStatistics().getCount();
    }

    /**
     * Gets number list.
     *
     * @return the number list
     */
    public List<Number> getNumberList() {
        return unmodifiableList(this.numberList);
    }

    /**
     * Combine number summary statistics.
     *
     * @param other the other
     * @return the number summary statistics
     */
    public NumberSummaryStatistics combine(NumberSummaryStatistics other) {
        return addAll(other.numberList);
    }

    /**
     * Gets stats.
     *
     * @param statsField the stats field
     * @return the stats
     */
    public Number getStats(StatsField statsField) {
        return statsField.calStats(getDoubleSummaryStatistics());
    }

    /**
     * Gets stats.
     *
     * @param statsFieldString the stats field string
     * @return the stats
     */
    public Number getStats(String statsFieldString) {
        return getStats(StatsField.valueOf(statsFieldString));
    }

    @Override
    public String toString() {
        return String.format(
                "%s{count=%d, sum=%f, min=%f, max=%f, average=%f}",
                this.getClass().getSimpleName(), getCount().longValue(),
                getSum().doubleValue(), getMin().doubleValue(),
                getMax().doubleValue(), getAverage().doubleValue());
    }

    /**
     * Gets stats field map.
     *
     * @return the stats field map
     */
    public Map<StatsField, Number> getStatsFieldMap() {
        return Arrays.stream(StatsField.values())
                .collect(toMap(Function.identity(), this::getStats));
    }
}
