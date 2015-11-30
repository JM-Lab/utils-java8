package kr.jm.utils.helper;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class JMLambda {

	public static <T> Map<Boolean, List<T>>
			partitionBy(Collection<T> collection, Predicate<T> predicate) {
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

	public static <T> void consumeIfTure(T target, Predicate<T> targetTester,
			Consumer<T> consumer) {
		if (targetTester.test(target))
			consumer.accept(target);
	}

	public static <T, U> void consumeIfTure(T target1, U target2,
			BiPredicate<T, U> targetTester, BiConsumer<T, U> biConsumer) {
		if (targetTester.test(target1, target2))
			biConsumer.accept(target1, target2);
	}

	public static <T, R> R appliedByBoolean(boolean bool, T target,
			Function<T, R> trueFunction, Function<T, R> falseFunction) {
		return bool ? trueFunction.apply(target) : falseFunction.apply(target);
	}

	public static <R> R getByBoolean(boolean bool, Supplier<R> trueSupply,
			Supplier<R> falseSupply) {
		return bool ? trueSupply.get() : falseSupply.get();
	}

	public static <R> R getSelf(R input) {
		return input;
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

}
