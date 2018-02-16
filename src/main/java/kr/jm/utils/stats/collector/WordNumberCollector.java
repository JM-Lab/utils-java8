package kr.jm.utils.stats.collector;

import kr.jm.utils.helper.JMString;

import java.util.List;
import java.util.Map;

/**
 * The type Word number collector.
 */
public class WordNumberCollector {
    private String collectorId;
    private long timestamp;
    private Map<String, Object> metaMap;
    private WordItemCollector wordCollector;
    private NumberItemCollector numberCollector;

    /**
     * Instantiates a new Word number collector.
     *
     * @param collectorId the collector id
     */
    public WordNumberCollector(String collectorId) {
        this(collectorId, System.currentTimeMillis());
    }

    /**
     * Instantiates a new Word number collector.
     *
     * @param collectorId the collector id
     * @param timestamp   the timestamp
     */
    public WordNumberCollector(String collectorId, long timestamp) {
        this(collectorId, timestamp, null);
    }

    /**
     * Instantiates a new Word number collector.
     *
     * @param collectorId the collector id
     * @param timestamp   the timestamp
     * @param metaMap     the meta map
     */
    public WordNumberCollector(String collectorId, long timestamp,
            Map<String, Object> metaMap) {
        this.collectorId = collectorId;
        this.timestamp = timestamp;
        this.wordCollector = new WordItemCollector();
        this.numberCollector = new NumberItemCollector();
        this.metaMap = metaMap;
    }

    private WordNumberCollector() {}

    /**
     * Gets collector id.
     *
     * @return the collector id
     */
    public String getCollectorId() {
        return collectorId;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Gets meta map.
     *
     * @return the meta map
     */
    public Map<String, Object> getMetaMap() {
        return metaMap;
    }

    /**
     * Add word word number collector.
     *
     * @param key  the key
     * @param word the word
     * @return the word number collector
     */
    public WordNumberCollector addWord(String key, String word) {
        wordCollector.add(key, word);
        return this;
    }

    /**
     * Add word list word number collector.
     *
     * @param key      the key
     * @param wordList the word list
     * @return the word number collector
     */
    public WordNumberCollector addWordList(String key, List<String> wordList) {
        wordCollector.addAll(key, wordList);
        return this;
    }

    /**
     * Build word count metrics map map.
     *
     * @return the map
     */
    public Map<String, Map<String, Long>> buildWordCountMetricsMap() {
        return wordCollector.extractCollectingMap();
    }

    /**
     * Add number word number collector.
     *
     * @param key    the key
     * @param number the number
     * @return the word number collector
     */
    public WordNumberCollector addNumber(String key, Number number) {
        numberCollector.add(key, number);
        return this;
    }

    /**
     * Add number list word number collector.
     *
     * @param key        the key
     * @param numberList the number list
     * @return the word number collector
     */
    public WordNumberCollector addNumberList(String key,
            List<Number> numberList) {
        numberCollector.addAll(key, numberList);
        return this;
    }

    /**
     * Build number stats metrics map map.
     *
     * @return the map
     */
    public Map<String, Map<String, Number>> buildNumberStatsMetricsMap() {
        return numberCollector.extractCollectingMap();
    }

    /**
     * Add data word number collector.
     *
     * @param key  the key
     * @param data the data
     * @return the word number collector
     */
    public WordNumberCollector addData(String key, String data) {
        if (JMString.isNumber(data))
            addNumber(key, Double.valueOf(data));
        else
            addWord(key, data);
        return this;
    }

    /**
     * Merge word number collector.
     *
     * @param wordNumberCollector the word number collector
     * @return the word number collector
     */
    public WordNumberCollector merge(WordNumberCollector wordNumberCollector) {
        wordCollector.merge(wordNumberCollector.wordCollector);
        numberCollector.merge(wordNumberCollector.numberCollector);
        return this;
    }

    /**
     * Merge all word number collector.
     *
     * @param wordNumberCollectorList the word number collector list
     * @return the word number collector
     */
    public WordNumberCollector mergeAll(
            List<WordNumberCollector> wordNumberCollectorList) {
        for (WordNumberCollector wordNumberCollector : wordNumberCollectorList)
            merge(wordNumberCollector);
        return this;
    }


    /**
     * Gets word collector.
     *
     * @return the word collector
     */
    public WordItemCollector getWordCollector() {return this.wordCollector;}

    /**
     * Gets number collector.
     *
     * @return the number collector
     */
    public NumberItemCollector getNumberCollector() {return this.numberCollector;}

    public String toString() {
        return "WordNumberCollector(collectorId=" + this.getCollectorId() +
                ", timestamp=" + this.getTimestamp() + ", metaMap=" +
                this.getMetaMap() + ", wordCollector=" +
                this.getWordCollector() + ", numberCollector=" +
                this.getNumberCollector() + ")";
    }
}
