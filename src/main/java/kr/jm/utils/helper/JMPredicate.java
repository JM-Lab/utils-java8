package kr.jm.utils.helper;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public class JMPredicate {

	public static <T> Predicate<T> getTrue() {
		return b -> TRUE;
	}

	public static <T> Predicate<T> getFalse() {
		return b -> FALSE;
	}

	public static Predicate<Number> getGreater(Number target) {
		return number -> number.doubleValue() > target.doubleValue();
	}

	public static Predicate<Number> getGreaterOrEqual(Number target) {
		return number -> number.doubleValue() >= target.doubleValue();
	}

	public static Predicate<Number> getLess(Number target) {
		return number -> number.doubleValue() < target.doubleValue();
	}

	public static Predicate<Number> getLessOrEqual(Number target) {
		return number -> number.doubleValue() <= target.doubleValue();
	}

	public static Predicate<Collection<?>> getGreaterSize(int target) {
		return collection -> getGreater(target).test(collection.size());
	}

	public static Predicate<Map<?, ?>> getGreaterMapSize(int target) {
		return map -> getGreater(target).test(map.size());
	}

	public static Predicate<Object[]> getGreaterLength(int target) {
		return array -> getGreater(target).test(array.length);
	}

	public static Predicate<Collection<?>> getGreaterOrEqualSize(int target) {
		return collection -> getGreaterOrEqual(target).test(collection.size());
	}

	public static Predicate<Map<?, ?>> getGreaterMapOrEqualSize(int target) {
		return map -> getGreaterOrEqual(target).test(map.size());
	}

	public static Predicate<Object[]> getGreaterOrEqualLength(int target) {
		return array -> getGreaterOrEqual(target).test(array.length);
	}

	public static Predicate<Collection<?>> getLessSize(int target) {
		return collection -> getLess(target).test(collection.size());
	}

	public static Predicate<Map<?, ?>> getLessMapSize(int target) {
		return map -> getLess(target).test(map.size());
	}

	public static Predicate<Object[]> getLessLength(int target) {
		return array -> getLess(target).test(array.length);
	}

	public static Predicate<Collection<?>> getLessOrEqualSize(int target) {
		return collection -> getLessOrEqual(target).test(collection.size());
	}

	public static Predicate<Map<?, ?>> getLessMapOrEqualSize(int target) {
		return map -> getLessOrEqual(target).test(map.size());
	}

	public static Predicate<Object[]> getLessOrEqualLength(int target) {
		return array -> getLessOrEqual(target).test(array.length);
	}

	public static Predicate<String> getContains(CharSequence target) {
		return string -> string.contains(target);
	}

	public static Predicate<String> getEmpty() {
		return string -> string.isEmpty();
	}

	public static Predicate<String> getEndWith(String suffix) {
		return string -> string.endsWith(suffix);
	}

	public static Predicate<String> getStartWith(String prefix) {
		return string -> string.startsWith(prefix);
	}

	public static Predicate<String> getMatchs(String regex) {
		return string -> string.matches(regex);
	}

}
