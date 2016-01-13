package kr.jm.utils.helper;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class JMOptional {

	public static Optional<String> getOptional(String string) {
		return getOptionalIfTrue(string, s -> !JMString.isEmpty(s));
	}

	public static <T> Optional<T> getOptionalIfTrue(T target,
			Predicate<T> predicate) {
		return Optional.<T> ofNullable(target).filter(predicate);
	}

	public static <T, R> Optional<R> getOptionalIfExist(Optional<T> optional,
			Function<T, R> returnBuilderFunction) {
		return optional.map(t -> returnBuilderFunction.apply(t));
	}

	public static <T1, T2, R> Optional<R> getOptionalIfExist(
			Optional<T1> firstOptional, Optional<T2> secondOptional,
			BiFunction<T1, T2, R> returnBuilderFunction) {
		return firstOptional.flatMap(t1 -> secondOptional
				.map(t2 -> returnBuilderFunction.apply(t1, t2)));
	}

	public static <T, C extends Collection<T>, R> Optional<R> getOptionalIfExist(
			C collection, Function<C, R> returnBuilderFunction) {
		return getOptional(collection).map(returnBuilderFunction);
	}

	public static <K, V, M extends Map<K, V>, R> Optional<R> getOptionalIfExist(
			M map, Function<M, R> returnBuilderFunction) {
		return getOptional(map).map(returnBuilderFunction);
	}

	public static <K, V, M extends Map<K, V>, R> Optional<R> getValueAsOptIfExist(
			Map<K, V> map, K key, Function<V, R> returnBuilderFunction) {
		return getOptional(map, key).map(returnBuilderFunction::apply);
	}

	public static <T, C extends Collection<T>> Optional<C> getOptional(
			C collection) {
		return Optional.<C> ofNullable(collection).filter(c -> c.size() > 0);
	}

	public static <K, V, M extends Map<K, V>> Optional<M> getOptional(M map) {
		return Optional.<M> ofNullable(map).filter(m -> m.size() > 0);
	}

	public static <K, V, M extends Map<K, V>> Optional<V> getOptional(M map,
			K key) {
		return Optional.<V> ofNullable(map.get(key));
	}

	public static <E, T extends Collection<E>> void ifExist(T collection,
			Consumer<T> consumer) {
		getOptional(collection).ifPresent(consumer);
	}

	public static <K, V, M extends Map<K, V>> void ifExist(M map,
			Consumer<M> consumer) {
		getOptional(map).ifPresent(consumer);
	}

	public static <T> T orElseGetIfNull(T target, Supplier<T> elseGetSupplier) {
		return Optional.ofNullable(target).orElseGet(elseGetSupplier);
	}

	public static <T> T orElseIfNull(T target, T elseTarget) {
		return Optional.ofNullable(target).orElse(elseTarget);
	}

}
