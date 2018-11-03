package kr.jm.utils.collections;

import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.helper.JMOptional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * The type Jm nested map.
 *
 * @param <K1> the type parameter
 * @param <K2> the type parameter
 * @param <V>  the type parameter
 */
public class JMNestedMap<K1, K2, V> implements Map<K1, Map<K2, V>> {

    private Map<K1, Map<K2, V>> nestedMap;

    /**
     * Instantiates a new Jm nested map.
     */
    public JMNestedMap() {
        this(false);
    }

    /**
     * Instantiates a new Jm nested map.
     *
     * @param map the map
     */
    public JMNestedMap(Map<K1, Map<K2, V>> map) {
        this(false, map);
    }

    /**
     * Instantiates a new Jm nested map.
     *
     * @param isWeak the is weak
     */
    public JMNestedMap(boolean isWeak) {
        this.nestedMap =
                isWeak ? new WeakHashMap<>() : new ConcurrentHashMap<>();
    }

    /**
     * Instantiates a new Jm nested map.
     *
     * @param isWeak the is weak
     * @param map    the map
     */
    public JMNestedMap(boolean isWeak, Map<K1, Map<K2, V>> map) {
        this.nestedMap =
                isWeak ? new WeakHashMap<>(map) : new ConcurrentHashMap<>(map);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#size()
     */
    @Override
    public int size() {
        return nestedMap.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return nestedMap.isEmpty();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key) {
        return nestedMap.containsKey(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value) {
        return nestedMap.containsValue(value);
    }

    @Override
    public Map<K2, V> get(Object key) {
        return nestedMap.get(key);
    }

    @Override
    public Map<K2, V> put(K1 key, Map<K2, V> value) {
        return nestedMap.put(key, value);
    }

    @Override
    public Map<K2, V> remove(Object key) {return nestedMap.remove(key);}

    @Override
    public void putAll(Map<? extends K1, ? extends Map<K2, V>> m) {
        nestedMap.putAll(m);
    }

    @Override
    public void clear() {nestedMap.clear();}

    @Override
    public Set<K1> keySet() {return nestedMap.keySet();}

    @Override
    public Collection<Map<K2, V>> values() {return nestedMap.values();}

    @Override
    public Set<Entry<K1, Map<K2, V>>> entrySet() {return nestedMap.entrySet();}

    @Override
    public boolean equals(Object o) {return nestedMap.equals(o);}

    @Override
    public int hashCode() {return nestedMap.hashCode();}

    @Override
    public String toString() {
        return nestedMap.toString();
    }

    /**
     * Put v.
     *
     * @param key1  the key 1
     * @param key2  the key 2
     * @param value the value
     * @return the v
     */
    public V put(K1 key1, K2 key2, V value) {
        return getOrPutGetNew(key1).put(key2, value);
    }

    /**
     * Get v.
     *
     * @param key1 the key 1
     * @param key2 the key 2
     * @return the v
     */
    public V get(K1 key1, K2 key2) {
        return JMOptional.getOptional(nestedMap, key1).map(map -> map.get(key2))
                .orElse(null);
    }

    /**
     * Gets or put get new.
     *
     * @param key1             the key 1
     * @param key2             the key 2
     * @param newValueSupplier the new value supplier
     * @return the or put get new
     */
    public V getOrPutGetNew(K1 key1, K2 key2, Supplier<V> newValueSupplier) {
        return JMMap
                .getOrPutGetNew(getOrPutGetNew(key1), key2, newValueSupplier);
    }

    /**
     * Gets or put get new.
     *
     * @param key1 the key 1
     * @return the or put get new
     */
    public Map<K2, V> getOrPutGetNew(K1 key1) {
        return getOrPutGetNew(key1, ConcurrentHashMap::new);
    }

    /**
     * Gets or put get new.
     *
     * @param key1           the key 1
     * @param newMapSupplier the new map supplier
     * @return the or put get new
     */
    public Map<K2, V> getOrPutGetNew(K1 key1,
            Supplier<Map<K2, V>> newMapSupplier) {
        return JMMap.getOrPutGetNew(nestedMap, key1, newMapSupplier);
    }
}
