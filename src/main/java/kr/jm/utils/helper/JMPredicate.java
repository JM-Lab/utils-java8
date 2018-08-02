
package kr.jm.utils.helper;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * The type Jm predicate.
 */
public class JMPredicate {

	/**
	 * Gets true.
	 *
	 * @param <T> the type parameter
	 * @return the true
	 */
	public static <T> Predicate<T> getTrue() {
		return target -> TRUE;
	}

	/**
	 * Gets false.
	 *
	 * @param <T> the type parameter
	 * @return the false
	 */
	public static <T> Predicate<T> getFalse() {
		return target -> FALSE;
	}

	/**
	 * Gets boolean.
	 *
	 * @param <T>  the type parameter
	 * @param bool the bool
	 * @return the boolean
	 */
	public static <T> Predicate<T> getBoolean(boolean bool) {
		return target -> bool;
	}

	/**
	 * Gets greater.
	 *
	 * @param target the target
	 * @return the greater
	 */
	public static Predicate<Number> getGreater(Number target) {
		return number -> number.doubleValue() > target.doubleValue();
	}

	/**
	 * Gets greater or equal.
	 *
	 * @param target the target
	 * @return the greater or equal
	 */
	public static Predicate<Number> getGreaterOrEqual(Number target) {
		return number -> number.doubleValue() >= target.doubleValue();
	}

	/**
	 * Gets less.
	 *
	 * @param target the target
	 * @return the less
	 */
	public static Predicate<Number> getLess(Number target) {
		return number -> number.doubleValue() < target.doubleValue();
	}

	/**
	 * Gets less or equal.
	 *
	 * @param target the target
	 * @return the less or equal
	 */
	public static Predicate<Number> getLessOrEqual(Number target) {
		return number -> number.doubleValue() <= target.doubleValue();
	}

	/**
	 * Gets greater size.
	 *
	 * @param target the target
	 * @return the greater size
	 */
	public static Predicate<Collection<?>> getGreaterSize(int target) {
		return collection -> collection.size() > target;
	}

	/**
	 * Gets greater map size.
	 *
	 * @param target the target
	 * @return the greater map size
	 */
	public static Predicate<Map<?, ?>> getGreaterMapSize(int target) {
		return map -> map.size() > target;
	}

	/**
	 * Gets greater length.
	 *
	 * @param target the target
	 * @return the greater length
	 */
	public static Predicate<Object[]> getGreaterLength(int target) {
		return array -> array.length > target;
	}

	/**
	 * Gets greater or equal size.
	 *
	 * @param target the target
	 * @return the greater or equal size
	 */
	public static Predicate<Collection<?>> getGreaterOrEqualSize(int target) {
		return collection -> collection.size() >= target;
	}

	/**
	 * Gets greater map or equal size.
	 *
	 * @param target the target
	 * @return the greater map or equal size
	 */
	public static Predicate<Map<?, ?>> getGreaterMapOrEqualSize(int target) {
		return map -> map.size() > target;
	}

	/**
	 * Gets greater or equal length.
	 *
	 * @param target the target
	 * @return the greater or equal length
	 */
	public static Predicate<Object[]> getGreaterOrEqualLength(int target) {
		return array -> array.length >= target;
	}

	/**
	 * Gets less size.
	 *
	 * @param target the target
	 * @return the less size
	 */
	public static Predicate<Collection<?>> getLessSize(int target) {
		return collection -> collection.size() < target;
	}

	/**
	 * Gets less map size.
	 *
	 * @param target the target
	 * @return the less map size
	 */
	public static Predicate<Map<?, ?>> getLessMapSize(int target) {
		return map -> map.size() < target;
	}

	/**
	 * Gets less length.
	 *
	 * @param target the target
	 * @return the less length
	 */
	public static Predicate<Object[]> getLessLength(int target) {
		return array -> array.length < target;
	}

