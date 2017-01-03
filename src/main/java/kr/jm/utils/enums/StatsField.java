package kr.jm.utils.enums;

import static java.util.stream.Collectors.toMap;
import static kr.jm.utils.helper.JMLambda.getSelf;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * The Enum StatsField.
 */
public enum StatsField {
	sum, min, max, avg, count;

	/**
	 * Value of alias.
	 *
	 * @param alias
	 *            the alias
	 * @return the stats field
	 */
	public static StatsField valueOfAlias(String alias) {
		switch (alias.toLowerCase()) {
		case "summary":
			return sum;
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

	/**
	 * Cal stats.
	 *
	 * @param intStream
	 *            the int stream
	 * @return the number
	 */
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

	/**
	 * Cal stats.
	 *
	 * @param longStream
	 *            the long stream
	 * @return the number
	 */
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

	/**
	 * Cal stats.
	 *
	 * @param doubleStream
	 *            the double stream
	 * @return the number
	 */
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

	/**
	 * Cal stats.
	 *
	 * @param summaryStatistics
	 *            the summary statistics
	 * @return the number
	 */
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

	/**
	 * Cal stats.
	 *
	 * @param summaryStatistics
	 *            the summary statistics
	 * @return the number
	 */
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

	/**
	 * Cal stats.
	 *
	 * @param summaryStatistics
	 *            the summary statistics
	 * @return the number
	 */
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

	public static Map<StatsField, Number>
			calStatsMap(IntSummaryStatistics summaryStatistics) {
		return buildStatsMap(
				statsField -> statsField.calStats(summaryStatistics));
	}

	public static Map<StatsField, Number>
			calStatsMap(LongSummaryStatistics summaryStatistics) {
		return buildStatsMap(
				statsField -> statsField.calStats(summaryStatistics));
	}

	public static Map<StatsField, Number>
			calStatsMap(DoubleSummaryStatistics summaryStatistics) {
		return buildStatsMap(
				statsField -> statsField.calStats(summaryStatistics));
	}

	private static Map<StatsField, Number>
			buildStatsMap(Function<StatsField, Number> valueMapper) {
		return Arrays.stream(StatsField.values())
				.collect(toMap(getSelf(), valueMapper));
	}
}
