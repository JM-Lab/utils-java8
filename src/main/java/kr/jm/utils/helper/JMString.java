
package kr.jm.utils.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The Class JMString.
 */
public class JMString {

	/** The Constant PIPE. */
	public static final String PIPE = "|";

	/** The Constant EMPTY. */
	public static final String EMPTY = "";

	/** The Constant UNDERSCORE. */
	public static final String UNDERSCORE = "_";

	/** The Constant HYPHEN. */
	public static final String HYPHEN = "-";

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

	/** The Constant LINE_SEPERATOR. */
	public static final String LINE_SEPERATOR =
			System.getProperty("line.separator");

	private static final String NumberPattern = "[+-]?\\d+(\\.\\d+)?";

	/** The Constant IPV4Pattern. */
	public static final String IPV4Pattern =
			"(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";

	/** The Constant IPV6Pattern. */
	public static final String IPV6Pattern =
			"([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

	private static Pattern numberPattern;

	/**
	 * Checks if is number.
	 *
	 * @param numberString
	 *            the number string
	 * @return true, if is number
	 */
	public static boolean isNumber(String numberString) {
		return Optional.ofNullable(numberPattern)
				.orElseGet(() -> numberPattern = Pattern.compile(NumberPattern))
				.matcher(numberString).matches();
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
	 * Joining with dot.
	 *
	 * @param stringList
	 *            the string list
	 * @return the string
	 */
	public static String joiningWithDot(List<String> stringList) {
		return joiningWithDelimeter(DOT, stringList);
	}

	/**
	 * Joining with pipe.
	 *
	 * @param stringList
	 *            the string list
	 * @return the string
	 */
	public static String joiningWithPipe(List<String> stringList) {
		return joiningWithDelimeter(PIPE, stringList);
	}

	/**
	 * Joining with pipe.
	 *
	 * @param strings
	 *            the strings
	 * @return the string
	 */
	public static String joiningWithPipe(String... strings) {
		return joiningWithDelimeter(PIPE, strings);
	}

	/**
	 * Joining with dot.
	 *
	 * @param strings
	 *            the strings
	 * @return the string
	 */
	public static String joiningWithDot(String... strings) {
		return joiningWithDelimeter(DOT, strings);
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
	 * Joining with underscore.
	 *
	 * @param strings
	 *            the strings
	 * @return the string
	 */
	public static String joiningWithUnderscore(String... strings) {
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
				? new String(stringBytes, 0, maxBytesLength - 1) : string;
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

	/**
	 * Builds the ip or hostname port pair.
	 *
	 * @param ipOrHostname
	 *            the ip or hostname
	 * @param port
	 *            the port
	 * @return the string
	 */
	public static String buildIpOrHostnamePortPair(String ipOrHostname,
			int port) {
		return ipOrHostname + ":" + port;
	}

	/**
	 * Rounded number format.
	 *
	 * @param number
	 *            the number
	 * @param decimalPoint
	 *            the decimal point
	 * @return the string
	 */
	public static String roundedNumberFormat(Double number, int decimalPoint) {
		return String.format("%." + decimalPoint + "f", number);
	}

}