	/**
	 * Gets less or equal size.
	 *
	 * @param target the target
	 * @return the less or equal size
	 */
	public static Predicate<Collection<?>> getLessOrEqualSize(int target) {
		return collection -> collection.size() <= target;
	}

	/**
	 * Gets less map or equal size.
	 *
	 * @param target the target
	 * @return the less map or equal size
	 */
	public static Predicate<Map<?, ?>> getLessMapOrEqualSize(int target) {
		return map -> map.size() <= target;
	}

	/**
	 * Gets less or equal length.
	 *
	 * @param target the target
	 * @return the less or equal length
	 */
	public static Predicate<Object[]> getLessOrEqualLength(int target) {
		return array -> array.length <= target;
	}

	/**
	 * Gets equal size.
	 *
	 * @param target the target
	 * @return the equal size
	 */
	public static Predicate<Collection<?>> getEqualSize(int target) {
		return collection -> collection.size() == target;
	}


	/**
	 * Gets contains.
	 *
	 * @param target the target
	 * @return the contains
	 */
	public static Predicate<String> getContains(CharSequence target) {
		return string -> string.contains(target);
	}

	/**
	 * Gets is empty.
	 *
	 * @return the is empty
	 */
	public static Predicate<String> getIsEmpty() {
		return String::isEmpty;
	}

	/**
	 * Gets ends with.
	 *
	 * @param suffix the suffix
	 * @return the ends with
	 */
	public static Predicate<String> getEndsWith(String suffix) {
		return string -> string.endsWith(suffix);
	}

	/**
	 * Gets starts with.
	 *
	 * @param prefix the prefix
	 * @return the starts with
	 */
	public static Predicate<String> getStartsWith(String prefix) {
		return string -> string.startsWith(prefix);
	}

	/**
	 * Gets matches.
	 *
	 * @param regex the regex
	 * @return the matches
	 */
	public static Predicate<String> getMatches(String regex) {
		return string -> string.matches(regex);
	}

	/**
	 * Gets is null.
	 *
	 * @param <T> the type parameter
	 * @return the is null
	 */
	public static <T> Predicate<T> getIsNull() {
		return Objects::isNull;
	}

	/**
	 * Gets is not null.
	 *
	 * @param <T> the type parameter
	 * @return the is not null
	 */
	public static <T> Predicate<T> getIsNotNull() {
		return Objects::nonNull;
	}

	/**
	 * Peek to run predicate.
	 *
	 * @param <T>   the type parameter
	 * @param block the block
	 * @return the predicate
	 */
	public static <T> Predicate<T> peekToRun(Runnable block) {
		return target -> JMLambda.getTrueAfterRunning(block);
	}

	/**
	 * Peek predicate.
	 *
	 * @param <T>      the type parameter
	 * @param consumer the consumer
	 * @return the predicate
	 */
	public static <T> Predicate<T> peek(Consumer<T> consumer) {
		return target -> JMLambda
				.getTrueAfterRunning(() -> consumer.accept(target));
	}

	/**
	 * Peek sopl predicate.
	 *
	 * @param <T> the type parameter
	 * @return the predicate
	 */
	public static <T> Predicate<T> peekSOPL() {
		return target -> JMLambda
				.getTrueAfterRunning(() -> System.out.println(target));
	}

	/**
	 * Gets equals.
	 *
	 * @param <T>     the type parameter
	 * @param target2 the target 2
	 * @return the equals
	 */
	public static <T> Predicate<T> getEquals(T target2) {
		return target -> target.equals(target2);
	}

	/**
	 * Negate predicate.
	 *
	 * @param <T>       the type parameter
	 * @param predicate the predicate
	 * @return the predicate
	 */
	public static <T> Predicate<T> negate(Predicate<T> predicate) {
		return target -> predicate.negate().test(target);
	}

	/**
	 * Negate predicate.
	 *
	 * @param <T>  the type parameter
	 * @param bool the bool
	 * @return the predicate
	 */
	public static <T> Predicate<T> negate(boolean bool) {
		return target -> !bool;
	}

   }
