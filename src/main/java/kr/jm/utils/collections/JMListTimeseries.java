package kr.jm.utils.collections;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JMListTimeseries<T> {
	private long intervalMillis;
	private JMListMap<Long, T> timeseriesListMap;

	public JMListTimeseries(long intervalSeconds) {
		this.intervalMillis = intervalSeconds * 1000;
		this.timeseriesListMap = new JMListMap<>(new ConcurrentHashMap<>());
	}

	public long getIntervalSeconds() {
		return intervalMillis / 1000;
	}

	public void add(long timestamp, T object) {
		this.timeseriesListMap.add(buildKeyTimetamp(timestamp), object);
	}

	public void addAll(long timestamp, List<T> objectList) {
		for (T object : objectList)
			add(timestamp, object);
	}

	public void put(long timestamp, List<T> objectList) {
		this.timeseriesListMap.put(buildKeyTimetamp(timestamp), objectList);
	}

	private Long buildKeyTimetamp(long timestamp) {
		return timestamp - (timestamp % intervalMillis);
	}

	public List<T> get(long timestamp) {
		return this.timeseriesListMap.get(buildKeyTimetamp(timestamp));
	}

	public List<T> remove(long timestamp) {
		return this.timeseriesListMap.remove(buildKeyTimetamp(timestamp));
	}

	public Set<Long> getTimestampKeySet() {
		return this.timeseriesListMap.keySet();
	}

	public Map<Long, List<T>> getAll() {
		return this.timeseriesListMap.getAll();
	}

	@Override
	public String toString() {
		return "JMTimeseries(intervalSeconds=" + getIntervalSeconds()
				+ ", timeseriesListMap=" + timeseriesListMap.toString() + ")";
	}

}
