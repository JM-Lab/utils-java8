package kr.jm.utils.helper;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static kr.jm.utils.helper.JMPredicate.getIsEmpty;

/**
 * The type Jm optional.
 */
public class JMOptional {

    /**
     * Gets optional.
     *
     * @param string the string
     * @return the optional
     */
    public static Optional<String> getOptional(String string) {
        return Optional.ofNullable(string).filter(getIsEmpty().negate());
    }

    /**
     * Gets optional if true.
     *
     * @param <T>    the type parameter
     * @param bool   the bool
     * @param target the target
     * @return the optional if true
     */
    public static <T> Optional<T> getOptionalIfTrue(boolean bool, T target) {
        return bool ? Optional.ofNullable(target) : Optional.empty();
    }

    /**
     * Gets nullable and filtered optional.
     *
     * @param <T>       the type parameter
     * @param target    the target
     * @param predicate the predicate
     * @return the nullable and filtered optional
     */
    public static <T> Optional<T> getNullableAndFilteredOptional(T target,
            Predicate<T> predicate) {
        return Optional.ofNullable(target).filter(predicate);
    }

    /**
     * Gets optional if exist.
     *
     * @param <T>                   the type parameter
     * @param <R>                   the type parameter
     * @param optional              the optional
     * @param returnBuilderFunction the return builder function
     * @return the optional if exist
     */
    public static <T, R> Optional<R> getOptionalIfExist(Optional<T> optional,
            Function<T, R> returnBuilderFunction) {
        return optional.map(returnBuilderFunction);
    }

    /**
     * Gets optional if exist.
     *
     * @param <T1>                  the type parameter
     * @param <T2>                  the type parameter
     * @param <R>                   the type parameter
     * @param firstOptional         the first optional
     * @param secondOptional        the second optional
     * @param returnBuilderFunction the return builder function
     * @return the optional if exist
     */
    public static <T1, T2, R> Optional<R> getOptionalIfExist(
            Optional<T1> firstOptional, Optional<T2> secondOptional,
            BiFunction<T1, T2, R> returnBuilderFunction) {
        return firstOptional.flatMap(t1 -> secondOptional
                .map(t2 -> returnBuilderFunction.apply(t1, t2)));
    }

    /**
     * Gets optional if exist.
     *
     * @param <T>                   the type parameter
     * @param <C>                   the type parameter
     * @param <R>                   the type parameter
     * @param collection            the collection
     * @param returnBuilderFunction the return builder function
     * @return the optional if exist
     */
    public static <T, C extends Collection<T>, R> Optional<R>
    getOptionalIfExist(C collection,
            Function<C, R> returnBuilderFunction) {
        return getOptional(collection).map(returnBuilderFunction);
    }

    /**
     * Gets optional if exist.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <M>                   the type parameter
     * @param <R>                   the type parameter
     * @param map                   the map
     * @param returnBuilderFunction the return builder function
     * @return the optional if exist
     */
    public static <K, V, M extends Map<K, V>, R> Optional<R>
    getOptionalIfExist(M map, Function<M, R> returnBuilderFunction) {
        return getOptional(map).map(returnBuilderFunction);
    }

    /**
     * Gets value as opt if exist.
     *
     * @param <K>                   the type parameter
     * @param <V>                   the type parameter
     * @param <R>                   the type parameter
     * @param map                   the map
     * @param key                   the key
     * @param returnBuilderFunction the return builder function
     * @return the value as opt if exist
     */
    public static <K, V, R> Optional<R> getValueAsOptIfExist(Map<K, V> map,
            K key, Function<V, R> returnBuilderFunction) {
        return getOptional(map, key).map(returnBuilderFunction::apply);
    }

    /**
     * Gets optional.
     *
     * @param <T>        the type parameter
     * @param <C>        the type parameter
     * @param collection the collection
     * @return the optional
     */
    public static <T, C extends Collection<T>> Optional<C>
    getOptional(C collection) {
        return Optional.ofNullable(collection).filter(c -> c.size() > 0);
    }

