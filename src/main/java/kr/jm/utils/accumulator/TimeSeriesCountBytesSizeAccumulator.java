package kr.jm.utils.accumulator;

import kr.jm.utils.collections.JMTimeSeries;
import kr.jm.utils.helper.JMOptional;

import java.util.function.Function;

/**
 * The type Time series count bytes size accumulator.
 */
public class TimeSeriesCountBytesSizeAccumulator extends
        JMTimeSeries<CountBytesSizeAccumulator> {
    /**
     * Instantiates a new Time series count bytes size accumulator.
     *
     * @param intervalSeconds the interval seconds
     */
    public TimeSeriesCountBytesSizeAccumulator(int intervalSeconds) {
        super(intervalSeconds);
    }

    /**
     * Gets count per seconds.
     *
     * @param timestamp the timestamp
     * @return the count per seconds
     */
    public double getCountPerSeconds(long timestamp) {
        return getNumber(timestamp, CountBytesSizeAccumulator::getCount) /
                intervalSeconds;
    }

    /**
     * Gets number.
     *
     * @param timestamp     the timestamp
     * @param valueFunction the value function
     * @return the number
     */
    public double getNumber(long timestamp,
            Function<CountBytesSizeAccumulator, Long> valueFunction) {
        return JMOptional.getOptional(this, timestamp).map(valueFunction)
                .map((Long::doubleValue)).orElse(0d);
    }

    /**
     * Gets bytes size per seconds.
     *
     * @param timestamp the timestamp
     * @return the bytes size per seconds
     */
    public double getBytesSizePerSeconds(long timestamp) {
        return getNumber(timestamp, CountBytesSizeAccumulator::getBytesSize) /
                intervalSeconds;
    }
}
