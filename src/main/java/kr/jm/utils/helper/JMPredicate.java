
package kr.jm.utils.helper;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The Class JMPredicate.
 */
public class JMPredicate {

	/**
	 * Gets the true.
	 *
	 * @param <T>
	 *            the generic type
	 * @return the true
	 */
	public static <T> Predicate<T> getTrue() {
		return target -> TRUE;
	}

	/**
	 * Gets the false.
	 *
	 * @param <T>
	 *            the generic type
	 * @return the false
	 */
	public static <T> Predicate<T> getFalse() {
		return target -> FALSE;
	}

	/**
	 * Gets the boolean.
	 *
	 * @param <T>
	 *            the generic type
	 * @param bool
	 *            the bool
	 * @return the boolean
	 */
	public static <T> Predicate<T> getBoolean(boolean bool) {
		return target -> bool;
	}

	/**
	 * Gets the greater.
	 *
	 * @param target
	 *            the target
	 * @return the greater
	 */
	public static Predicate<Number> getGreater(Number target) {
		return number -> number.doubleValue() > target.doubleValue();
	}

	/**
	 * Gets the greater or equal.
	 *
	 * @param target
	 *            the target
	 * @return the greater or equal
	 */
	public static Predicate<Number> getGreaterOrEqual(Number target) {
		return number -> number.doubleValue() >= target.doubleValue();
	}

	/**
	 * Gets the less.
	 *
	 * @param target
	 *            the target
	 * @return the less
	 */
	public static Predicate<Number> getLess(Number target) {
		return number -> number.doubleValue() < target.doubleValue();
	}

	/**
	 * Gets the less or equal.
	 *
	 * @param target
	 *            the target
	 * @return the less or equal
	 */
	public static Predicate<Number> getLessOrEqual(Number target) {
		return number -> number.doubleValue() <= target.doubleValue();
	}

	/**
	 * Gets the greater size.
	 *
	 * @param target
	 *            the target
	 * @return the greater size
	 */
	public static Predicate<Collection<?>> getGreaterSize(int target) {
		return collection -> getGreater(target).test(collection.size());
	}

	/**
	 * Gets the greater map size.
	 *
	 * @param target
	 *            the target
	 * @return the greater map size
	 */
	public static Predicate<Map<?, ?>> getGreaterMapSize(int target) {
		return map -> getGreater(target).test(map.size());
	}

	/**
	 * Gets the greater length.
	 *
	 * @param target
	 *            the target
	 * @return the greater length
	 */
	public static Predicate<Object[]> getGreaterLength(int target) {
		return array -> getGreater(target).test(array.length);
	}

	/**
	 * Gets the greater or equal size.
	 *
	 * @param target
	 *            the target
	 * @return the greater or equal size
	 */
	public static Predicate<Collection<?>> getGreaterOrEqualSize(int target) {
		return collection -> getGreaterOrEqual(target).test(collection.size());
	}

	/**
	 * Gets the greater map or equal size.
	 *
	 * @param target
	 *            the target
	 * @return the greater map or equal size
	 */
	public static Predicate<Map<?, ?>> getGreaterMapOrEqualSize(int target) {
		return map -> getGreaterOrEqual(target).test(map.size());
	}

	/**
	 * Gets the greater or equal length.
	 *
	 * @param target
	 *            the target
	 * @return the greater or equal length
	 */
	public static Predicate<Object[]> getGreaterOrEqualLength(int target) {
		return array -> getGreaterOrEqual(target).test(array.length);
	}

	/**
	 * Gets the less size.
	 *
	 * @param target
	 *            the target
	 * @return the less size
	 */
	public static Predicate<Collection<?>> getLessSize(int target) {
		return collection -> getLess(target).test(collection.size());
	}

	/**
	 * Gets the less map size.
	 *
	 * @param target
	 *            the target
	 * @return the less map size
	 */
	public static Predicate<Map<?, ?>> getLessMapSize(int target) {
		return map -> getLess(target).test(map.size());
	}

	/**
	 * Gets the less length.
	 *
	 * @param target
	 *            the target
	 * @return the less length
	 */
	public static Predicate<Object[]> getLessLength(int target) {
		return array -> getLess(target).test(array.length);
	}

	/**
	 * Gets the less or equal size.
	 *
	 * @param target
	 *            the target
	 * @return the less or equal size
	 */
	public static Predicate<Collection<?>> getLessOrEqualSize(int target) {
		return collection -> getLessOrEqual(target).test(collection.size());
	}

	/**
	 * Gets the less map or equal size.
	 *
	 * @param target
	 *            the target
	 * @return the less map or equal size
	 */
	public static Predicate<Map<?, ?>> getLessMapOrEqualSize(int target) {
		return map -> getLessOrEqual(target).test(map.size());
	}

	/**
	 * Gets the less or equal length.
	 *
	 * @param target
	 *            the target
	 * @return the less or equal length
	 */
	public static Predicate<Object[]> getLessOrEqualLength(int target) {
		return array -> getLessOrEqual(target).test(array.length);
	}

	/**
	 * Gets the contains.
	 *
	 * @param target
	 *            the target
	 * @return the contains
	 */
	public static Predicate<String> getContains(CharSequence target) {
		return string -> string.contains(target);
	}

	/**
	 * Gets the empty.
	 *
	 * @return the empty
	 */
	public static Predicate<String> getEmpty() {
		return string -> string.isEmpty();
	}

	/**
	 * Gets the end with.
	 *
	 * @param suffix
	 *            the suffix
	 * @return the end with
	 */
	public static Predicate<String> getEndWith(String suffix) {
		return string -> string.endsWith(suffix);
	}

	/**
	 * Gets the start with.
	 *
	 * @param prefix
	 *            the prefix
	 * @return the start with
	 */
	public static Predicate<String> getStartWith(String prefix) {
		return string -> string.startsWith(prefix);
	}

	/**
	 * Gets the matches.
	 *
	 * @param regex
	 *            the regex
	 * @return the matches
	 */
	public static Predicate<String> getMatches(String regex) {
		return string -> string.matches(regex);
	}

	/**
	 * Gets the null.
	 *
	 * @param <T>
	 *            the generic type
	 * @return the null
	 */
	public static <T> Predicate<T> getNull() {
		return target -> target == null;
	}

	/**
	 * Gets the not null.
	 *
	 * @param <T>
	 *            the generic type
	 * @return the not null
	 */
	public static <T> Predicate<T> getNotNull() {
		return target -> target != null;
	}

	/**
	 * Peek to run.
	 *
	 * @param <T>
	 *            the generic type
	 * @param block
	 *            the block
	 * @return the predicate
	 */
	public static <T> Predicate<T> peekToRun(Runnable block) {
		return target -> JMLambda.getTrueAfterRunning(block);
	}

	/**
	 * Peek.
	 *
	 * @param <T>
	 *            the generic type
	 * @param consumer
	 *            the consumer
	 * @return the predicate
	 */
	public static <T> Predicate<T> peek(Consumer<T> consumer) {
		return target -> JMLambda
				.getTrueAfterRunning(() -> consumer.accept(target));
	}

	/**
	 * Gets the equals.
	 *
	 * @param <T>
	 *            the generic type
	 * @param target2
	 *            the target2
	 * @return the equals
	 */
	public static <T> Predicate<T> getEquals(T target2) {
		return target -> target.equals(target2);
	}

}
