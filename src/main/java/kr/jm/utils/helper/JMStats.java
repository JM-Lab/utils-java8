package kr.jm.utils.helper;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.enums.StatsField;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * The Class JMStats.
 */
public class JMStats {

    /**
     * Cal stats.
     *
     * @param statsString       the stats string
     * @param summaryStatistics the summary statistics
     * @return the number
     */
    public static Number calStats(String statsString,
            IntSummaryStatistics summaryStatistics) {
        return StatsField.valueOfAlias(statsString).calStats(summaryStatistics);
    }

    /**
     * Cal stats.
     *
     * @param statsString       the stats string
     * @param summaryStatistics the summary statistics
     * @return the number
     */
    public static Number calStats(String statsString,
            LongSummaryStatistics summaryStatistics) {
        return StatsField.valueOfAlias(statsString).calStats(summaryStatistics);
    }

    /**
     * Cal stats.
     *
     * @param statsString       the stats string
     * @param summaryStatistics the summary statistics
     * @return the number
     */
    public static Number calStats(String statsString,
            DoubleSummaryStatistics summaryStatistics) {
        return StatsField.valueOfAlias(statsString).calStats(summaryStatistics);
    }

    /**
     * Cal stats.
     *
     * @param statsString  the stats string
     * @param numberStream the number stream
     * @return the number
     */
    public static Number calStats(String statsString, IntStream numberStream) {
        return StatsField.valueOfAlias(statsString).calStats(numberStream);
    }

    /**
     * Cal stats.
     *
     * @param statsString  the stats string
     * @param numberStream the number stream
     * @return the number
     */
    public static Number calStats(String statsString, LongStream numberStream) {
        return StatsField.valueOfAlias(statsString).calStats(numberStream);
    }

    /**
     * Cal stats.
     *
     * @param statsString  the stats string
     * @param numberStream the number stream
     * @return the number
     */
    public static Number calStats(String statsString,
            DoubleStream numberStream) {
        return StatsField.valueOfAlias(statsString).calStats(numberStream);
    }

    /**
     * Gets the percentile value.
     *
     * @param targetPercentile    the target percentile
     * @param unorderedNumberList the unordered number list
     * @return the percentile value
     */
    public static Number getPercentileValue(int targetPercentile,
            List<Number> unorderedNumberList) {
        return getPercentileValueWithSorted(targetPercentile,
                sortedDoubleList(unorderedNumberList));
    }

    private static Double getPercentileValueWithSorted(int targetPercentile,
            List<Double> sortedNumberList) {
        return getPercentileValue(targetPercentile, sortedNumberList.size(),
                sortedNumberList);
    }

    /**
     * Gets the percentile value map.
     *
     * @param targetPercentileList the target percentile list
     * @param numberList           the number list
     * @return the percentile value map
     */
    public static Map<Integer, Number> getPercentileValueMap(
            List<Integer> targetPercentileList, List<Number> numberList) {
        List<Double> sortedDoubleList = sortedDoubleList(numberList);
        return targetPercentileList.stream()
                .collect(toMap(percentile -> percentile,
                        percentile -> getPercentileValueWithSorted(percentile,
                                sortedDoubleList)));
    }

    private static double getPercentileValue(int targetPercentile, int size,
            List<Double> sortedList) {
        int index = size * adjustTargetPercentile(targetPercentile) / 100;
        return index == 0 ? 0d : sortedList.get(index - 1);
    }

    private static List<Double> sortedDoubleList(List<Number> numberList) {
        return JMStream.buildDoubleStream(numberList).sorted().boxed()
                .collect(toList());
    }

    private static int adjustTargetPercentile(int targetPercentile) {
        return targetPercentile < 0 ? 0
                : targetPercentile > 100 ? 100 : targetPercentile;
    }

    /**
     * Min.
     *
     * @param <N>        the number type
     * @param numberList the number list
     * @return the number
     */
    public static <N extends Number> Number min(List<N> numberList) {
        return JMCollections.isNullOrEmpty(numberList) ? 0
                : numberList.get(0) instanceof Integer
                ? numberList.stream().mapToInt(Number::intValue).min()
                .orElse(0)
                : numberList.get(0) instanceof Long
                ? numberList.stream()
                .mapToLong(Number::longValue).min()
                .orElse(0)
                : numberList.stream()
                .mapToDouble(Number::doubleValue).min()
                .orElse(0);
    }

    /**
     * Max.
     *
     * @param <N>        the number type
     * @param numberList the number list
     * @return the number
     */
    public static <N extends Number> Number max(List<N> numberList) {
        return JMCollections.isNullOrEmpty(numberList) ? 0
                : numberList.get(0) instanceof Integer
                ? numberList.stream().mapToInt(Number::intValue).max()
                .orElse(0)
                : numberList.get(0) instanceof Long
                ? numberList.stream()
                .mapToLong(Number::longValue).max()
                .orElse(0)
                : numberList.stream()
                .mapToDouble(Number::doubleValue).max()
                .orElse(0);
    }

    /**
     * Count.
     *
     * @param numberList the number list
     * @return the number
     */
    public static Number count(List<?> numberList) {
        return numberList.size();
    }

    /**
     * Sum.
     *
     * @param <N>        the number type
     * @param numberList the number list
     * @return the number
     */
    public static <N extends Number> Number sum(List<N> numberList) {
        return JMCollections.isNullOrEmpty(numberList) ? 0
                : numberList.get(0) instanceof Integer
                ? numberList.stream().mapToInt(Number::intValue).sum()
                : numberList.get(0) instanceof Long
                ? numberList.stream()
                .mapToLong(Number::longValue).sum()
                : numberList.stream()
                .mapToDouble(Number::doubleValue).sum();
    }

