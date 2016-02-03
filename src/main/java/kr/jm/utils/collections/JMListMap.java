package kr.jm.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import kr.jm.utils.datastructure.JMMap;

/**
 * The Class JMListMap.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class JMListMap<K, V> implements Map<K, List<V>> {

	private Map<K, List<V>> listMap;
	private Supplier<List<V>> newListSupplier = ArrayList::new;

	/* (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return listMap.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return listMap.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return listMap.containsKey(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		return listMap.containsValue(value);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public List<V> get(Object key) {
		return listMap.get(key);
	}

	/**
	 * Adds the.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void add(K key, V value) {
		JMMap.getOrPutGetNew(listMap, key, newListSupplier).add(value);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public List<V> put(K key, List<V> valueList) {
		return listMap.put(key, valueList);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public List<V> remove(Object key) {
		return listMap.remove(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends List<V>> m) {
		listMap.putAll(m);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		listMap.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<K> keySet() {
		return listMap.keySet();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<List<V>> values() {
		return listMap.values();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<K, List<V>>> entrySet() {
		return listMap.entrySet();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return listMap.equals(o);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return listMap.hashCode();
	}

	/**
	 * Instantiates a new JM list map.
	 */
	public JMListMap() {
		this.listMap = new HashMap<>();
	}

	/**
	 * Instantiates a new JM list map.
	 *
	 * @param map the map
	 */
	public JMListMap(Map<K, List<V>> map) {
		this.listMap = map;
	}

}
