package kr.jm.utils.collections;

import kr.jm.utils.helper.JMThread;
import kr.jm.utils.time.JMTimeUtil;
import org.junit.Test;

import static kr.jm.utils.helper.JMConsumer.getSOPL;
import static org.junit.Assert.assertEquals;

public class JMListTimeSeriesTest {

    @Test
    public void testJMTimeSeries() {
        JMListTimeSeries<Long> jmListTimeSeries = new JMListTimeSeries<>(2);
        long timestamp = System.currentTimeMillis();
        jmListTimeSeries.add(timestamp, timestamp);
        JMThread.sleep(1000);
        timestamp = System.currentTimeMillis();
        jmListTimeSeries.add(timestamp, timestamp);
        JMThread.sleep(1000);
        timestamp = System.currentTimeMillis();
        jmListTimeSeries.add(timestamp, timestamp);
        JMThread.sleep(1000);
        timestamp = System.currentTimeMillis();
        jmListTimeSeries.add(timestamp, timestamp);
        JMThread.sleep(1000);
        timestamp = System.currentTimeMillis();
        jmListTimeSeries.add(timestamp, timestamp);

        assertEquals(3, jmListTimeSeries.getTimestampKeyList().stream()
                .map(JMTimeUtil::getTime).sorted().peek(getSOPL()).count());
        System.out.println(jmListTimeSeries);
    }

}
