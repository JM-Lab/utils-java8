package kr.jm.utils.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Jm string.
 */
public class JMString {

    /**
     * The constant PIPE.
     */
    public static final String PIPE = "|";

    /**
     * The constant EMPTY.
     */
    public static final String EMPTY = "";

    /**
     * The constant UNDERSCORE.
     */
    public static final String UNDERSCORE = "_";

    /**
     * The constant HYPHEN.
     */
    public static final String HYPHEN = "-";

    /**
     * The constant COLON.
     */
    public static final String COLON = ":";

    /**
     * The constant SEMICOLON.
     */
    public static final String SEMICOLON = ";";

    /**
     * The constant COMMA.
     */
    public static final String COMMA = ",";

    /**
     * The constant SPACE.
     */
    public static final String SPACE = " ";

    /**
     * The constant DOT.
     */
    public static final String DOT = ".";

    /**
     * The constant LINE_SEPARATOR.
     */
    public static final String LINE_SEPARATOR = System.lineSeparator();
    /**
     * The constant IPV4Pattern.
     */
    public static final String IPV4Pattern =
            "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    /**
     * The constant IPV6Pattern.
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
     * Is number boolean.
     *
     * @param numberString the number string
     * @return the boolean
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
     * Joining string.
     *
     * @param stringStream the string stream
     * @return the string
     */
    public static String joining(Stream<String> stringStream) {
        return stringStream.collect(Collectors.joining());
    }

    /**
     * Joining with string.
     *
     * @param stringStream the string stream
     * @param delimiter    the delimiter
     * @return the string
     */
    public static String joiningWith(Stream<String> stringStream,
            CharSequence delimiter) {
        return stringStream.collect(Collectors.joining(delimiter));
    }

    /**
     * Joining with comma string.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithComma(List<String> stringList) {
        return joiningWithDelimiter(COMMA, stringList);
    }

    /**
     * Joining with underscore string.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithUnderscore(List<String> stringList) {
        return joiningWithDelimiter(UNDERSCORE, stringList);
    }

    /**
     * Joining with space string.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithSpace(List<String> stringList) {
        return joiningWithDelimiter(SPACE, stringList);
    }

    /**
     * Joining with semicolon string.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithSemicolon(List<String> stringList) {
        return joiningWithDelimiter(SEMICOLON, stringList);
    }

    /**
     * Joining with dot string.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithDot(List<String> stringList) {
        return joiningWithDelimiter(DOT, stringList);
    }

    /**
     * Joining with pipe string.
     *
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithPipe(List<String> stringList) {
        return joiningWithDelimiter(PIPE, stringList);
    }

    /**
     * Joining with pipe string.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithPipe(String... strings) {
        return joiningWithDelimiter(PIPE, strings);
    }

    /**
     * Joining with dot string.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithDot(String... strings) {
        return joiningWithDelimiter(DOT, strings);
    }

    /**
     * Joining with comma string.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithComma(String... strings) {
        return joiningWithDelimiter(COMMA, strings);
    }

    /**
     * Joining with underscore string.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithUnderscore(String... strings) {
        return joiningWithDelimiter(UNDERSCORE, strings);
    }

    /**
     * Joining with space string.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithSpace(String... strings) {
        return joiningWithDelimiter(SPACE, strings);
    }

    /**
     * Joining with semicolon string.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joiningWithSemicolon(String... strings) {
        return joiningWithDelimiter(SEMICOLON, strings);
    }

    /**
     * Is not null or empty boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public static boolean isNotNullOrEmpty(String string) {
        return !isNullOrEmpty(string);
    }

    /**
     * Is null or empty boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || isEmpty(string);
    }

    /**
     * Is empty boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public static boolean isEmpty(String string) {
        return EMPTY.equals(string);
    }

    /**
     * Joining string.
     *
     * @param strings the strings
     * @return the string
     */
    public static String joining(String... strings) {
        return joining(Arrays.stream(strings));
    }

    /**
     * Joining with delimiter string.
     *
     * @param delimiter the delimiter
     * @param strings   the strings
     * @return the string
     */
    public static String joiningWithDelimiter(CharSequence delimiter,
            String... strings) {
        return joiningWith(Arrays.stream(strings), delimiter);
    }

    /**
     * Joining with delimiter string.
     *
     * @param delimiter  the delimiter
     * @param stringList the string list
     * @return the string
     */
    public static String joiningWithDelimiter(CharSequence delimiter,
            List<String> stringList) {
        return joiningWith(stringList.stream(), delimiter);
    }

    /**
     * Split file name into pre suffix string [ ].
     *
     * @param fileName the file name
     * @return the string [ ]
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
     * Gets prefix of file name.
     *
     * @param fileName the file name
     * @return the prefix of file name
     */
    public static String getPrefixOfFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf(DOT);
        return dotIndex > 0 ? fileName.substring(0, dotIndex) : fileName;
    }

    /**
     * Gets extension.
     *
     * @param fileName the file name
     * @return the extension
     */
    public static String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(DOT);
        return dotIndex > 0 ? fileName.substring(dotIndex) : EMPTY;
    }

    /**
     * Truncate string.
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
     * Truncate string.
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
     * Build ip or hostname port pair string.
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
     * Rounded number format string.
     *
     * @param number       the number
     * @param decimalPoint the decimal point
     * @return the string
     */
    public static String roundedNumberFormat(Double number, int decimalPoint) {
        return String.format("%." + decimalPoint + "f", number);
    }

}
