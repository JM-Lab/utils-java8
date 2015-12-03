package kr.jm.utils.datastructure;

import java.util.Arrays;
import java.util.Comparator;

import kr.jm.utils.helper.JMString;

public class JMArrays {

	public static <E> E[]
			buildArray(@SuppressWarnings("unchecked") E... objects) {
		return objects;
	}

	public static <V extends Comparable<V>> V[] sort(V[] array) {
		Arrays.sort(array);
		return array;
	}

	public static <V> V[] sort(V[] array, Comparator<? super V> comparator) {
		Arrays.sort(array, comparator);
		return array;
	}

	public static <V> V getLast(V[] array) {
		return JMArrays.isNullOrEmpty(array) ? null : array[array.length - 1];
	}

	public static <V> boolean isNullOrEmpty(V[] array) {
		return array == null || array.length == 0 ? true : false;
	}

	public static <V> boolean isNotNullOrEmpty(V[] array) {
		return !isNullOrEmpty(array);
	}

	public static String[] buildArrayFromCsv(String csvString) {
		return csvString.split(JMString.COMMA);
	}

	public static String[] buildArrayWithDelimeter(String stringWithDelimeter,
			String delimeter) {
		return stringWithDelimeter.split(delimeter);
	}

}