    /**
     * Average.
     *
     * @param <N>        the number type
     * @param numberList the number list
     * @return the number
     */
    public static <N extends Number> Number average(List<N> numberList) {
        return JMCollections.isNullOrEmpty(numberList) ? 0
                : numberList.get(0) instanceof Integer
                ? numberList.stream().mapToInt(Number::intValue)
                .average().orElse(0)
                : numberList.get(0) instanceof Long
                ? numberList.stream()
                .mapToLong(Number::longValue).average()
                .orElse(0)
                : numberList.stream()
                .mapToDouble(Number::doubleValue)
                .average().orElse(0);
    }

    /**
     * Cal percent.
     *
     * @param target the target
     * @param total  the total
     * @return the int
     */
    public static int calPercent(Number target, Number total) {
        return (int) calPercentPrecisely(target, total);
    }

    /**
     * Cal percent precisely.
     *
     * @param target the target
     * @param total  the total
     * @return the double
     */
    public static double calPercentPrecisely(Number target, Number total) {
        double targetD = target.doubleValue();
        double totalD = total.doubleValue();
        return targetD == totalD ? 100d : targetD / totalD * 100;
    }

    /**
     * Cal percent.
     *
     * @param target the target
     * @param total  the total
     * @param digit  the digit
     * @return the string
     */
    public static String calPercent(Number target, Number total, int digit) {
        return JMString.roundedNumberFormat(calPercentPrecisely(target, total),
                digit);
    }

    /**
     * Round with decimal place.
     *
     * @param doubleNumber the double number
     * @param decimalPlace the decimal place
     * @return the double
     */
    public static double roundWithDecimalPlace(double doubleNumber,
            int decimalPlace) {
        double pow = pow(10, decimalPlace);
        return Math.round(doubleNumber * pow) / pow;
    }

    /**
     * Round with place.
     *
     * @param doubleNumber the double number
     * @param place        the place
     * @return the double
     */
    public static double roundWithPlace(double doubleNumber, int place) {

        double pow = pow(10, place);
        return Math.round(doubleNumber / pow) * pow;
    }

    /**
     * Pow.
     *
     * @param baseNumber the base number
     * @param exponent   the exponent
     * @return the double
     */
    public static double pow(Number baseNumber, int exponent) {
        return exponent < 1 ? 1
                : exponent > 1
                ? baseNumber.doubleValue()
                * pow(baseNumber, exponent - 1)
                : baseNumber.doubleValue();
    }

    /**
     * Round with place.
     *
     * @param longNumber the long number
     * @param place      the place
     * @return the long
     */
    public static long roundWithPlace(long longNumber, int place) {
        return (long) roundWithPlace((double) longNumber, place);
    }

    /**
     * Cal standard deviation.
     *
     * @param numberList the number list
     * @return the double
     */
    // Sample Standard Deviation
    public static double
    calStandardDeviation(List<? extends Number> numberList) {
        return Math.sqrt(calVariance(numberList));
    }

    /**
     * Cal population standard deviation.
     *
     * @param numberList the number list
     * @return the double
     */
    public static double
    calPopulationStandardDeviation(List<? extends Number> numberList) {
        return Math.sqrt(calPopulationVariance(numberList));
    }

    /**
     * Cal variance.
     *
     * @param numberList the number list
     * @return the double
     */
    // Sample Variance
    public static double calVariance(List<? extends Number> numberList) {
        return calVariance(numberList, numberList.size() - 1);
    }

    /**
     * Cal population variance.
     *
     * @param numberList the number list
     * @return the double
     */
    public static double calPopulationVariance(
            List<? extends Number> numberList) {
        return calVariance(numberList, numberList.size());
    }

    private static double calVariance(List<? extends Number> numberList,
            int count) {
        return numberList.size() == 1 ? 0 :
                calSampleMinusMeanSumOfSquares(numberList) / count;
    }

    public static double
    calSampleMinusMeanSumOfSquares(List<? extends Number> numberList) {
        double[] doubleSamples =
                numberList.stream().mapToDouble(Number::doubleValue).toArray();
        double average = Arrays.stream(doubleSamples).average().getAsDouble();
        return calSumOfSquares(
                Arrays.stream(doubleSamples).map(d -> d - average));
    }

    /**
     * Cal sum of squares.
     *
     * @param numberList the number list
     * @return the double
     */
    public static double calSumOfSquares(List<? extends Number> numberList) {
        return calSumOfSquares(
                numberList.stream().mapToDouble(Number::doubleValue));
    }

    public static double calSumOfSquares(DoubleStream doubleStream) {
        return doubleStream.map(d -> d * d).sum();
    }

    /**
     * Cal pairwise variance.
     *
     * @param count1    the count 1
     * @param sum1      the sum 1
     * @param variance1 the variance 1
     * @param count2    the count 2
     * @param sum2      the sum 2
     * @param variance2 the variance 2
     * @return the double
     */
    // https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#cite_note-:0-10
    public static double calPairwiseVariance(long count1, double sum1,
            double variance1, long count2, double sum2, double variance2) {
        return (variance1 * (count1 - 1) + variance2 * (count2 - 1)
                + pow(sum2 / count2 - sum1 / count1, 2) * count1 * count2
                / (count1 + count2))
                / (count1 + count2 - 1);
    }

}
