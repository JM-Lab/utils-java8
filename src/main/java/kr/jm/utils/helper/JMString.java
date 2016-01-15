
package kr.jm.utils.helper;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The Class JMString.
 */
public class JMString {

	/** The Constant EMPTY. */
	public static final String EMPTY = "";

	/** The Constant UNDERSCORE. */
	public static final String UNDERSCORE = "_";

	/** The Constant COLON. */
	public static final String COLON = ":";

	/** The Constant SEMICOLON. */
	public static final String SEMICOLON = ";";

	/** The Constant COMMA. */
	public static final String COMMA = ",";

	/** The Constant SPACE. */
	public static final String SPACE = " ";

	/** The Constant DOT. */
	public static final String DOT = ".";

	/** The Constant NUMBER_PATTERN. */
	public static final Pattern NUMBER_PATTERN = Pattern
			.compile("[+-]?\\d+(\\.\\d+)?");

	/** The Constant LINE_SEPERATOR. */
	public static final String LINE_SEPERATOR = System
			.getProperty("line.separator");

	/**
	 * Checks if is number.
	 *
	 * @param numberString
	 *            the number string
	 * @return true, if is number
	 */
	public static boolean isNumber(String numberString) {
		return NUMBER_PATTERN.matcher(numberString).matches();
	}

	/**
	 * Joining with comma.
	 *
	 * @param stringList
	 *            the string list
	 * @return the string
	 */
	public static String joiningWithComma(List<String> stringList) {
		return joiningWithDelimeter(COMMA, stringList);
	}

	/**
	 * Joining with underscore.
	 *
	 * @param stringList
	 *            the string list
	 * @return the string
	 */
	public static String joiningWithUnderscore(List<String> stringList) {
		return joiningWithDelimeter(UNDERSCORE, stringList);
	}

	/**
	 * Joining with space.
	 *
	 * @param stringList
	 *            the string list
	 * @return the string
	 */
	public static String joiningWithSpace(List<String> stringList) {
		return joiningWithDelimeter(SPACE, stringList);
	}

	/**
	 * Joining with semicolon.
	 *
	 * @param stringList
	 *            the string list
	 * @return the string
	 */
	public static String joiningWithSemicolon(List<String> stringList) {
		return joiningWithDelimeter(SEMICOLON, stringList);
	}

	/**
	 * Joining with comma.
	 *
	 * @param strings
	 *            the strings
	 * @return the string
	 */
	public static String joiningWithComma(String... strings) {
		return joiningWithDelimeter(COMMA, strings);
	}

	/**
	 * Joining with underbar.
	 *
	 * @param strings
	 *            the strings
	 * @return the string
	 */
	public static String joiningWithUnderbar(String... strings) {
		return joiningWithDelimeter(UNDERSCORE, strings);
	}

	/**
	 * Joining with space.
	 *
	 * @param strings
	 *            the strings
	 * @return the string
	 */
	public static String joiningWithSpace(String... strings) {
		return joiningWithDelimeter(SPACE, strings);
	}

	/**
	 * Joining with semicolon.
	 *
	 * @param strings
	 *            the strings
	 * @return the string
	 */
	public static String joiningWithSemicolon(String... strings) {
		return joiningWithDelimeter(SEMICOLON, strings);
	}

	/**
	 * Checks if is not null or empty.
	 *
	 * @param string
	 *            the string
	 * @return true, if is not null or empty
	 */
	public static boolean isNotNullOrEmpty(String string) {
		return !isNullOrEmpty(string);
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param string
	 *            the string
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(String string) {
		return string == null || isEmpty(string);
	}

	/**
	 * Checks if is empty.
	 *
	 * @param string
	 *            the string
	 * @return true, if is empty
	 */
	public static boolean isEmpty(String string) {
		return EMPTY.equals(string);
	}

	/**
	 * Joining.
	 *
	 * @param strings
	 *            the strings
	 * @return the string
	 */
	public static String joining(String... strings) {
		return Arrays.stream(strings).collect(Collectors.joining());
	}

	/**
	 * Joining with delimeter.
	 *
	 * @param delimiter
	 *            the delimiter
	 * @param strings
	 *            the strings
	 * @return the string
	 */
	public static String joiningWithDelimeter(CharSequence delimiter,
			String... strings) {
		return Arrays.stream(strings).collect(Collectors.joining(delimiter));
	}

	/**
	 * Joining with delimeter.
	 *
	 * @param delimeter
	 *            the delimeter
	 * @param stringList
	 *            the string list
	 * @return the string
	 */
	public static String joiningWithDelimeter(CharSequence delimeter,
			List<String> stringList) {
		return stringList.stream().collect(Collectors.joining(delimeter));
	}

	/**
	 * Split file name into pre suffix.
	 *
	 * @param fileName
	 *            the file name
	 * @return the string[]
	 */
	public static String[] splitFileNameIntoPreSuffix(String fileName) {
		String[] preSuffix = { fileName, EMPTY };
		int dotIndex = fileName.lastIndexOf(DOT);
		if (dotIndex > 0) {
			preSuffix[0] = fileName.substring(0, dotIndex);
			preSuffix[1] = fileName.substring(dotIndex);
		}
		return preSuffix;
	}

	/**
	 * Gets the prefix of file name.
	 *
	 * @param fileName
	 *            the file name
	 * @return the prefix of file name
	 */
	public static String getPrefixOfFileName(String fileName) {
		int dotIndex = fileName.lastIndexOf(DOT);
		return dotIndex > 0 ? fileName.substring(0, dotIndex) : fileName;
	}

	/**
	 * Gets the extension.
	 *
	 * @param fileName
	 *            the file name
	 * @return the extension
	 */
	public static String getExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(DOT);
		return dotIndex > 0 ? fileName.substring(dotIndex) : EMPTY;
	}

	/**
	 * Truncate.
	 *
	 * @param string
	 *            the string
	 * @param maxBytesLength
	 *            the max bytes length
	 * @return the string
	 */
	public static String truncate(String string, int maxBytesLength) {
		byte[] stringBytes = string.getBytes();
		return stringBytes.length > maxBytesLength
				? buildNewString(stringBytes, maxBytesLength) : string;
	}

	private static String buildNewString(byte[] stringBytes,
			int maxBytesLength) {
		String string = new String(stringBytes, 0, maxBytesLength);
		return string.getBytes().length > maxBytesLength
				? buildNewString(string.getBytes(), maxBytesLength - 1)
				: string;
	}

	/**
	 * Truncate.
	 *
	 * @param string
	 *            the string
	 * @param maxBytesLength
	 *            the max bytes length
	 * @param appendString
	 *            the append string
	 * @return the string
	 */
	public static String truncate(String string, int maxBytesLength,
			String appendString) {
		return string.getBytes().length > maxBytesLength ? truncate(string,
				maxBytesLength - appendString.getBytes().length) + appendString
				: string;
	}

}