    /**
     * Gets optional.
     *
     * @param <T>   the type parameter
     * @param array the array
     * @return the optional
     */
    public static <T> Optional<T[]> getOptional(T[] array) {
        return Optional.ofNullable(array).filter(a -> a.length > 0);
    }

    /**
     * Gets optional.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param <M> the type parameter
     * @param map the map
     * @return the optional
     */
    public static <K, V, M extends Map<K, V>> Optional<M> getOptional(M map) {
        return Optional.ofNullable(map).filter(m -> m.size() > 0);
    }

    /**
     * Gets optional.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param <M> the type parameter
     * @param map the map
     * @param key the key
     * @return the optional
     */
    public static <K, V, M extends Map<K, V>> Optional<V> getOptional(M map,
            K key) {
        return Optional.ofNullable(map).map(m -> m.get(key));
    }

    /**
     * If exist.
     *
     * @param <E>        the type parameter
     * @param <T>        the type parameter
     * @param collection the collection
     * @param consumer   the consumer
     */
    public static <E, T extends Collection<E>> void ifExist(T collection,
            Consumer<T> consumer) {
        getOptional(collection).ifPresent(consumer);
    }

    /**
     * If exist.
     *
     * @param <K>      the type parameter
     * @param <V>      the type parameter
     * @param <M>      the type parameter
     * @param map      the map
     * @param consumer the consumer
     */
    public static <K, V, M extends Map<K, V>> void ifExist(M map,
            Consumer<M> consumer) {
        getOptional(map).ifPresent(consumer);
    }

    /**
     * If not null.
     *
     * @param <T>      the type parameter
     * @param object   the object
     * @param consumer the consumer
     */
    public static <T> void ifNotNull(T object, Consumer<T> consumer) {
        Optional.ofNullable(object).ifPresent(consumer);
    }

    /**
     * Or else get if null t.
     *
     * @param <T>             the type parameter
     * @param target          the target
     * @param elseGetSupplier the else get supplier
     * @return the t
     */
    public static <T> T orElseGetIfNull(T target, Supplier<T> elseGetSupplier) {
        return Optional.ofNullable(target).orElseGet(elseGetSupplier);
    }

    /**
     * Or else if null t.
     *
     * @param <T>        the type parameter
     * @param target     the target
     * @param elseTarget the else target
     * @return the t
     */
    public static <T> T orElseIfNull(T target, T elseTarget) {
        return Optional.ofNullable(target).orElse(elseTarget);
    }

    /**
     * If exist into stream stream.
     *
     * @param <T>        the type parameter
     * @param <C>        the type parameter
     * @param collection the collection
     * @return the stream
     */
    public static <T, C extends Collection<T>> Stream<T>
    ifExistIntoStream(C collection) {
        return getOptional(collection).map(Collection::stream)
                .orElseGet(Stream::empty);
    }

    /**
     * Is present all boolean.
     *
     * @param optionals the optionals
     * @return the boolean
     */
    public static boolean isPresentAll(Optional<?>... optionals) {
        for (Optional<?> optional : optionals)
            if (!optional.isPresent())
                return false;
        return true;
    }

    /**
     * Gets list if is present.
     *
     * @param <T>       the type parameter
     * @param optionals the optionals
     * @return the list if is present
     */
    public static <T> List<T> getListIfIsPresent(Optional<T>... optionals) {
        return Arrays.stream(optionals).filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toList());
    }

    /**
     * Gets or null list.
     *
     * @param <T>       the type parameter
     * @param optionals the optionals
     * @return the or null list
     */
    public static <T> List<T> getOrNullList(Optional<T>... optionals) {
        return Arrays.stream(optionals).map(opt -> opt.orElse(null))
                .collect(Collectors.toList());
    }

    /**
     * Gets object or null list.
     *
     * @param <T>       the type parameter
     * @param optionals the optionals
     * @return the object or null list
     */
    public static <T> List<T> getObjectOrNullList(Optional<T>... optionals) {
        return Arrays.stream(optionals).map(opt -> opt.orElse(null))
                .collect(Collectors.toList());
    }


}
