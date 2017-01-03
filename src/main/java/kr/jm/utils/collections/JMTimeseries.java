package kr.jm.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.datastructure.JMMap;

public class JMTimeseries<V> implements Map<Long, V> {
	long intervalMillis;
	Map<Long, V> timeseriesMap;

	public JMTimeseries(long intervalSeconds) {
		this.intervalMillis = intervalSeconds * 1000;
		this.timeseriesMap = new ConcurrentHashMap<>();
	}

	public long getIntervalSeconds() {
		return intervalMillis / 1000;
	}

	@Override
	public V put(Long timestamp, V object) {
		return this.timeseriesMap.put(buildKeyTimetamp(timestamp), object);
	}

	@Override
	public V get(Object timestamp) {
		return this.timeseriesMap.get(buildKeyTimetamp(timestamp));
	}

	public V getOrNew(Long timestamp, Supplier<V> newSupplier) {
		return JMMap.getOrPutGetNew(this.timeseriesMap,
				buildKeyTimetamp(timestamp), newSupplier);
	}

	@Override
	public V remove(Object timestamp) {
		return this.timeseriesMap.remove(buildKeyTimetamp(timestamp));
	}

	public List<Long> getTimestampKeyList() {
		return JMCollections.sort(new ArrayList<>(keySet()),
				(l1, l2) -> l2.compareTo(l1));
	}

	public Map<Long, V> getAll() {
		return new HashMap<>(this.timeseriesMap);
	}

	public Long buildKeyTimetamp(Long timestamp) {
		return timestamp - (timestamp % intervalMillis);
	}

	private Long buildKeyTimetamp(Object timestamp) {
		return timestamp instanceof Long ? buildKeyTimetamp((Long) timestamp)
				: null;
	}

	@Override
	public String toString() {
		return "JMTimeseries(intervalSeconds=" + getIntervalSeconds()
				+ ", timeseriesMap=" + timeseriesMap.toString() + ")";
	}

	@Override
	public int size() {
		return this.timeseriesMap.size();
	}

	@Override
	public boolean isEmpty() {
		return this.timeseriesMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object timestamp) {
		return this.timeseriesMap.containsKey(buildKeyTimetamp(timestamp));
	}

	@Override
	public boolean containsValue(Object value) {
		return this.timeseriesMap.containsValue(value);
	}

	@Override
	public void putAll(Map<? extends Long, ? extends V> m) {
		this.timeseriesMap.putAll(m);
	}

	@Override
	public void clear() {
		this.timeseriesMap.clear();
	}

	@Override
	public Set<Long> keySet() {
		return this.timeseriesMap.keySet();
	}

	@Override
	public Collection<V> values() {
		return this.timeseriesMap.values();
	}

	@Override
	public Set<java.util.Map.Entry<Long, V>> entrySet() {
		return this.timeseriesMap.entrySet();
	}

}
