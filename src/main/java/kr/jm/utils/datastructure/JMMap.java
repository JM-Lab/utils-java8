package kr.jm.utils.datastructure;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import kr.jm.utils.helper.JMOptional;

public class JMMap {

	public static <K, V> List<V> removeAllIfByKey(Map<K, V> map,
			Predicate<? super K> predicate) {
		synchronized (map) {
			return map.keySet().stream().filter(predicate).collect(toList())
					.stream().map(map::remove).collect(toList());
		}
	}

	public static <K, V> List<V> removeAllIfByEntry(Map<K, V> map,
			Predicate<? super Entry<K, V>> predicate) {
		synchronized (map) {
			return map.entrySet().stream().filter(predicate).map(Entry::getKey)
					.collect(toList()).stream().map(map::remove)
					.collect(toList());
		}
	}

	public static <K, V> V getOrElse(Map<K, V> map, K key,
			Supplier<V> valueSupplier) {
		return JMOptional.getOptional(map, key).orElseGet(valueSupplier);
	}

	public static <K, V> V getOrPutGetNew(Map<K, V> map, K key,
			Supplier<V> newValueSupplier) {
		synchronized (map) {
			return JMOptional.getOptional(map, key).orElseGet(
					() -> putGetNew(map, key, newValueSupplier.get()));
		}
	}

	public static <K, V> V getOrPutGetNew(Map<K, V> map, K key, V newValue) {
		synchronized (map) {
			return JMOptional.getOptional(map, key)
					.orElseGet(() -> putGetNew(map, key, newValue));
		}
	}

	public static <V, K> V putGetNew(Map<K, V> map, K key, V newValue) {
		synchronized (map) {
			map.put(key, newValue);
			return newValue;
		}
	}

	public static <K, V, NK> Map<NK, V> newChangedKeyMap(Map<K, V> map,
			Function<K, NK> changingKeyFunction) {
		synchronized (map) {
			return map.entrySet().stream()
					.collect(toMap(
							entry -> changingKeyFunction.apply(entry.getKey()),
							Entry::getValue));
		}
	}

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

	public static <K, V, NV> Map<K, NV> newChangedValueMap(Map<K, V> map,
			Function<V, NV> changingValueFunction) {
		synchronized (map) {
			return map.entrySet().stream().collect(toMap(Entry::getKey,
					entry -> changingValueFunction.apply(entry.getValue())));
		}
	}

	public static <K, V, NV> Map<K, NV> newFilteredChangedValueMap(
			Map<K, V> map, Predicate<Entry<K, V>> filter,
			Function<V, NV> changingValueFunction) {
		synchronized (map) {
			return map.entrySet().stream().filter(filter).collect(toMap(
					Entry::getKey,
					entry -> changingValueFunction.apply(entry.getValue())));
		}
	}

	public static <K, V, NK, NV> Map<NK, NV> newChangedKeyValueMap(
			Map<K, V> map, Function<K, NK> changingKeyFunction,
			Function<V, NV> changingValueFunction) {
		synchronized (map) {
			return map.entrySet().stream()
					.collect(toMap(entry -> changingKeyFunction
							.apply(entry.getKey()),
					entry -> changingValueFunction.apply(entry.getValue())));
		}
	}

	public static <K, V, NK, NV> Map<NK, NV> newFilteredChangedKeyValueMap(
			Map<K, V> map, Predicate<? super Entry<K, V>> filter,
			Function<K, NK> changingKeyFunction,
			Function<V, NV> changingValueFunction) {
		synchronized (map) {
			return map.entrySet().stream().filter(filter)
					.collect(toMap(entry -> changingKeyFunction
							.apply(entry.getKey()),
					entry -> changingValueFunction.apply(entry.getValue())));
		}
	}

	public static <K, V> Map<K, V> newFilteredMap(Map<K, V> map,
			Predicate<? super Entry<K, V>> filter) {
		synchronized (map) {
			return map.entrySet().stream().filter(filter)
					.collect(toMap(Entry::getKey, Entry::getValue));
		}
	}

	public static <K, V> Map<K, V> sort(Map<K, V> map,
			Comparator<? super Entry<K, V>> comparator) {
		synchronized (map) {
			return sortedStream(map, comparator)
					.collect(toMap(Entry::getKey, Entry::getValue));
		}
	}

	public static <K extends Comparable<K>, V> Map<K, V> sort(Map<K, V> map) {
		synchronized (map) {
			return sortedStream(map)
					.collect(toMap(Entry::getKey, Entry::getValue));
		}
	}

	public static <K, V> Stream<Entry<K, V>> sortedStream(Map<K, V> map,
			Comparator<? super Entry<K, V>> comparator) {
		synchronized (map) {
			return map.entrySet().stream().sorted(comparator);
		}
	}

	public static <K extends Comparable<K>, V> Stream<Entry<K, V>> sortedStream(
			Map<K, V> map) {
		synchronized (map) {
			return map.entrySet().stream().sorted(comparing(Entry::getKey));
		}
	}

	public static <K, V extends Comparable<V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		synchronized (map) {
			return sortedStreamByValue(map)
					.collect(toMap(Entry::getKey, Entry::getValue));
		}
	}

	public static <K, V extends Comparable<V>> Stream<Entry<K, V>> sortedStreamByValue(
			Map<K, V> map) {
		synchronized (map) {
			return map.entrySet().stream().sorted(comparing(Entry::getValue));
		}
	}

	public static boolean isNotNullOrEmpty(Map<?, ?> map) {
		return !JMMap.isNullOrEmpty(map);
	}

	public static boolean isNullOrEmpty(Map<?, ?> map) {
		return map == null || map.size() == 0 ? true : false;
	}
}
