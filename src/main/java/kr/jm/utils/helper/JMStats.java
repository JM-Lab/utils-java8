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

public class JMStats {

	public static Number calStats(String statsString,
			IntSummaryStatistics summaryStatistics) {
		return StatsField.valueOfAlias(statsString).calStats(summaryStatistics);
	}

	public static Number calStats(String statsString,
			LongSummaryStatistics summaryStatistics) {
		return StatsField.valueOfAlias(statsString).calStats(summaryStatistics);
	}

	public static Number calStats(String statsString,
			DoubleSummaryStatistics summaryStatistics) {
		return StatsField.valueOfAlias(statsString).calStats(summaryStatistics);
	}

	public static Number calStats(String statsString, IntStream numberStream) {
		return StatsField.valueOfAlias(statsString).calStats(numberStream);
	}

	public static Number calStats(String statsString, LongStream numberStream) {
		return StatsField.valueOfAlias(statsString).calStats(numberStream);
	}

	public static Number calStats(String statsString, DoubleStream numberStream) {
		return StatsField.valueOfAlias(statsString).calStats(numberStream);
	}

	public enum StatsField {
		sum, min, max, avg, count;

		public static StatsField valueOfAlias(String alias) {
			switch (alias.toLowerCase()) {
			case "minimum":
				return min;
			case "maximum":
				return max;
			case "average":
				return avg;
			default:
				return valueOf(alias);
			}
		}

		public Number calStats(IntStream intStream) {
			switch (this) {
			case sum:
				return intStream.sum();
			case min:
				return intStream.min().orElse(0);
			case max:
				return intStream.max().orElse(0);
			case count:
				return intStream.count();
			case avg:
				return intStream.average().orElse(0d);
			default:
				return 0;
			}
		}

		public Number calStats(LongStream longStream) {
			switch (this) {
			case sum:
				return longStream.sum();
			case min:
				return longStream.min().orElse(0l);
			case max:
				return longStream.max().orElse(0l);
			case count:
				return longStream.count();
			case avg:
				return longStream.average().orElse(0d);
			default:
				return 0l;
			}
		}

		public Number calStats(DoubleStream doubleStream) {
			switch (this) {
			case sum:
				return doubleStream.sum();
			case min:
				return doubleStream.min().orElse(0d);
			case max:
				return doubleStream.max().orElse(0d);
			case count:
				return doubleStream.count();
			case avg:
				return doubleStream.average().orElse(0d);
			default:
				return 0d;
			}
		}

		public Number calStats(IntSummaryStatistics summaryStatistics) {
			switch (this) {
			case sum:
				return summaryStatistics.getSum();
			case min:
				return summaryStatistics.getMin();
			case max:
				return summaryStatistics.getMax();
			case count:
				return summaryStatistics.getCount();
			case avg:
				return summaryStatistics.getAverage();
			default:
				return 0;
			}
		}

		public Number calStats(LongSummaryStatistics summaryStatistics) {
			switch (this) {
			case sum:
				return summaryStatistics.getSum();
			case min:
				return summaryStatistics.getMin();
			case max:
				return summaryStatistics.getMax();
			case count:
				return summaryStatistics.getCount();
			case avg:
				return summaryStatistics.getAverage();
			default:
				return 0l;
			}
		}

		public Number calStats(DoubleSummaryStatistics summaryStatistics) {
			switch (this) {
			case sum:
				return summaryStatistics.getSum();
			case min:
				return summaryStatistics.getMin();
			case max:
				return summaryStatistics.getMax();
			case count:
				return summaryStatistics.getCount();
			case avg:
				return summaryStatistics.getAverage();
			default:
				return 0d;
			}
		}
	}

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

	public static Map<Integer, Number> getPercentileValueMap(
			List<Integer> targetPercentileList, List<Number> numberList) {
		List<Double> sortedDoubleList = sortedDoubleList(numberList);
		return targetPercentileList.stream().collect(
				toMap(percentile -> percentile,
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
		return targetPercentile < 0 ? 0 : targetPercentile > 100 ? 100
				: targetPercentile;
	}

	public static <N extends Number> Number min(List<N> numberList) {
		return JMCollections.isNullOrEmpty(numberList) ? 0
				: numberList.get(0) instanceof Integer ? numberList.stream()
						.mapToInt(Number::intValue).min().orElse(0)
						: numberList.get(0) instanceof Long ? numberList
								.stream().mapToLong(Number::longValue).min()
								.orElse(0) : numberList.stream()
								.mapToDouble(Number::doubleValue).min()
								.orElse(0);
	}

	public static <N extends Number> Number max(List<N> numberList) {
		return JMCollections.isNullOrEmpty(numberList) ? 0
				: numberList.get(0) instanceof Integer ? numberList.stream()
						.mapToInt(Number::intValue).max().orElse(0)
						: numberList.get(0) instanceof Long ? numberList
								.stream().mapToLong(Number::longValue).max()
								.orElse(0) : numberList.stream()
								.mapToDouble(Number::doubleValue).max()
								.orElse(0);
	}

	public static Number count(List<?> numberList) {
		return numberList.size();
	}

	public static <N extends Number> Number sum(List<N> numberList) {
		return JMCollections.isNullOrEmpty(numberList) ? 0
				: numberList.get(0) instanceof Integer ? numberList.stream()
						.mapToInt(Number::intValue).sum()
						: numberList.get(0) instanceof Long ? numberList
								.stream().mapToLong(Number::longValue).sum()
								: numberList.stream()
										.mapToDouble(Number::doubleValue).sum();
	}

	public static <N extends Number> Number average(List<N> numberList) {
		return JMCollections.isNullOrEmpty(numberList) ? 0
				: numberList.get(0) instanceof Integer ? numberList.stream()
						.mapToInt(Number::intValue).average().orElse(0)
						: numberList.get(0) instanceof Long ? numberList
								.stream().mapToLong(Number::longValue)
								.average().orElse(0) : numberList.stream()
								.mapToDouble(Number::doubleValue).average()
								.orElse(0);
	}

}
