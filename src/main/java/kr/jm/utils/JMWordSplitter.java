package kr.jm.utils;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * The type Jm word splitter.
 */
public class JMWordSplitter {

    /**
     * The constant DefaultSplitPattern.
     */
    public static Pattern DefaultSplitPattern = Pattern.compile("\\W+");

    /**
     * Split as stream stream.
     *
     * @param splitPattern the split pattern
     * @param text         the text
     * @return the stream
     */
    public static Stream<String> splitAsStream(Pattern splitPattern,
            String text) {
        return splitPattern.splitAsStream(text);
    }

    /**
     * Split as list list.
     *
     * @param splitPattern the split pattern
     * @param text         the text
     * @return the list
     */
    public static List<String> splitAsList(Pattern splitPattern, String text) {
        return splitAsStream(splitPattern, text).collect(toList());
    }

    /**
     * Split string [ ].
     *
     * @param splitPattern the split pattern
     * @param text         the text
     * @return the string [ ]
     */
    public static String[] split(Pattern splitPattern, String text) {
        return splitPattern.split(text);
    }

    /**
     * Split as stream stream.
     *
     * @param text the text
     * @return the stream
     */
    public static Stream<String> splitAsStream(String text) {
        return splitAsStream(DefaultSplitPattern, text);
    }

    /**
     * Split as list list.
     *
     * @param text the text
     * @return the list
     */
    public static List<String> splitAsList(String text) {
        return splitAsList(DefaultSplitPattern, text);
    }

    /**
     * Split string [ ].
     *
     * @param text the text
     * @return the string [ ]
     */
    public static String[] split(String text) {
        return split(DefaultSplitPattern, text);
    }

}
