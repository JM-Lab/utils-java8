package kr.jm.utils.stats.generator;

import kr.jm.utils.helper.JMFiles;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static kr.jm.utils.JMWordSplitter.DefaultSplitPattern;
import static kr.jm.utils.helper.JMLambda.countBy;

/**
 * The type Word count generator.
 */
public class WordCountGenerator {

    /**
     * Build count map map.
     *
     * @param pattern the pattern
     * @param path    the path
     * @return the map
     */
    public static Map<String, Long> buildCountMap(Pattern pattern, Path path) {
        return buildCountMap(
                JMFiles.getLineStream(path).flatMap(pattern::splitAsStream));
    }

    /**
     * Build count map map.
     *
     * @param pattern the pattern
     * @param text    the text
     * @return the map
     */
    public static Map<String, Long> buildCountMap(Pattern pattern,
            String text) {
        return buildCountMap(pattern.splitAsStream(text));
    }

    /**
     * Build count map map.
     *
     * @param wordList the word list
     * @return the map
     */
    public static Map<String, Long> buildCountMap(List<String> wordList) {
        return buildCountMap(wordList.stream());
    }

    /**
     * Build count map map.
     *
     * @param wordStream the word stream
     * @return the map
     */
    public static Map<String, Long> buildCountMap(Stream<String> wordStream) {
        return countBy(wordStream);
    }

    /**
     * Build count map map.
     *
     * @param path the path
     * @return the map
     */
    public Map<String, Long> buildCountMap(Path path) {
        return buildCountMap(DefaultSplitPattern, path);
    }

    /**
     * Build count map map.
     *
     * @param text the text
     * @return the map
     */
    public Map<String, Long> buildCountMap(String text) {
        return buildCountMap(DefaultSplitPattern, text);
    }

}
