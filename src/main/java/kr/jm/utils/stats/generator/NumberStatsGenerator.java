package kr.jm.utils.stats.generator;

import kr.jm.utils.stats.NumberSummaryStatistics;
import kr.jm.utils.stats.StatsField;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static kr.jm.utils.helper.JMOptional.getOptional;

/**
 * The type Number stats generator.
 */
public class NumberStatsGenerator {

    /**
     * Build stats map map.
     *
     * @param numberCollection the number collection
     * @return the map
     */
    public static Map<StatsField, Number> buildStatsMap(
            Collection<Number> numberCollection) {
        return getOptional(numberCollection).map(NumberSummaryStatistics::of)
                .map(NumberStatsGenerator::buildStatsMap)
                .orElseGet(Collections::emptyMap);
    }

    /**
     * Build stats map map.
     *
     * @param numberSummaryStatistics the number summary statistics
     * @return the map
     */
    public static Map<StatsField, Number> buildStatsMap(
            NumberSummaryStatistics numberSummaryStatistics) {
        return unmodifiableMap(numberSummaryStatistics.getStatsFieldMap());
    }

}
