package kr.jm.utils.collections;

import kr.jm.utils.datastructure.JMMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Jm list map.
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
public class JMListMap<K, V> implements Map<K, List<V>> {

    private Map<K, List<V>> listMap;

    /**
     * Instantiates a new Jm list map.
     */
    public JMListMap() {
        this.listMap = new ConcurrentHashMap<>();
    }

    /**
     * Instantiates a new Jm list map.
     *
     * @param map the map
     */
    public JMListMap(Map<K, List<V>> map) {
        this.listMap = map;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#size()
     */
    @Override
    public int size() {
        return listMap.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return listMap.isEmpty();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key) {
        return listMap.containsKey(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value) {
        return listMap.containsValue(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#get(java.lang.Object)
     */
    @Override
    public List<V> get(Object key) {
        return listMap.get(key);
    }

    /**
     * Add boolean.
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public boolean add(K key, V value) {
        return getOrPutGetNewList(key).add(value);
    }

    /**
     * Add all boolean.
     *
     * @param key  the key
     * @param list the list
     * @return the boolean
     */
    public boolean addAll(K key, List<V> list) {
        return getOrPutGetNewList(key).addAll(list);
    }

    /**
     * Merge jm list map.
     *
     * @param jmListMap the jm list map
     * @return the jm list map
     */
    public JMListMap<K, V> merge(JMListMap<K, V> jmListMap) {
        jmListMap.forEach(this::addAll);
        return this;
    }

    private List<V> getOrPutGetNewList(K key) {
        return JMMap.getOrPutGetNew(listMap, key, () -> Collections
                .synchronizedList(new ArrayList<>()));
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public List<V> put(K key, List<V> valueList) {
        return listMap.put(key, valueList);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#remove(java.lang.Object)
     */
    @Override
    public List<V> remove(Object key) {
        return listMap.remove(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#putAll(java.util.Map)
     */
    @Override
    public void putAll(Map<? extends K, ? extends List<V>> m) {
        listMap.putAll(m);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#clear()
     */
    @Override
    public void clear() {
        listMap.clear();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#keySet()
     */
    @Override
    public Set<K> keySet() {
        return listMap.keySet();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#values()
     */
    @Override
    public Collection<List<V>> values() {
        return listMap.values();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Map#entrySet()
     */
    @Override
    public Set<java.util.Map.Entry<K, List<V>>> entrySet() {
        return listMap.entrySet();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        return listMap.equals(o);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return listMap.hashCode();
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public Map<K, List<V>> getAll() {
        return new HashMap<>(this.listMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return listMap.toString();
    }

}
