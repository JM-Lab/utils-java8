
package kr.jm.utils.helper;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * The Class JMLambda.
 */
public class JMLambda {

	/**
	 * Partition by.
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param predicate
	 *            the predicate
	 * @return the map
	 */
	public static <T> Map<Boolean, List<T>>
			partitionBy(Collection<T> collection, Predicate<T> predicate) {
		return collection.stream().collect(partitioningBy(predicate));
	}

	/**
	 * Group by.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <R>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param classifier
	 *            the classifier
	 * @return the map
	 */
	public static <T, R> Map<R, List<T>> groupBy(Collection<T> collection,
			Function<T, R> classifier) {
		return collection.stream().collect(groupingBy(classifier));
	}

	/**
	 * Group by.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <R1>
	 *            the generic type
	 * @param <R2>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param classifier1
	 *            the classifier1
	 * @param classifier2
	 *            the classifier2
	 * @return the map
	 */
	public static <T, R1, R2> Map<R1, Map<R2, T>> groupBy(
			Collection<T> collection, Function<T, R1> classifier1,
			Function<T, R2> classifier2) {
		return collection.stream()
				.collect(groupingBy(classifier1, toMap(classifier2, r -> r)));
	}

	/**
	 * Consume by predicate.
	 *
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param predicate
	 *            the predicate
	 * @param trueConsumer
	 *            the true consumer
	 * @param falseConsumer
	 *            the false consumer
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
	 * @param <T>
	 *            the generic type
	 * @param collection
	 *            the collection
	 * @param predicate
	 *            the predicate
	 * @param trueConsumer
	 *            the true consumer
	 * @param falseConsumer
	 *            the false consumer
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
	 * @param <T>
	 *            the generic type
	 * @param bool
	 *            the bool
	 * @param target
	 *            the target
	 * @param trueConsumer
	 *            the true consumer
	 * @param falseConsumer
	 *            the false consumer
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
	 * @param <T>
	 *            the generic type
	 * @param target
	 *            the target
	 * @param consumer
	 *            the consumer
	 */
	public static <T> void consumeIfNotNull(T target, Consumer<T> consumer) {
		Optional.ofNullable(target).ifPresent(consumer);
	}

	/**
	 * Consume if true.
	 *
	 * @param <T>
	 *            the generic type
	 * @param bool
	 *            the bool
	 * @param target
	 *            the target
	 * @param consumer
	 *            the consumer
	 */
	public static <T> void consumeIfTrue(boolean bool, T target,
			Consumer<T> consumer) {
		if (bool)
			consumer.accept(target);
	}

	/**
	 * Consume if true.
	 *
	 * @param <T>
	 *            the generic type
	 * @param target
	 *            the target
	 * @param targetTester
	 *            the target tester
	 * @param consumer
	 *            the consumer
	 */
	public static <T> void consumeIfTrue(T target, Predicate<T> targetTester,
			Consumer<T> consumer) {
		consumeIfTrue(targetTester.test(target), target, consumer);
	}

	/**
	 * Consume if true.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <U>
	 *            the generic type
	 * @param bool
	 *            the bool
	 * @param target1
	 *            the target1
	 * @param target2
	 *            the target2
	 * @param biConsumer
	 *            the bi consumer
	 */
	public static <T, U> void consumeIfTrue(boolean bool, T target1, U target2,
			BiConsumer<T, U> biConsumer) {
		if (bool)
			biConsumer.accept(target1, target2);
	}

	/**
	 * Consume if ture.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <U>
	 *            the generic type
	 * @param target1
	 *            the target1
	 * @param target2
	 *            the target2
	 * @param targetTester
	 *            the target tester
	 * @param biConsumer
	 *            the bi consumer
	 */
	public static <T, U> void consumeIfTure(T target1, U target2,
			BiPredicate<T, U> targetTester, BiConsumer<T, U> biConsumer) {
		consumeIfTrue(targetTester.test(target1, target2), target1, target2,
				biConsumer);
	}

