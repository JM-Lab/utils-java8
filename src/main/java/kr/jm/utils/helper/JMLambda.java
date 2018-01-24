
package kr.jm.utils.helper;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

/**
 * The Class JMLambda.
 */
public class JMLambda {

    /**
     * Partition by.
     *
     * @param <T>        the generic type
     * @param collection the collection
     * @param predicate  the predicate
     * @return the map
     */
    public static <T> Map<Boolean, List<T>>
    partitionBy(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().collect(partitioningBy(predicate));
    }

    /**
     * Group by.
     *
     * @param <T>        the generic type
     * @param <R>        the generic type
     * @param classifier the classifier
     * @return the map
     */
    public static <T, R> Map<R, List<T>> groupBy(Stream<T> stream,
            Function<T, R> classifier) {
        return stream.collect(groupingBy(classifier));
    }

    public static <T, R> Map<R, T> mapBy(Stream<T> stream,
            Function<T, R> classifier) {
        return stream
                .collect(toMap(classifier, identity(), (o, o2) -> o2));
    }

    public static <T> Map<T, Long> countBy(Stream<T> stream) {
        return stream.collect(groupingBy(identity(), counting()));
    }

    public static <T, R> Map<R, List<T>> groupBy(Collection<T> collection,
            Function<T, R> classifier) {
        return groupBy(collection.stream(), classifier);
    }

    public static <T, R> Map<R, T> mapBy(Collection<T> collection,
            Function<T, R> classifier) {
        return mapBy(collection.stream(), classifier);
    }

    public static <T> Map<T, Long> countBy(Collection<T> collection) {
        return countBy(collection.stream());
    }

    public static <T, R> Map<R, T> merge(Stream<Map<R, T>> stream) {
        return stream.collect(HashMap::new, Map::putAll, Map::putAll);
    }

    /**
     * Group by two key.
     *
     * @param <T>         the generic type
     * @param <R1>        the generic type
     * @param <R2>        the generic type
     * @param collection  the collection
     * @param classifier1 the classifier 1
     * @param classifier2 the classifier 2
     * @return the map
     */
    public static <T, R1, R2> Map<R1, Map<R2, T>> groupByTwoKey(
            Collection<T> collection, Function<T, R1> classifier1,
            Function<T, R2> classifier2) {
        return collection.stream()
                .collect(groupingBy(classifier1, toMap(classifier2, t -> t)));
    }

    /**
     * Consume by predicate.
     *
     * @param <T>           the generic type
     * @param collection    the collection
     * @param predicate     the predicate
     * @param trueConsumer  the true consumer
     * @param falseConsumer the false consumer
     */
    public static <T> void consumeByPredicate(Collection<T> collection,
            Predicate<T> predicate, Consumer<T> trueConsumer,
            Consumer<T> falseConsumer) {
        collection.forEach(target -> JMLambda.consumeByBoolean(
                predicate.test(target), target, trueConsumer, falseConsumer));
    }

    /**
     * Consume by predicate in parallel.
     *
     * @param <T>           the generic type
     * @param collection    the collection
     * @param predicate     the predicate
     * @param trueConsumer  the true consumer
     * @param falseConsumer the false consumer
     */
    public static <T> void consumeByPredicateInParallel(
            Collection<T> collection, Predicate<T> predicate,
            Consumer<T> trueConsumer, Consumer<T> falseConsumer) {
        collection.parallelStream()
                .forEach(target -> JMLambda.consumeByBoolean(
                        predicate.test(target), target, trueConsumer,
                        falseConsumer));
    }

    /**
     * Consume by boolean.
     *
     * @param <T>           the generic type
     * @param bool          the bool
     * @param target        the target
     * @param trueConsumer  the true consumer
     * @param falseConsumer the false consumer
     */
    public static <T> void consumeByBoolean(boolean bool, T target,
            Consumer<T> trueConsumer, Consumer<T> falseConsumer) {
        if (bool)
            trueConsumer.accept(target);
        else
            falseConsumer.accept(target);
    }

    /**
     * Consume if not null.
     *
     * @param <T>      the generic type
     * @param target   the target
     * @param consumer the consumer
     */
    public static <T> void consumeIfNotNull(T target, Consumer<T> consumer) {
        Optional.ofNullable(target).ifPresent(consumer);
    }

    /**
     * Consume if true.
     *
     * @param <T>      the generic type
     * @param bool     the bool
     * @param target   the target
     * @param consumer the consumer
     */
    public static <T> void consumeIfTrue(boolean bool, T target,
            Consumer<T> consumer) {
        if (bool)
            consumer.accept(target);
    }

    /**
     * Consume if true.
     *
     * @param <T>          the generic type
     * @param target       the target
     * @param targetTester the target tester
     * @param consumer     the consumer
     */
    public static <T> void consumeIfTrue(T target, Predicate<T> targetTester,
            Consumer<T> consumer) {
        consumeIfTrue(targetTester.test(target), target, consumer);
    }

    /**
     * Consume if true.
     *
     * @param <T>        the generic type
     * @param <U>        the generic type
     * @param bool       the bool
     * @param target1    the target 1
     * @param target2    the target 2
     * @param biConsumer the bi consumer
     */
    public static <T, U> void consumeIfTrue(boolean bool, T target1, U target2,
            BiConsumer<T, U> biConsumer) {
        if (bool)
            biConsumer.accept(target1, target2);
    }

