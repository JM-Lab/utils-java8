package kr.jm.utils.stats.collector;

import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.stats.NumberSummaryStatistics;
import kr.jm.utils.stats.StatsMap;

import java.util.List;
import java.util.Map;

/**
 * The type Number item collector.
 */
public class NumberItemCollector extends AbstractItemCollector<Number> {

    /**
     * Extract collecting stats map map.
     *
     * @param dataList the data list
     * @return the map
     */
    public Map<String, StatsMap> extractCollectingStatsMap(
            List<Number> dataList) {
        return JMMap.newChangedValueMap(this, this::buildStatsMap);
    }

    private StatsMap buildStatsMap(List<Number> numberList) {
        return new StatsMap(
                NumberSummaryStatistics.of(numberList).getStatsFieldMap());
    }

    @Override
    public Map<String, Map<String, Number>> extractCollectingMap() {
        return JMMap.newChangedValueMap(this, this::convertToMetricMap);
    }

    private Map<String, Number> convertToMetricMap(List<Number> dataList) {
        return buildStatsMap(dataList).getStatsFieldStringMap();
    }

}
