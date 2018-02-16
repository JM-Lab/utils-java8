package kr.jm.utils.collections;

import kr.jm.utils.helper.JMOptional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class JMListMap.
 *
 * @param <V> the key type
 */
public class JMCountMap<V> implements Map<V, Long> {

    private final Map<V, Long> countMap;

    /**
     * Instantiates a new JM list map.
     */
    public JMCountMap() {
        this.countMap = new ConcurrentHashMap<>();
    }

    /**
     * Instantiates a new JM list map.
     *
     * @param map the map
     */
    public JMCountMap(Map<V, Long> map) {
        this.countMap = map;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#size()
     */
    @Override
    public int size() {
        return countMap.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return countMap.isEmpty();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key) {
        return countMap.containsKey(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value) {
        return countMap.containsValue(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#get(java.lang.Object)
     */
    @Override
    public Long get(Object key) {
        return countMap.get(key);
    }

    @Override
    public Long put(V key, Long value) {
        return countMap.put(key, value);
    }

    @Override
    public Long remove(Object key) {
        return countMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends V, ? extends Long> m) {
        countMap.putAll(m);
    }

    @Override
    public void clear() {
        countMap.clear();
    }

    @Override
    public Set<V> keySet() {
        return countMap.keySet();
    }

    @Override
    public Collection<Long> values() {
        return countMap.values();
    }

    @Override
    public Set<Entry<V, Long>> entrySet() {
        return countMap.entrySet();
    }

    @Override
    public String toString() {
        return "JMCountMap{" + "countMap=" + countMap + '}';
    }

    /**
     * Increment and get long.
     *
     * @param value the value
     * @return the long
     */
    public long incrementAndGet(V value) {
        synchronized (countMap) {
            countMap.put(value, getCount(value) + 1);
            return countMap.get(value);
        }
    }

    /**
     * Gets count.
     *
     * @param value the value
     * @return the count
     */
    public long getCount(V value) {
        return JMOptional.getOptional(countMap, value).orElse(0L);
    }

    /**
     * Merge jm count map.
     *
     * @param jmCountMap the jm count map
     * @return the jm count map
     */
    public JMCountMap<V> merge(JMCountMap<V> jmCountMap) {
        synchronized (countMap) {
            jmCountMap.forEach((value, count) -> countMap
                    .put(value, getCount(value) + count));
            return this;
        }
    }


}