    /**
     * Consume if true.
     *
     * @param <T>          the generic type
     * @param <U>          the generic type
     * @param target1      the target 1
     * @param target2      the target 2
     * @param targetTester the target tester
     * @param biConsumer   the bi consumer
     */
    public static <T, U> void consumeIfTrue(T target1, U target2,
            BiPredicate<T, U> targetTester, BiConsumer<T, U> biConsumer) {
        consumeIfTrue(targetTester.test(target1, target2), target1, target2,
                biConsumer);
    }

    /**
     * Function if true.
     *
     * @param <T>      the generic type
     * @param <R>      the generic type
     * @param bool     the bool
     * @param target   the target
     * @param function the function
     * @return the optional
     */
    public static <T, R> Optional<R> functionIfTrue(boolean bool, T target,
            Function<T, R> function) {
        return supplierIfTrue(bool, () -> function.apply(target));
    }

    /**
     * Bi function if true.
     *
     * @param <T>        the generic type
     * @param <U>        the generic type
     * @param <R>        the generic type
     * @param bool       the bool
     * @param target1    the target 1
     * @param target2    the target 2
     * @param biFunction the bi function
     * @return the optional
     */
    public static <T, U, R> Optional<R> biFunctionIfTrue(boolean bool,
            T target1, U target2, BiFunction<T, U, R> biFunction) {
        return supplierIfTrue(bool, () -> biFunction.apply(target1, target2));
    }

    /**
     * Supplier if true.
     *
     * @param <R>      the generic type
     * @param bool     the bool
     * @param supplier the supplier
     * @return the optional
     */
    public static <R> Optional<R> supplierIfTrue(boolean bool,
            Supplier<R> supplier) {
        return bool ? Optional.ofNullable(supplier.get()) : Optional.empty();
    }

    /**
     * Function by boolean.
     *
     * @param <T>           the generic type
     * @param <R>           the generic type
     * @param bool          the bool
     * @param target        the target
     * @param trueFunction  the true function
     * @param falseFunction the false function
     * @return the r
     */
    public static <T, R> R functionByBoolean(boolean bool, T target,
            Function<T, R> trueFunction, Function<T, R> falseFunction) {
        return bool ? trueFunction.apply(target) : falseFunction.apply(target);
    }

    /**
     * Supplier by boolean.
     *
     * @param <R>           the generic type
     * @param bool          the bool
     * @param trueSupplier  the true supplier
     * @param falseSupplier the false supplier
     * @return the r
     */
    public static <R> R supplierByBoolean(boolean bool,
            Supplier<R> trueSupplier, Supplier<R> falseSupplier) {
        return bool ? trueSupplier.get() : falseSupplier.get();
    }

    /**
     * Change into.
     *
     * @param <T>   the generic type
     * @param <R>   the generic type
     * @param input the input
     * @return the function
     */
    public static <T, R> Function<T, R> changeInto(R input) {
        return t -> input;
    }

    /**
     * Supplier if null.
     *
     * @param <R>      the generic type
     * @param target   the target
     * @param supplier the supplier
     * @return the r
     */
    public static <R> R supplierIfNull(R target, Supplier<R> supplier) {
        return Optional.ofNullable(target).orElseGet(supplier);
    }

    /**
     * Gets the true after running.
     *
     * @param block the block
     * @return the true after running
     */
    public static boolean getTrueAfterRunning(Runnable block) {
        block.run();
        return true;
    }

    /**
     * Gets the false after running.
     *
     * @param block the block
     * @return the false after running
     */
    public static boolean getFalseAfterRunning(Runnable block) {
        block.run();
        return false;
    }

    /**
     * Run if true.
     *
     * @param bool  the bool
     * @param block the block
     */
    public static void runIfTrue(boolean bool, Runnable block) {
        if (bool)
            block.run();
    }

    /**
     * Run by boolean.
     *
     * @param bool       the bool
     * @param trueBlock  the true block
     * @param falseBlock the false block
     */
    public static void runByBoolean(boolean bool, Runnable trueBlock,
            Runnable falseBlock) {
        if (bool)
            trueBlock.run();
        else
            falseBlock.run();
    }

    /**
     * Gets the self.
     *
     * @param <T> the generic type
     * @return the self
     */
    public static <T> Function<T, T> getSelf() {
        return t -> t;
    }

    /**
     * Gets the supplier.
     *
     * @param <T>    the generic type
     * @param target the target
     * @return the supplier
     */
    public static <T> Supplier<T> getSupplier(T target) {
        return () -> target;
    }

    /**
     * Consume and get self.
     *
     * @param <T>            the generic type
     * @param target         the target
     * @param targetConsumer the target consumer
     * @return the t
     */
    public static <T> T consumeAndGetSelf(T target,
            Consumer<T> targetConsumer) {
        targetConsumer.accept(target);
        return target;
    }

    public static <R> R runAndReturn(Runnable runnable,
            Supplier<R> returnSupplier) {
        runnable.run();
        return returnSupplier.get();
    }

    public static <R> R runAndReturn(Runnable runnable, R returnObject) {
        return runAndReturn(runnable, () -> returnObject);
    }
}
