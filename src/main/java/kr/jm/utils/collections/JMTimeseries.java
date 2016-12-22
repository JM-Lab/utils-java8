package kr.jm.utils.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JMTimeseries<T> {
	private long intervalMillis;
	private Map<Long, T> timeseriesMap;

	public JMTimeseries(long intervalSeconds) {
		this.intervalMillis = intervalSeconds * 1000;
		this.timeseriesMap = new ConcurrentHashMap<>();
	}

	public long getIntervalSeconds() {
		return intervalMillis / 1000;
	}

	public T put(long timestamp, T object) {
		return this.timeseriesMap.put(buildKeyTimetamp(timestamp), object);
	}

	private Long buildKeyTimetamp(long timestamp) {
		return timestamp - (timestamp % intervalMillis);
	}

	public T get(long timestamp) {
		return this.timeseriesMap.get(buildKeyTimetamp(timestamp));
	}

	public T remove(long timestamp) {
		return this.timeseriesMap.remove(buildKeyTimetamp(timestamp));
	}

	public Set<Long> getTimestampKeySet() {
		return this.timeseriesMap.keySet();
	}

	public Map<Long, T> getAll() {
		return new HashMap<>(this.timeseriesMap);
	}

	@Override
	public String toString() {
		return "JMTimeseries(intervalSeconds=" + getIntervalSeconds()
				+ ", timeseriesMap=" + timeseriesMap.toString() + ")";
	}

}
