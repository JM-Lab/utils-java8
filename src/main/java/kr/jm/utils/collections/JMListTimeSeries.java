package kr.jm.utils.collections;

import kr.jm.utils.datastructure.JMMap;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Jm list time series.
 *
 * @param <T> the type parameter
 */
public class JMListTimeSeries<T> extends JMTimeSeries<List<T>> {

    /**
     * Instantiates a new Jm list time series.
     *
     * @param intervalSeconds the interval seconds
     */
    public JMListTimeSeries(int intervalSeconds) {
        super(intervalSeconds);
    }

    /**
     * Add.
     *
     * @param timestamp the timestamp
     * @param object    the object
     */
    public void add(long timestamp, T object) {
        JMMap.getOrPutGetNew(this.timeSeriesMap, buildKeyTimestamp(timestamp),
                ArrayList::new).add(object);
    }

    /**
     * Add all.
     *
     * @param timestamp  the timestamp
     * @param objectList the object list
     */
    public void addAll(long timestamp, List<T> objectList) {
        for (T object : objectList)
            add(timestamp, object);
    }

    /*
     * (non-Javadoc)
     *
     * @see kr.jm.utils.collections.JMTimeSeries#toString()
     */
    @Override
    public String toString() {
        return "JMListTimeSeries(intervalSeconds=" + getIntervalSeconds()
                + ", timeSeriesMap=" + timeSeriesMap.toString() + ")";
    }

}
