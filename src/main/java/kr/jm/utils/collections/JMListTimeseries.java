package kr.jm.utils.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import kr.jm.utils.datastructure.JMMap;

public class JMListTimeseries<T> extends JMTimeseries<List<T>> {

	private Supplier<List<T>> newListSupplier = ArrayList<T>::new;

	public JMListTimeseries(long intervalSeconds) {
		super(intervalSeconds);
	}

	public void add(long timestamp, T object) {
		JMMap.getOrPutGetNew(this.timeseriesMap, buildKeyTimetamp(timestamp),
				newListSupplier).add(object);
	}

	public void addAll(long timestamp, List<T> objectList) {
		for (T object : objectList)
			add(timestamp, object);
	}

	@Override
	public String toString() {
		return "JMListTimeseries(intervalSeconds=" + getIntervalSeconds()
				+ ", timeseriesMap=" + timeseriesMap.toString() + ")";
	}

}
