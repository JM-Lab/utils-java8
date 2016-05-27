
package kr.jm.utils.datastructure;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import kr.jm.utils.helper.JMOptional;

/**
 * The Class JMMap.
 */
public class JMMap {

	/**
	 * Removes the all if by key.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @param predicate
	 *            the predicate
	 * @return the list
	 */
	public static <K, V> List<V> removeAllIfByKey(Map<K, V> map,
			Predicate<? super K> predicate) {
		synchronized (map) {
			return map.keySet().stream().filter(predicate).collect(toList())
					.stream().map(map::remove).collect(toList());
		}
	}

	/**
	 * Removes the all if by entry.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @param predicate
	 *            the predicate
	 * @return the list
	 */
	public static <K, V> List<V> removeAllIfByEntry(Map<K, V> map,
			Predicate<? super Entry<K, V>> predicate) {
		synchronized (map) {
			return map.entrySet().stream().filter(predicate).map(Entry::getKey)
					.collect(toList()).stream().map(map::remove)
					.collect(toList());
		}
	}

	/**
	 * Gets the or else.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @param key
	 *            the key
	 * @param valueSupplier
	 *            the value supplier
	 * @return the or else
	 */
	public static <K, V> V getOrElse(Map<K, V> map, K key,
			Supplier<V> valueSupplier) {
		return JMOptional.getOptional(map, key).orElseGet(valueSupplier);
	}

	/**
	 * Gets the or put get new.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @param key
	 *            the key
	 * @param newValueSupplier
	 *            the new value supplier
	 * @return the or put get new
	 */
	public static <K, V> V getOrPutGetNew(Map<K, V> map, K key,
			Supplier<V> newValueSupplier) {
		synchronized (map) {
			return JMOptional.getOptional(map, key).orElseGet(
					() -> putGetNew(map, key, newValueSupplier.get()));
		}
	}

	/**
	 * Gets the or put get new.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @param key
	 *            the key
	 * @param newValue
	 *            the new value
	 * @return the or put get new
	 */
	public static <K, V> V getOrPutGetNew(Map<K, V> map, K key, V newValue) {
		synchronized (map) {
			return JMOptional.getOptional(map, key)
					.orElseGet(() -> putGetNew(map, key, newValue));
		}
	}

	/**
	 * Put get new.
	 *
	 * @param <V>
	 *            the value type
	 * @param <K>
	 *            the key type
	 * @param map
	 *            the map
	 * @param key
	 *            the key
	 * @param newValue
	 *            the new value
	 * @return the v
	 */
	public static <V, K> V putGetNew(Map<K, V> map, K key, V newValue) {
		synchronized (map) {
			map.put(key, newValue);
			return newValue;
		}
	}

	/**
	 * New changed key map.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param <NK>
	 *            the generic type
	 * @param map
	 *            the map
	 * @param changingKeyFunction
	 *            the changing key function
	 * @return the map
	 */
	public static <K, V, NK> Map<NK, V> newChangedKeyMap(Map<K, V> map,
			Function<K, NK> changingKeyFunction) {
		synchronized (map) {
			return map.entrySet().stream()
					.collect(toMap(
							entry -> changingKeyFunction.apply(entry.getKey()),
							Entry::getValue));
		}
	}

	/**
	 * New filtered changed key map.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param <NK>
	 *            the generic type
	 * @param map
	 *            the map
	 * @param filter
	 *            the filter
	 * @param changingKeyFunction
	 *            the changing key function
	 * @return the map
	 */
	public static <K, V, NK> Map<NK, V> newFilteredChangedKeyMap(Map<K, V> map,
			Predicate<? super Entry<K, V>> filter,
			Function<K, NK> changingKeyFunction) {
		synchronized (map) {
			return map.entrySet().stream().filter(filter)
					.collect(toMap(
							entry -> changingKeyFunction.apply(entry.getKey()),
							Entry::getValue));
		}
	}

	/**
	 * New changed value map.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param <NV>
	 *            the generic type
	 * @param map
	 *            the map
	 * @param changingValueFunction
	 *            the changing value function
	 * @return the map
	 */
	public static <K, V, NV> Map<K, NV> newChangedValueMap(Map<K, V> map,
			Function<V, NV> changingValueFunction) {
		synchronized (map) {
			return map.entrySet().stream().collect(toMap(Entry::getKey,
					entry -> changingValueFunction.apply(entry.getValue())));
		}
	}

