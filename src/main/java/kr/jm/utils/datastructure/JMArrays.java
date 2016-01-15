
package kr.jm.utils.datastructure;

import java.util.Arrays;
import java.util.Comparator;

import kr.jm.utils.helper.JMString;

/**
 * The Class JMArrays.
 */
public class JMArrays {

	/**
	 * Builds the array.
	 *
	 * @param <E>
	 *            the element type
	 * @param objects
	 *            the objects
	 * @return the e[]
	 */
	public static <E> E[] buildArray(
			@SuppressWarnings("unchecked") E... objects) {
		return objects;
	}

	/**
	 * Sort.
	 *
	 * @param <V>
	 *            the value type
	 * @param array
	 *            the array
	 * @return the v[]
	 */
	public static <V extends Comparable<V>> V[] sort(V[] array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Sort.
	 *
	 * @param <V>
	 *            the value type
	 * @param array
	 *            the array
	 * @param comparator
	 *            the comparator
	 * @return the v[]
	 */
	public static <V> V[] sort(V[] array, Comparator<? super V> comparator) {
		Arrays.sort(array, comparator);
		return array;
	}

	/**
	 * Gets the last.
	 *
	 * @param <V>
	 *            the value type
	 * @param array
	 *            the array
	 * @return the last
	 */
	public static <V> V getLast(V[] array) {
		return JMArrays.isNullOrEmpty(array) ? null : array[array.length - 1];
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param <V>
	 *            the value type
	 * @param array
	 *            the array
	 * @return true, if is null or empty
	 */
	public static <V> boolean isNullOrEmpty(V[] array) {
		return array == null || array.length == 0 ? true : false;
	}

	/**
	 * Checks if is not null or empty.
	 *
	 * @param <V>
	 *            the value type
	 * @param array
	 *            the array
	 * @return true, if is not null or empty
	 */
	public static <V> boolean isNotNullOrEmpty(V[] array) {
		return !isNullOrEmpty(array);
	}

	/**
	 * Builds the array from csv.
	 *
	 * @param csvString
	 *            the csv string
	 * @return the string[]
	 */
	public static String[] buildArrayFromCsv(String csvString) {
		return csvString.split(JMString.COMMA);
	}

	/**
	 * Builds the array with delimeter.
	 *
	 * @param stringWithDelimeter
	 *            the string with delimeter
	 * @param delimeter
	 *            the delimeter
	 * @return the string[]
	 */
	public static String[] buildArrayWithDelimeter(String stringWithDelimeter,
			String delimeter) {
		return stringWithDelimeter.split(delimeter);
	}

}
