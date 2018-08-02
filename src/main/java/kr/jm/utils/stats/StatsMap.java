package kr.jm.utils.stats;

import kr.jm.utils.datastructure.JMMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static kr.jm.utils.stats.StatsField.*;

/**
 * The type Stats map.
 */
public class StatsMap implements Map<StatsField, Number> {

    private final Map<StatsField, Number> statsFieldNumberMap;

    /**
     * Instantiates a new Stats map.
     */
    protected StatsMap() {
        this(Collections.emptyList());
    }

    /**
     * Instantiates a new Stats map.
     *
     * @param numberList the number list
     */
    public StatsMap(List<Number> numberList) {
        this(NumberSummaryStatistics.of(numberList).getStatsFieldMap());
    }

    /**
     * Instantiates a new Stats map.
     *
     * @param statsFieldNumberMap the stats field number map
     */
    public StatsMap(Map<StatsField, Number> statsFieldNumberMap) {
        this.statsFieldNumberMap = new ConcurrentHashMap<>(statsFieldNumberMap);
    }

    @Override
    public int size() {return statsFieldNumberMap.size();}

    @Override
    public boolean isEmpty() {return statsFieldNumberMap.isEmpty();}

    @Override
    public boolean containsKey(Object key) {
        return statsFieldNumberMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return statsFieldNumberMap.containsValue(value);
    }

    @Override
    public Number get(Object key) {return statsFieldNumberMap.get(key);}

    @Override
    public Number put(StatsField key, Number value) {
        return statsFieldNumberMap.put(key, value);
    }

    @Override
    public Number remove(Object key) {return statsFieldNumberMap.remove(key);}

    @Override
    public void putAll(
            Map<? extends StatsField, ? extends Number> m) {
        statsFieldNumberMap.putAll(m);
    }

    @Override
    public void clear() {statsFieldNumberMap.clear();}

    @Override
    public Set<StatsField> keySet() {return statsFieldNumberMap.keySet();}

    @Override
    public Collection<Number> values() {return statsFieldNumberMap.values();}

    @Override
    public Set<Entry<StatsField, Number>> entrySet() {
        return statsFieldNumberMap.entrySet();
    }

    @Override
    public boolean equals(Object o) {return statsFieldNumberMap.equals(o);}

    @Override
    public int hashCode() {return statsFieldNumberMap.hashCode();}

    @Override
    public String toString() {
        return statsFieldNumberMap.toString();
    }

    /**
     * Merge stats map.
     *
     * @param statsMap the stats map
     * @return the stats map
     */
    public StatsMap merge(StatsMap statsMap) {
        synchronized (statsFieldNumberMap) {
            statsFieldNumberMap
                    .put(count, statsFieldNumberMap.get(count).longValue() +
                            statsMap.get(count).longValue());
            statsFieldNumberMap
                    .put(sum, statsFieldNumberMap.get(sum).doubleValue() +
                            statsMap.get(sum).doubleValue());
            statsFieldNumberMap
                    .put(min, statsFieldNumberMap.get(min).doubleValue() <
                            statsMap.get(min)
                                    .doubleValue() ? statsFieldNumberMap
                            .get(min).doubleValue() : statsMap.get(min)
                            .doubleValue());
            statsFieldNumberMap
                    .put(max, statsFieldNumberMap.get(max).doubleValue() >
                            statsMap.get(max)
                                    .doubleValue() ? statsFieldNumberMap
                            .get(max).doubleValue() : statsMap.get(max)
                            .doubleValue());
            statsFieldNumberMap
                    .put(avg, statsFieldNumberMap.get(sum).doubleValue() /
                            statsFieldNumberMap.get(count).doubleValue());
            return this;
        }
    }

    /**
     * Gets stats field string map.
     *
     * @return the stats field string map
     */
    public Map<String, Number> getStatsFieldStringMap() {
        return changeIntoStatsFieldStringMap(this);
    }

    /**
     * Change into stats field string map map.
     *
     * @param statsMap the stats map
     * @return the map
     */
    public static Map<String, Number> changeIntoStatsFieldStringMap(
            StatsMap statsMap) {
        return JMMap.newChangedKeyMap(statsMap, StatsField::name);
    }

    /**
     * Change into stats map stats map.
     *
     * @param statsFieldStringMap the stats field string map
     * @return the stats map
     */
    public static StatsMap changeIntoStatsMap(
            Map<String, Number> statsFieldStringMap) {
        return new StatsMap(JMMap.newChangedKeyMap(statsFieldStringMap,
                StatsField::valueOf));
    }

}
