
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

    /**
     * The Constant PIPE.
     */
    public static final String PIPE = "|";

    /**
     * The Constant EMPTY.
     */
    public static final String EMPTY = "";

    /**
     * The Constant UNDERSCORE.
     */
    public static final String UNDERSCORE = "_";

    /**
     * The Constant HYPHEN.
     */
    public static final String HYPHEN = "-";

    /**
     * The Constant COLON.
     */
    public static final String COLON = ":";

    /**
     * The Constant SEMICOLON.
     */
    public static final String SEMICOLON = ";";

    /**
     * The Constant COMMA.
     */
    public static final String COMMA = ",";

    /**
     * The Constant SPACE.
     */
    public static final String SPACE = " ";

    /**
     * The Constant DOT.
     */
    public static final String DOT = ".";

    /**
     * The Constant LINE_SEPARATOR.
     */
    public static final String LINE_SEPARATOR = System.lineSeparator();
    /**
     * The Constant IPV4Pattern.
     */
    public static final String IPV4Pattern =
            "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    /**
     * The Constant IPV6Pattern.
     */
    public static final String IPV6Pattern =
            "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";
    /**
     * The constant NumberPattern.
     */
    public static final String NumberPattern = "[+-]?\\d+(\\.\\d+)?";
    /**
     * The constant WordPattern.
     */
    public static final String WordPattern = "\\S+";
    private static Pattern numberPattern;
    private static Pattern wordPattern;

    /**
     * Checks if is number.
     *
     * @param numberString the number string
     * @return true, if is number
     */
    public static boolean isNumber(String numberString) {
        return Optional.ofNullable(numberPattern)
                .orElseGet(() -> numberPattern = Pattern.compile(NumberPattern))
                .matcher(numberString).matches();
    }

    /**
     * Is word boolean.
     *
     * @param wordString the word string
     * @return the boolean
     */
    public static boolean isWord(String wordString) {
        return Optional.ofNullable(wordPattern)
                .orElseGet(() -> wordPattern = Pattern.compile(WordPattern))
                .matcher(wordString).matches();
    }

    /**
     * Joining with comma.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithComma(List<String> stringList) {
        return joiningWithDelimiter(COMMA, stringList);
    }

    /**
     * Joining with underscore.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithUnderscore(List<String> stringList) {
        return joiningWithDelimiter(UNDERSCORE, stringList);
    }

    /**
     * Joining with space.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithSpace(List<String> stringList) {
        return joiningWithDelimiter(SPACE, stringList);
    }

    /**
     * Joining with semicolon.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithSemicolon(List<String> stringList) {
        return joiningWithDelimiter(SEMICOLON, stringList);
    }

    /**
     * Joining with dot.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithDot(List<String> stringList) {
        return joiningWithDelimiter(DOT, stringList);
    }

    /**
     * Joining with pipe.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithPipe(List<String> stringList) {
        return joiningWithDelimiter(PIPE, stringList);
    }

    /**
     * Joining with pipe.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithPipe(String... strings) {
        return joiningWithDelimiter(PIPE, strings);
    }

    /**
     * Joining with dot.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithDot(String... strings) {
        return joiningWithDelimiter(DOT, strings);
    }

    /**
     * Joining with comma.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithComma(String... strings) {
        return joiningWithDelimiter(COMMA, strings);
    }

    /**
     * Joining with underscore.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithUnderscore(String... strings) {
        return joiningWithDelimiter(UNDERSCORE, strings);
    }

    /**
     * Joining with space.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithSpace(String... strings) {
        return joiningWithDelimiter(SPACE, strings);
    }

    /**
     * Joining with semicolon.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithSemicolon(String... strings) {
        return joiningWithDelimiter(SEMICOLON, strings);
    }

    /**
     * Checks if is not null or getEmptyStringArray.
     *
     * @param string the string
     * @return true, if is not null or getEmptyStringArray
     */
    public static boolean isNotNullOrEmpty(String string) {
        return !isNullOrEmpty(string);
    }

    /**
     * Checks if is null or getEmptyStringArray.
     *
     * @param string the string
     * @return true, if is null or getEmptyStringArray
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || isEmpty(string);
    }

    /**
     * Checks if is getEmptyStringArray.
     *
     * @param string the string
     * @return true, if is getEmptyStringArray
     */
    public static boolean isEmpty(String string) {
        return EMPTY.equals(string);
    }

    /**
     * Joining.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joining(String... strings) {
        return Arrays.stream(strings).collect(Collectors.joining());
    }

    /**
     * Joining with delimiter.
     *
     * @param delimiter the delimiter
     * @param strings   the strings
     * @return the string
     */
    public static String joiningWithDelimiter(CharSequence delimiter,
            String... strings) {
        return Arrays.stream(strings).collect(Collectors.joining(delimiter));
    }

    /**
     * Joining with delimiter.
     *
     * @param delimiter  the delimiter
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithDelimiter(CharSequence delimiter,
            List<String> stringList) {
        return stringList.stream().collect(Collectors.joining(delimiter));
    }

    /**
     * Split file name into pre suffix.
     *
     * @param fileName the file name
     * @return the string[]
     */
    public static String[] splitFileNameIntoPreSuffix(String fileName) {
        String[] preSuffix = {fileName, EMPTY};
        int dotIndex = fileName.lastIndexOf(DOT);
        if (dotIndex > 0) {
            preSuffix[0] = fileName.substring(0, dotIndex);
            preSuffix[1] = fileName.substring(dotIndex);
        }
        return preSuffix;
    }

    /**
     * Gets the prefix addAll file name.
     *
     * @param fileName the file name
     * @return the prefix addAll file name
     */
    public static String getPrefixOfFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf(DOT);
        return dotIndex > 0 ? fileName.substring(0, dotIndex) : fileName;
    }

    /**
     * Gets the extension.
     *
     * @param fileName the file name
     * @return the extension
     */
    public static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(DOT);
        return dotIndex > 0 ? fileName.substring(dotIndex) : EMPTY;
    }

    /**
     * Truncate.
     *
     * @param string         the string
     * @param maxBytesLength the max bytes length
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
     * @param string         the string
     * @param maxBytesLength the max bytes length
     * @param appendString   the append string
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
     * @param ipOrHostname the ip or hostname
     * @param port         the port
     * @return the string
     */
    public static String buildIpOrHostnamePortPair(String ipOrHostname,
            int port) {
        return ipOrHostname + ":" + port;
    }

    /**
     * Rounded number formatted.
     *
     * @param number       the number
     * @param decimalPoint the decimal point
     * @return the string
     */
    public static String roundedNumberFormat(Double number, int decimalPoint) {
        return String.format("%." + decimalPoint + "f", number);
    }

}
