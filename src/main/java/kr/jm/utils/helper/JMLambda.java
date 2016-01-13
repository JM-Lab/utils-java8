package kr.jm.utils.helper;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;

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

public class JMLambda {

	public static <T> Map<Boolean, List<T>> partitionBy(
			Collection<T> collection, Predicate<T> predicate) {
		return collection.stream().collect(partitioningBy(predicate));
	}

	public static <T, R> Map<R, List<T>> groupBy(Collection<T> collection,
			Function<T, R> classifier) {
		return collection.stream().collect(groupingBy(classifier));
	}

	public static <T> void consumeByPredicate(Collection<T> collection,
			Predicate<T> predicate, Consumer<T> trueConsumer,
			Consumer<T> falseConsumer) {
		collection.forEach(target -> JMLambda.consumeByBoolean(
				predicate.test(target), target, trueConsumer, falseConsumer));
	}

	public static <T> void consumeByPredicateInParallel(
			Collection<T> collection, Predicate<T> predicate,
			Consumer<T> trueConsumer, Consumer<T> falseConsumer) {
		collection.parallelStream()
				.forEach(target -> JMLambda.consumeByBoolean(
						predicate.test(target), target, trueConsumer,
						falseConsumer));
	}

	public static <T> void consumeByBoolean(boolean bool, T target,
			Consumer<T> trueConsumer, Consumer<T> falseConsumer) {
		if (bool)
			trueConsumer.accept(target);
		else
			falseConsumer.accept(target);
	}

	public static <T> void consumeIfNotNull(T target, Consumer<T> consumer) {
		Optional.ofNullable(target).ifPresent(consumer);
	}

	public static <T> void consumeIfTrue(boolean bool, T target,
			Consumer<T> consumer) {
		if (bool)
			consumer.accept(target);
	}

	public static <T> void consumeIfTrue(T target, Predicate<T> targetTester,
			Consumer<T> consumer) {
		consumeIfTrue(targetTester.test(target), target, consumer);
	}

	public static <T, U> void consumeIfTrue(boolean bool, T target1, U target2,
			BiConsumer<T, U> biConsumer) {
		if (bool)
			biConsumer.accept(target1, target2);
	}

	public static <T, U> void consumeIfTure(T target1, U target2,
			BiPredicate<T, U> targetTester, BiConsumer<T, U> biConsumer) {
		consumeIfTrue(targetTester.test(target1, target2), target1, target2,
				biConsumer);
	}

	public static <T, R> Optional<R> applyIfTrue(boolean bool, T target,
			Function<T, R> function) {
		return bool ? Optional.ofNullable(function.apply(target))
				: Optional.empty();
	}

	public static <T, U, R> Optional<R> applyIfTrue(boolean bool, T target1,
			U target2, BiFunction<T, U, R> biFunction) {
		return bool ? Optional.ofNullable(biFunction.apply(target1, target2))
				: Optional.empty();
	}

	public static <R> Optional<R> getIfTrue(boolean bool,
			Supplier<R> supplier) {
		return bool ? Optional.ofNullable(supplier.get()) : Optional.empty();
	}

	public static <T, R> R applyByBoolean(boolean bool, T target,
			Function<T, R> trueFunction, Function<T, R> falseFunction) {
		return bool ? trueFunction.apply(target) : falseFunction.apply(target);
	}

	public static <R> R getByBoolean(boolean bool, Supplier<R> trueSupply,
			Supplier<R> falseSupply) {
		return bool ? trueSupply.get() : falseSupply.get();
	}

	public static <T, R> Function<T, R> changeInto(R input) {
		return t -> input;
	}

	public static <R> R getElseIfNull(R target, Supplier<R> elseSupplier) {
		return Optional.ofNullable(target).orElseGet(elseSupplier);
	}

	public static boolean getTrueAfterRunning(Runnable block) {
		block.run();
		return true;
	}

	public static boolean getFalseAfterRunning(Runnable block) {
		block.run();
		return false;
	}

	public static void runIfTrue(boolean bool, Runnable block) {
		if (bool)
			block.run();
	}

	public static void runByBoolean(boolean bool, Runnable trueBlock,
			Runnable falseBlock) {
		if (bool)
			trueBlock.run();
		else
			falseBlock.run();
	}

}
