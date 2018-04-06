package kr.jm.utils.collections;

import kr.jm.utils.helper.JMThread;
import kr.jm.utils.time.JMTimeUtil;
import org.junit.Test;

import static kr.jm.utils.helper.JMConsumer.getSOPL;
import static org.junit.Assert.assertEquals;

/**
 * The Class JMTimeSeriesTest.
 */
public class JMTimeSeriesTest {

	/**
	 * Test JM timeSeries.
	 *
	 * @throws Exception the exception
	 */
	@Test
    public void testJMTimeSeries() {
		JMTimeSeries<Long> jmTimeSeries = new JMTimeSeries<>(2);
		long timestamp = System.currentTimeMillis();
		jmTimeSeries.put(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeSeries.put(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeSeries.put(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeSeries.put(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeSeries.put(timestamp, timestamp);

		assertEquals(3, jmTimeSeries.getTimestampKeyList().stream()
				.map(JMTimeUtil::getTime).peek(getSOPL()).count());
		System.out.println(jmTimeSeries);
		System.out.println(jmTimeSeries.getTimestampKeyList());

	}

}
