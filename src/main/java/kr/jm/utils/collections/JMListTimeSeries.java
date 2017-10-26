package kr.jm.utils.collections;

import kr.jm.utils.datastructure.JMMap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The Class JMListTimeSeries.
 *
 * @param <T> the generic type
 */
public class JMListTimeSeries<T> extends JMTimeSeries<List<T>> {

	private Supplier<List<T>> newListSupplier = ArrayList<T>::new;

	/**
	 * Instantiates a new JM list timeSeries.
	 *
	 * @param intervalSeconds the interval seconds
	 */
	public JMListTimeSeries(long intervalSeconds) {
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
				newListSupplier).add(object);
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
