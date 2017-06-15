
package kr.jm.utils.helper;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Stream;

import static kr.jm.utils.helper.JMPredicate.getIsEmpty;

/**
 * The Class JMOptional.
 */
public class JMOptional {

	/**
	 * Gets the optional.
	 *
	 * @param string
	 *            the string
	 * @return the optional
	 */
	public static Optional<String> getOptional(String string) {
		return Optional.ofNullable(string).filter(getIsEmpty().negate());
	}

	/**
	 * Gets the optional if true.
	 *
	 * @param <T>
	 *            the generic type
	 * @param bool
	 *            the bool
	 * @param target
	 *            the target
	 * @return the optional if true
	 */
	public static <T> Optional<T> getOptionalIfTrue(boolean bool, T target) {
		return bool ? Optional.ofNullable(target) : Optional.empty();
	}

	/**
	 * Gets the nullable and filtered optional.
	 *
	 * @param <T>
	 *            the generic type
	 * @param target
	 *            the target
	 * @param predicate
	 *            the predicate
	 * @return the nullable and filtered optional
	 */
	public static <T> Optional<T> getNullableAndFilteredOptional(T target,
			Predicate<T> predicate) {
		return Optional.ofNullable(target).filter(predicate);
	}

	/**
	 * Gets the optional if exist.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <R>
	 *            the generic type
	 * @param optional
	 *            the optional
	 * @param returnBuilderFunction
	 *            the return builder function
	 * @return the optional if exist
	 */
	public static <T, R> Optional<R> getOptionalIfExist(Optional<T> optional,
			Function<T, R> returnBuilderFunction) {
		return optional.map(returnBuilderFunction::apply);
	}

	/**
	 * Gets the optional if exist.
	 *
	 * @param <T1>
	 *            the generic type
	 * @param <T2>
	 *            the generic type
	 * @param <R>
	 *            the generic type
	 * @param firstOptional
	 *            the first optional
	 * @param secondOptional
	 *            the second optional
	 * @param returnBuilderFunction
	 *            the return builder function
	 * @return the optional if exist
	 */
	public static <T1, T2, R> Optional<R> getOptionalIfExist(
			Optional<T1> firstOptional, Optional<T2> secondOptional,
			BiFunction<T1, T2, R> returnBuilderFunction) {
		return firstOptional.flatMap(t1 -> secondOptional
				.map(t2 -> returnBuilderFunction.apply(t1, t2)));
	}

	/**
	 * Gets the optional if exist.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <C>
	 *            the generic type
	 * @param <R>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param returnBuilderFunction
	 *            the return builder function
	 * @return the optional if exist
	 */
	public static <T, C extends Collection<T>, R> Optional<R>
			getOptionalIfExist(C collection,
					Function<C, R> returnBuilderFunction) {
		return getOptional(collection).map(returnBuilderFunction);
	}

	/**
	 * Gets the optional if exist.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param <M>
	 *            the generic type
	 * @param <R>
	 *            the generic type
	 * @param map
	 *            the map
	 * @param returnBuilderFunction
	 *            the return builder function
	 * @return the optional if exist
	 */
	public static <K, V, M extends Map<K, V>, R> Optional<R>
			getOptionalIfExist(M map, Function<M, R> returnBuilderFunction) {
		return getOptional(map).map(returnBuilderFunction);
	}

	/**
	 * Gets the value as opt if exist.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param <M>
	 *            the generic type
	 * @param <R>
	 *            the generic type
	 * @param map
	 *            the map
	 * @param key
	 *            the key
	 * @param returnBuilderFunction
	 *            the return builder function
	 * @return the value as opt if exist
	 */
	public static <K, V, M extends Map<K, V>, R> Optional<R>
			getValueAsOptIfExist(Map<K, V> map, K key,
					Function<V, R> returnBuilderFunction) {
		return getOptional(map, key).map(returnBuilderFunction::apply);
	}

	/**
	 * Gets the optional.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <C>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @return the optional
	 */
	public static <T, C extends Collection<T>> Optional<C>
			getOptional(C collection) {
		return Optional.<C> ofNullable(collection).filter(c -> c.size() > 0);
	}

	/**
	 * Gets the optional.
	 *
	 * @param <T>
	 *            the generic type
	 * @param array
	 *            the array
	 * @return the optional
	 */
	public static <T> Optional<T[]> getOptional(T[] array) {
		return Optional.ofNullable(array).filter(a -> a.length > 0);
	}

	/**
	 * Gets the optional.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param <M>
	 *            the generic type
	 * @param map
	 *            the map
	 * @return the optional
	 */
	public static <K, V, M extends Map<K, V>> Optional<M> getOptional(M map) {
		return Optional.<M> ofNullable(map).filter(m -> m.size() > 0);
	}

	/**
	 * Gets the optional.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param <M>
	 *            the generic type
	 * @param map
	 *            the map
	 * @param key
	 *            the key
	 * @return the optional
	 */
	public static <K, V, M extends Map<K, V>> Optional<V> getOptional(M map,
			K key) {
		return Optional.<M> ofNullable(map).map(m -> m.get(key));
	}

	/**
	 * If exist.
	 *
	 * @param <E>
	 *            the element type
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param consumer
	 *            the consumer
	 */
	public static <E, T extends Collection<E>> void ifExist(T collection,
			Consumer<T> consumer) {
		getOptional(collection).ifPresent(consumer);
	}

	/**
	 * If exist.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param <M>
	 *            the generic type
	 * @param map
	 *            the map
	 * @param consumer
	 *            the consumer
	 */
	public static <K, V, M extends Map<K, V>> void ifExist(M map,
			Consumer<M> consumer) {
		getOptional(map).ifPresent(consumer);
	}

	/**
	 * If not null.
	 *
	 * @param <T>
	 *            the generic type
	 * @param object
	 *            the object
	 * @param consumer
	 *            the consumer
	 */
	public static <T> void ifNotNull(T object, Consumer<T> consumer) {
		Optional.ofNullable(object).ifPresent(consumer);
	}

	/**
	 * Or else get if null.
	 *
	 * @param <T>
	 *            the generic type
	 * @param target
	 *            the target
	 * @param elseGetSupplier
	 *            the else get supplier
	 * @return the t
	 */
	public static <T> T orElseGetIfNull(T target, Supplier<T> elseGetSupplier) {
		return Optional.ofNullable(target).orElseGet(elseGetSupplier);
	}

	/**
	 * Or else if null.
	 *
	 * @param <T>
	 *            the generic type
	 * @param target
	 *            the target
	 * @param elseTarget
	 *            the else target
	 * @return the t
	 */
	public static <T> T orElseIfNull(T target, T elseTarget) {
		return Optional.ofNullable(target).orElse(elseTarget);
	}

	/**
	 * If exist into stream.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <C>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @return the stream
	 */
	public static <T, C extends Collection<T>> Stream<T>
			ifExistIntoStream(C collection) {
		return getOptional(collection).map(Collection::stream)
				.orElseGet(Stream::empty);
	}

	/**
	 * Checks if is present all.
	 *
	 * @param optionals
	 *            the optionals
	 * @return true, if is present all
	 */
	public static boolean isPresentAll(Optional<?>... optionals) {
		for (Optional<?> optional : optionals)
			if (!optional.isPresent())
				return false;
		return true;
	}

}