	/**
	 * New filtered changed value map.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param <NV>
	 *            the generic type
	 * @param map
	 *            the map
	 * @param filter
	 *            the filter
	 * @param changingValueFunction
	 *            the changing value function
	 * @return the map
	 */
	public static <K, V, NV> Map<K, NV> newFilteredChangedValueMap(
			Map<K, V> map, Predicate<Entry<K, V>> filter,
			Function<V, NV> changingValueFunction) {
		synchronized (map) {
			return map.entrySet().stream().filter(filter).collect(toMap(
					Entry::getKey,
					entry -> changingValueFunction.apply(entry.getValue())));
		}
	}

	/**
	 * New changed key value map.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param <NK>
	 *            the generic type
	 * @param <NV>
	 *            the generic type
	 * @param map
	 *            the map
	 * @param changingKeyFunction
	 *            the changing key function
	 * @param changingValueFunction
	 *            the changing value function
	 * @return the map
	 */
	public static <K, V, NK, NV> Map<NK, NV> newChangedKeyValueMap(
			Map<K, V> map, Function<K, NK> changingKeyFunction,
			Function<V, NV> changingValueFunction) {
		synchronized (map) {
			return map.entrySet().stream().collect(toMap(
					entry -> changingKeyFunction.apply(entry.getKey()),
					entry -> changingValueFunction.apply(entry.getValue())));
		}
	}

	/**
	 * New filtered changed key value map.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param <NK>
	 *            the generic type
	 * @param <NV>
	 *            the generic type
	 * @param map
	 *            the map
	 * @param filter
	 *            the filter
	 * @param changingKeyFunction
	 *            the changing key function
	 * @param changingValueFunction
	 *            the changing value function
	 * @return the map
	 */
	public static <K, V, NK, NV> Map<NK, NV> newFilteredChangedKeyValueMap(
			Map<K, V> map, Predicate<? super Entry<K, V>> filter,
			Function<K, NK> changingKeyFunction,
			Function<V, NV> changingValueFunction) {
		synchronized (map) {
			return map.entrySet().stream().filter(filter).collect(toMap(
					entry -> changingKeyFunction.apply(entry.getKey()),
					entry -> changingValueFunction.apply(entry.getValue())));
		}
	}

	/**
	 * New filtered map.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @param filter
	 *            the filter
	 * @return the map
	 */
	public static <K, V> Map<K, V> newFilteredMap(Map<K, V> map,
			Predicate<? super Entry<K, V>> filter) {
		synchronized (map) {
			return map.entrySet().stream().filter(filter)
					.collect(toMap(Entry::getKey, Entry::getValue));
		}
	}

	/**
	 * Sort.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @param comparator
	 *            the comparator
	 * @return the map
	 */
	public static <K, V> Map<K, V> sort(Map<K, V> map,
			Comparator<K> comparator) {
		synchronized (map) {
			TreeMap<K, V> sortedMap = new TreeMap<>(comparator);
			sortedMap.putAll(map);
			return sortedMap;
		}
	}

	/**
	 * Sort.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @return the map
	 */
	public static <K extends Comparable<K>, V> Map<K, V> sort(Map<K, V> map) {
		synchronized (map) {
			return new TreeMap<>(map);
		}
	}

	/**
	 * Sorted stream.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @param comparator
	 *            the comparator
	 * @return the stream
	 */
	public static <K, V> Stream<Entry<K, V>> sortedStream(Map<K, V> map,
			Comparator<? super Entry<K, V>> comparator) {
		synchronized (map) {
			return map.entrySet().stream().sorted(comparator);
		}
	}

	/**
	 * Sorted stream.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @return the stream
	 */
	public static <K extends Comparable<K>, V> Stream<Entry<K, V>>
			sortedStream(Map<K, V> map) {
		synchronized (map) {
			return map.entrySet().stream().sorted(comparing(Entry::getKey));
		}
	}

	/**
	 * Sort by value.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @return the map
	 */
	public static <K, V extends Comparable<V>> Map<K, V>
			sortByValue(Map<K, V> map) {
		return sort(map, (k1, k2) -> map.get(k1).compareTo(map.get(k2)));
	}

	/**
	 * Sorted stream by value.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            the map
	 * @return the stream
	 */
	public static <K, V extends Comparable<V>> Stream<Entry<K, V>>
			sortedStreamByValue(Map<K, V> map) {
		synchronized (map) {
			return map.entrySet().stream().sorted(comparing(Entry::getValue));
		}
	}

	/**
	 * Checks if is not null or empty.
	 *
	 * @param map
	 *            the map
	 * @return true, if is not null or empty
	 */
	public static boolean isNotNullOrEmpty(Map<?, ?> map) {
		return !JMMap.isNullOrEmpty(map);
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param map
	 *            the map
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(Map<?, ?> map) {
		return map == null || map.size() == 0 ? true : false;
	}
}
