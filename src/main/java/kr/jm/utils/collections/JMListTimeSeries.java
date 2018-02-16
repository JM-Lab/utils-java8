package kr.jm.utils.collections;

import kr.jm.utils.datastructure.JMMap;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class JMListTimeSeries.
 *
 * @param <T> the generic type
 */
public class JMListTimeSeries<T> extends JMTimeSeries<List<T>> {

    /**
     * Instantiates a new JM list timeSeries.
     *
     * @param intervalSeconds the interval seconds
     */
    public JMListTimeSeries(int intervalSeconds) {
		super(intervalSeconds);
	}

    /**
     * Adds the.
     *
     * @param timestamp the timestamp
     * @param object    the object
     */
    public void add(long timestamp, T object) {
		JMMap.getOrPutGetNew(this.timeSeriesMap, buildKeyTimestamp(timestamp),
				ArrayList::new).add(object);
	}

    /**
     * Adds the all.
     *
     * @param timestamp  the timestamp
     * @param objectList the object list
     */
    public void addAll(long timestamp, List<T> objectList) {
		for (T object : objectList)
			add(timestamp, object);
	}

	/*
	 * (non-Javadoc)
     *
     * @see kr.jm.utils.collections.JMTimeSeries#toString()
     */
	@Override
	public String toString() {
		return "JMListTimeSeries(intervalSeconds=" + getIntervalSeconds()
				+ ", timeSeriesMap=" + timeSeriesMap.toString() + ")";
	}

}
