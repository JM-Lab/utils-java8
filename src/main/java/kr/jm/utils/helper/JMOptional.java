package kr.jm.utils.helper;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class JMOptional {

	public static <T, R> Optional<R> getOptionalIfExist(Optional<T> optional,
			Function<T, R> returnBuilderFunction) {
		return optional.map(t -> returnBuilderFunction.apply(t));
	}

	public static <T1, T2, R> Optional<R> getBiOptionalIfExist(
			Optional<T1> firstOptional, Optional<T2> secondOptional,
			BiFunction<T1, T2, R> returnBuilderFunction) {
		return firstOptional.flatMap(t1 -> secondOptional
				.map(t2 -> returnBuilderFunction.apply(t1, t2)));
	}

	public static <T, C extends Collection<T>, R> Optional<R> getOptionalIfExist(
			C collection, Function<C, R> returnBuilderFunction) {
		return of(collection).map(returnBuilderFunction);
	}

	public static <K, V, M extends Map<K, V>, R> Optional<R> getOptionalIfExist(
			M map, Function<M, R> returnBuilderFunction) {
		return of(map).map(returnBuilderFunction);
	}

	public static <K, V, M extends Map<K, V>, R> Optional<R> getValueAsOptIfExist(
			Map<K, V> map, K key, Function<V, R> returnBuilderFunction) {
		return getOptional(map, key).map(returnBuilderFunction::apply);
	}

	public static <T, C extends Collection<T>> Optional<C> of(C collection) {
		return Optional.ofNullable(collection).filter(c -> c.size() > 0);
	}

	public static <K, V, M extends Map<K, V>> Optional<M> of(M map) {
		return Optional.ofNullable(map).filter(m -> m.size() > 0);
	}

	public static <K, V, M extends Map<K, V>> Optional<V> getOptional(M map,
			K key) {
		return Optional.ofNullable(map.get(key));
	}

	public static <E, T extends Collection<E>> void ifExist(T collection,
			Consumer<T> consumer) {
		of(collection).ifPresent(consumer);
	}

	public static <K, V, M extends Map<K, V>> void ifExist(M map,
			Consumer<M> consumer) {
		of(map).ifPresent(consumer);
	}

	public static Optional<String> getOptional(String string) {
		return Optional.ofNullable(string).filter(s -> !JMString.isEmpty(s));
	}

}
