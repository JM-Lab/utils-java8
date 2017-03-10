
package kr.jm.utils.helper;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.enums.StatsField;

/**
 * The Class JMStats.
 */
public class JMStats {

	/**
	 * Cal stats.
	 *
	 * @param statsString
	 *            the stats string
	 * @param summaryStatistics
	 *            the summary statistics
	 * @return the number
	 */
	public static Number calStats(String statsString,
			IntSummaryStatistics summaryStatistics) {
		return StatsField.valueOfAlias(statsString).calStats(summaryStatistics);
	}

	/**
	 * Cal stats.
	 *
	 * @param statsString
	 *            the stats string
	 * @param summaryStatistics
	 *            the summary statistics
	 * @return the number
	 */
	public static Number calStats(String statsString,
			LongSummaryStatistics summaryStatistics) {
		return StatsField.valueOfAlias(statsString).calStats(summaryStatistics);
	}

	/**
	 * Cal stats.
	 *
	 * @param statsString
	 *            the stats string
	 * @param summaryStatistics
	 *            the summary statistics
	 * @return the number
	 */
	public static Number calStats(String statsString,
			DoubleSummaryStatistics summaryStatistics) {
		return StatsField.valueOfAlias(statsString).calStats(summaryStatistics);
	}

	/**
	 * Cal stats.
	 *
	 * @param statsString
	 *            the stats string
	 * @param numberStream
	 *            the number stream
	 * @return the number
	 */
	public static Number calStats(String statsString, IntStream numberStream) {
		return StatsField.valueOfAlias(statsString).calStats(numberStream);
	}

	/**
	 * Cal stats.
	 *
	 * @param statsString
	 *            the stats string
	 * @param numberStream
	 *            the number stream
	 * @return the number
	 */
	public static Number calStats(String statsString, LongStream numberStream) {
		return StatsField.valueOfAlias(statsString).calStats(numberStream);
	}

	/**
	 * Cal stats.
	 *
	 * @param statsString
	 *            the stats string
	 * @param numberStream
	 *            the number stream
	 * @return the number
	 */
	public static Number calStats(String statsString,
			DoubleStream numberStream) {
		return StatsField.valueOfAlias(statsString).calStats(numberStream);
	}

	/**
	 * Gets the percentile value.
	 *
	 * @param targetPercentile
	 *            the target percentile
	 * @param unorderedNumberList
	 *            the unordered number list
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
	 * @param targetPercentileList
	 *            the target percentile list
	 * @param numberList
	 *            the number list
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
	 * @param <N>
	 *            the number type
	 * @param numberList
	 *            the number list
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
	 * @param <N>
	 *            the number type
	 * @param numberList
	 *            the number list
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
	 * @param numberList
	 *            the number list
	 * @return the number
	 */
	public static Number count(List<?> numberList) {
		return numberList.size();
	}

	/**
	 * Sum.
	 *
	 * @param <N>
	 *            the number type
	 * @param numberList
	 *            the number list
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
	 * @param <N>
	 *            the number type
	 * @param numberList
	 *            the number list
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
	 * @param target
	 *            the target
	 * @param total
	 *            the total
	 * @return the int
	 */
	public static int calPercent(Number target, Number total) {
		return (int) calPercentPrecisely(target, total);
	}

	/**
	 * Cal percent precisely.
	 *
	 * @param target
	 *            the target
	 * @param total
	 *            the total
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
	 * @param target
	 *            the target
	 * @param total
	 *            the total
	 * @param digit
	 *            the digit
	 * @return the string
	 */
	public static String calPercent(Number target, Number total, int digit) {
		return String.format("%." + digit + "f",
				calPercentPrecisely(target, total));
	}

	public static double roundWithDecimalPlace(double doubleNumber,
			int decimalPlace) {
		double pow = Math.pow(10, decimalPlace);
		return Math.round(doubleNumber * pow) / pow;
	}

	public static double roundWithPlace(double doubleNumber, int place) {
		double pow = Math.pow(10, place);
		return Math.round(doubleNumber / pow) * pow;
	}

	public static long roundWithPlace(long longNumber, int place) {
		return (long) roundWithPlace((double) longNumber, place);
	}
}
