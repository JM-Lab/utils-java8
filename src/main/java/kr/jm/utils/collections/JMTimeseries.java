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

/**
 * The Class JMTimeseries.
 *
 * @param <V>
 *            the value type
 */
public class JMTimeseries<V> implements Map<Long, V> {
	long intervalMillis;
	Map<Long, V> timeseriesMap;

	/**
	 * Instantiates a new JM timeseries.
	 *
	 * @param intervalSeconds
	 *            the interval seconds
	 */
	public JMTimeseries(long intervalSeconds) {
		this.intervalMillis = intervalSeconds * 1000;
		this.timeseriesMap = new ConcurrentHashMap<>();
	}

	public long getIntervalSeconds() {
		return intervalMillis / 1000;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(Long timestamp, V object) {
		return this.timeseriesMap.put(buildKeyTimetamp(timestamp), object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public V get(Object timestamp) {
		return this.timeseriesMap.get(buildKeyTimetamp(timestamp));
	}

	/**
	 * Gets the or new.
	 *
	 * @param timestamp
	 *            the timestamp
	 * @param newSupplier
	 *            the new supplier
	 * @return the or new
	 */
	public V getOrNew(Long timestamp, Supplier<V> newSupplier) {
		return JMMap.getOrPutGetNew(this.timeseriesMap,
				buildKeyTimetamp(timestamp), newSupplier);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
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

	/**
	 * Builds the key timetamp.
	 *
	 * @param timestamp
	 *            the timestamp
	 * @return the long
	 */
	public Long buildKeyTimetamp(Long timestamp) {
		return timestamp - (timestamp % intervalMillis);
	}

	private Long buildKeyTimetamp(Object timestamp) {
		return timestamp instanceof Long ? buildKeyTimetamp((Long) timestamp)
				: null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JMTimeseries(intervalSeconds=" + getIntervalSeconds()
				+ ", timeseriesMap=" + timeseriesMap.toString() + ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return this.timeseriesMap.size();
	}

	@Override
	public boolean isEmpty() {
		return this.timeseriesMap.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object timestamp) {
		return this.timeseriesMap.containsKey(buildKeyTimetamp(timestamp));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		return this.timeseriesMap.containsValue(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends Long, ? extends V> m) {
		this.timeseriesMap.putAll(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		this.timeseriesMap.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<Long> keySet() {
		return this.timeseriesMap.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<V> values() {
		return this.timeseriesMap.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<Long, V>> entrySet() {
		return this.timeseriesMap.entrySet();
	}

}
