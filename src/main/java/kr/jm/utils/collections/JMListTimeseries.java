package kr.jm.utils.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import kr.jm.utils.datastructure.JMMap;

/**
 * The Class JMListTimeseries.
 *
 * @param <T>
 *            the generic type
 */
public class JMListTimeseries<T> extends JMTimeseries<List<T>> {

	private Supplier<List<T>> newListSupplier = ArrayList<T>::new;

	/**
	 * Instantiates a new JM list timeseries.
	 *
	 * @param intervalSeconds
	 *            the interval seconds
	 */
	public JMListTimeseries(long intervalSeconds) {
		super(intervalSeconds);
	}

	/**
	 * Adds the.
	 *
	 * @param timestamp
	 *            the timestamp
	 * @param object
	 *            the object
	 */
	public void add(long timestamp, T object) {
		JMMap.getOrPutGetNew(this.timeseriesMap, buildKeyTimetamp(timestamp),
				newListSupplier).add(object);
	}

	/**
	 * Adds the all.
	 *
	 * @param timestamp
	 *            the timestamp
	 * @param objectList
	 *            the object list
	 */
	public void addAll(long timestamp, List<T> objectList) {
		for (T object : objectList)
			add(timestamp, object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.jm.utils.collections.JMTimeseries#toString()
	 */
	@Override
	public String toString() {
		return "JMListTimeseries(intervalSeconds=" + getIntervalSeconds()
				+ ", timeseriesMap=" + timeseriesMap.toString() + ")";
	}

}
