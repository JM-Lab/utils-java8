package kr.jm.utils.stats.generator;

import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.stats.NumberSummaryStatistics;
import kr.jm.utils.stats.StatsField;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

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
    public static Map<String, Number> buildStatsMap(
            Collection<Number> numberCollection) {
        return JMMap.newChangedKeyMap(
                getOptional(numberCollection).map(NumberSummaryStatistics::of)
                        .map(NumberSummaryStatistics::getStatsFieldMap)
                        .orElseGet(Collections::emptyMap), StatsField::name);
    }

}
