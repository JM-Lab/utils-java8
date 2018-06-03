package kr.jm.utils.collections;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.datastructure.JMMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static java.util.Comparator.reverseOrder;

/**
 * The type Jm time series.
 *
 * @param <V> the type parameter
 */
public class JMTimeSeries<V> implements Map<Long, V> {
    /**
     * The Interval seconds.
     */
    protected int intervalSeconds;
    /**
     * The Interval millis.
     */
    protected long intervalMillis;
    /**
     * The Time series map.
     */
    protected Map<Long, V> timeSeriesMap;

    /**
     * Instantiates a new Jm time series.
     *
     * @param intervalSeconds the interval seconds
     */
    public JMTimeSeries(int intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
        this.intervalMillis = intervalSeconds * 1000;
        this.timeSeriesMap = new ConcurrentHashMap<>();
    }

    /**
     * Gets interval seconds.
     *
     * @return the interval seconds
     */
    public int getIntervalSeconds() {
        return this.intervalSeconds;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public V put(Long timestamp, V object) {
        return this.timeSeriesMap.put(buildKeyTimestamp(timestamp), object);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#get(java.lang.Object)
     */
    @Override
    public V get(Object timestamp) {
        return this.timeSeriesMap.get(buildKeyTimestamp(timestamp));
    }

    /**
     * Gets or put get new.
     *
     * @param timestamp   the timestamp
     * @param newSupplier the new supplier
     * @return the or put get new
     */
    public V getOrPutGetNew(Long timestamp, Supplier<V> newSupplier) {
        return JMMap.getOrPutGetNew(this.timeSeriesMap,
                buildKeyTimestamp(timestamp), newSupplier);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#remove(java.lang.Object)
     */
    @Override
    public V remove(Object timestamp) {
        return this.timeSeriesMap.remove(buildKeyTimestamp(timestamp));
    }

    /**
     * Gets timestamp key list.
     *
     * @return the timestamp key list
     */
    public List<Long> getTimestampKeyList() {
        return JMCollections.sort(new ArrayList<>(keySet()), reverseOrder());
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public Map<Long, V> getAll() {
        return new HashMap<>(this.timeSeriesMap);
    }

    /**
     * Build key timestamp long.
     *
     * @param timestamp the timestamp
     * @return the long
     */
    public Long buildKeyTimestamp(Long timestamp) {
        return timestamp - (timestamp % intervalMillis);
    }

    private Long buildKeyTimestamp(Object timestamp) {
        return timestamp instanceof Long ? buildKeyTimestamp((Long) timestamp)
                : null;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "JMTimeSeries(intervalSeconds=" + getIntervalSeconds()
                + ", timeSeriesMap=" + timeSeriesMap.toString() + ")";
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#size()
     */
    @Override
    public int size() {
        return this.timeSeriesMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.timeSeriesMap.isEmpty();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object timestamp) {
        return this.timeSeriesMap.containsKey(buildKeyTimestamp(timestamp));
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value) {
        return this.timeSeriesMap.containsValue(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#putAll(java.util.Map)
     */
    @Override
    public void putAll(Map<? extends Long, ? extends V> m) {
        this.timeSeriesMap.putAll(m);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#clear()
     */
    @Override
    public void clear() {
        this.timeSeriesMap.clear();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#keySet()
     */
    @Override
    public Set<Long> keySet() {
        return this.timeSeriesMap.keySet();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#values()
     */
    @Override
    public Collection<V> values() {
        return this.timeSeriesMap.values();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#entrySet()
     */
    @Override
    public Set<java.util.Map.Entry<Long, V>> entrySet() {
        return this.timeSeriesMap.entrySet();
    }

}
