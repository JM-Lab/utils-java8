package kr.jm.utils.stats.collector;

import kr.jm.utils.datastructure.JMMap;
import kr.jm.utils.stats.generator.WordCountGenerator;

import java.util.Map;

/**
 * The type Word item collector.
 */
public class WordItemCollector extends AbstractItemCollector<String> {

    @Override
    public Map<String, Map<String, Long>> extractCollectingMap() {
        return JMMap
                .newChangedValueMap(this, WordCountGenerator::buildCountMap);
    }

}
