package kr.jm.utils.stats.collector;

import kr.jm.utils.collections.JMListMap;

import java.util.Map;

/**
 * The type Abstract item collector.
 *
 * @param <Item> the type parameter
 */
public abstract class AbstractItemCollector<Item> extends
        JMListMap<String, Item> {
    /**
     * Extract collecting map map.
     *
     * @param <N> the type parameter
     * @return the map
     */
    public abstract <N extends Number> Map<String, Map<String, N>> extractCollectingMap();
}
