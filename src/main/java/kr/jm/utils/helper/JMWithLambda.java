package kr.jm.utils.helper;

import static java.util.stream.Collectors.partitioningBy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class JMWithLambda {

	public static <T> Map<Boolean, List<T>> partitionBy(
			Collection<T> collection, Predicate<T> predicate) {
		return collection.stream().collect(partitioningBy(predicate));
	}

	public static <T> void consumeByPredicate(Collection<T> collection,
			Predicate<T> predicate, Consumer<T> trueConsumer,
			Consumer<T> falseConsumer) {
		collection.forEach(target -> JMWithLambda.consumeByBoolean(
				predicate.test(target), target, trueConsumer, falseConsumer));
	}

	public static <T> void consumeByPredicateInParallel(
			Collection<T> collection, Predicate<T> predicate,
			Consumer<T> trueConsumer, Consumer<T> falseConsumer) {
		collection.parallelStream().forEach(
				target -> JMWithLambda.consumeByBoolean(predicate.test(target),
						target, trueConsumer, falseConsumer));
	}

	public static <T> void consumeByBoolean(boolean bool, T target,
			Consumer<T> trueConsumer, Consumer<T> falseConsumer) {
		if (bool)
			trueConsumer.accept(target);
		else
			falseConsumer.accept(target);
	}

	public static <T> void ifTureConsume(T target, Predicate<T> targetTester,
			Consumer<T> consumer) {
		if (targetTester.test(target))
			consumer.accept(target);
	}

	public static <T, U> void ifTureConsume(T target1, U target2,
			BiPredicate<T, U> targetTester, BiConsumer<T, U> biConsumer) {
		if (targetTester.test(target1, target2))
			biConsumer.accept(target1, target2);
	}

	public static <T, R> R applyByBoolean(boolean bool, T target,
			Function<T, R> trueFunction, Function<T, R> falseFunction) {
		return bool ? trueFunction.apply(target) : falseFunction.apply(target);
	}

}