	/**
	 * Apply if true.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <R>
	 *            the generic type
	 * @param bool
	 *            the bool
	 * @param target
	 *            the target
	 * @param function
	 *            the function
	 * @return the optional
	 */
	public static <T, R> Optional<R> applyIfTrue(boolean bool, T target,
			Function<T, R> function) {
		return bool ? Optional.ofNullable(function.apply(target))
				: Optional.empty();
	}

	/**
	 * Apply if true.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <U>
	 *            the generic type
	 * @param <R>
	 *            the generic type
	 * @param bool
	 *            the bool
	 * @param target1
	 *            the target1
	 * @param target2
	 *            the target2
	 * @param biFunction
	 *            the bi function
	 * @return the optional
	 */
	public static <T, U, R> Optional<R> applyIfTrue(boolean bool, T target1,
			U target2, BiFunction<T, U, R> biFunction) {
		return bool ? Optional.ofNullable(biFunction.apply(target1, target2))
				: Optional.empty();
	}

	/**
	 * Gets the if true.
	 *
	 * @param <R>
	 *            the generic type
	 * @param bool
	 *            the bool
	 * @param supplier
	 *            the supplier
	 * @return the if true
	 */
	public static <R> Optional<R> getIfTrue(boolean bool,
			Supplier<R> supplier) {
		return bool ? Optional.ofNullable(supplier.get()) : Optional.empty();
	}

	/**
	 * Apply by boolean.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <R>
	 *            the generic type
	 * @param bool
	 *            the bool
	 * @param target
	 *            the target
	 * @param trueFunction
	 *            the true function
	 * @param falseFunction
	 *            the false function
	 * @return the r
	 */
	public static <T, R> R applyByBoolean(boolean bool, T target,
			Function<T, R> trueFunction, Function<T, R> falseFunction) {
		return bool ? trueFunction.apply(target) : falseFunction.apply(target);
	}

	/**
	 * Gets the by boolean.
	 *
	 * @param <R>
	 *            the generic type
	 * @param bool
	 *            the bool
	 * @param trueSupply
	 *            the true supply
	 * @param falseSupply
	 *            the false supply
	 * @return the by boolean
	 */
	public static <R> R getByBoolean(boolean bool, Supplier<R> trueSupply,
			Supplier<R> falseSupply) {
		return bool ? trueSupply.get() : falseSupply.get();
	}

	/**
	 * Change into.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <R>
	 *            the generic type
	 * @param input
	 *            the input
	 * @return the function
	 */
	public static <T, R> Function<T, R> changeInto(R input) {
		return t -> input;
	}

	/**
	 * Gets the else if null.
	 *
	 * @param <R>
	 *            the generic type
	 * @param target
	 *            the target
	 * @param elseSupplier
	 *            the else supplier
	 * @return the else if null
	 */
	public static <R> R getElseIfNull(R target, Supplier<R> elseSupplier) {
		return Optional.ofNullable(target).orElseGet(elseSupplier);
	}

	/**
	 * Gets the true after running.
	 *
	 * @param block
	 *            the block
	 * @return the true after running
	 */
	public static boolean getTrueAfterRunning(Runnable block) {
		block.run();
		return true;
	}

	/**
	 * Gets the false after running.
	 *
	 * @param block
	 *            the block
	 * @return the false after running
	 */
	public static boolean getFalseAfterRunning(Runnable block) {
		block.run();
		return false;
	}

	/**
	 * Run if true.
	 *
	 * @param bool
	 *            the bool
	 * @param block
	 *            the block
	 */
	public static void runIfTrue(boolean bool, Runnable block) {
		if (bool)
			block.run();
	}

	/**
	 * Run by boolean.
	 *
	 * @param bool
	 *            the bool
	 * @param trueBlock
	 *            the true block
	 * @param falseBlock
	 *            the false block
	 */
	public static void runByBoolean(boolean bool, Runnable trueBlock,
			Runnable falseBlock) {
		if (bool)
			trueBlock.run();
		else
			falseBlock.run();
	}

}
