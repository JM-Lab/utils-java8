package kr.jm.utils.helper;

import static java.util.stream.Collectors.partitioningBy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class JMWithLambda {

	public static <T> Map<Boolean, List<T>> partitionBy(
			Collection<T> collection, Predicate<? super T> predicate) {
		return collection.stream().collect(partitioningBy(predicate));
	}

	public static <T> void consumeByPredicate(Collection<T> collection,
			Predicate<? super T> predicate, Consumer<? super T> trueConsumer,
			Consumer<? super T> falseConsumer) {
		collection.forEach(target -> JMWithLambda.consumeByBoolean(
				predicate.test(target), target, trueConsumer, falseConsumer));
	}

	public static <T> void consumeByPredicateInParallel(
			Collection<T> collection, Predicate<? super T> predicate,
			Consumer<? super T> trueConsumer, Consumer<? super T> falseConsumer) {
		collection.parallelStream().forEach(
				target -> JMWithLambda.consumeByBoolean(predicate.test(target),
						target, trueConsumer, falseConsumer));
	}

	public static <T> void consumeByBoolean(boolean bool, T target,
			Consumer<? super T> trueConsumer, Consumer<? super T> falseConsumer) {
		if (bool)
			trueConsumer.accept(target);
		else
			falseConsumer.accept(target);
	}

}
