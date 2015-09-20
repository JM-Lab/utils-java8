package kr.jm.utils.datastructure;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import kr.jm.utils.helper.JMOptional;
import kr.jm.utils.helper.JMString;

public class JMCollections {

	public static <T, C extends Collection<T>> void ifNotNullOrEmptyConsume(
			C collection, Consumer<C> consumer) {
		JMOptional.of(collection).ifPresent(consumer);
	}

	public static boolean isNotNullOrEmpty(Collection<?> collection) {
		return !isNullOrEmpty(collection);
	}

	public static boolean isNullOrEmpty(Collection<?> collection) {
		return collection == null || collection.size() == 0 ? true : false;
	}

	public static <T, L extends List<T>> T getLast(List<T> list) {
		return isNullOrEmpty(list) ? null : list.get(list.size() - 1);
	}

	public static <T extends Comparable<T>> List<T> sort(List<T> list) {
		Collections.sort(list);
		return list;
	}

	public static <T> List<T> sort(List<T> list,
			Comparator<? super T> comparator) {
		Collections.sort(list, comparator);
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <E> List<E> buildList(E... objects) {
		return Arrays.asList(objects);
	}

	public static List<String> buildListFromCsv(String csvString) {
		return buildList(JMArrays.buildArrayFromCsv(csvString));
	}

	public static List<String> buildListWithDelimeter(
			String stringWithDelimeter, String delimeter) {
		return buildList(JMArrays.buildArrayWithDelimeter(stringWithDelimeter,
				delimeter));
	}

	public static List<String> buildListByLine(String stringByLine) {
		return buildListWithDelimeter(stringByLine, JMString.lineSeperator);
	}

}
