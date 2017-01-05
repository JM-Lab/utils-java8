package kr.jm.utils.collections;

import static kr.jm.utils.helper.JMConsumer.getSOPL;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import kr.jm.utils.helper.JMThread;
import kr.jm.utils.time.JMTimeUtil;

/**
 * The Class JMListTimeseriesTest.
 */
public class JMListTimeseriesTest {

	/**
	 * Test JM timeseries.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testJMTimeseries() throws Exception {
		JMListTimeseries<Long> jmListTimeseries = new JMListTimeseries<>(2);
		long timestamp = System.currentTimeMillis();
		jmListTimeseries.add(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmListTimeseries.add(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmListTimeseries.add(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmListTimeseries.add(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmListTimeseries.add(timestamp, timestamp);

		assertEquals(3, jmListTimeseries.getTimestampKeyList().stream()
				.map(JMTimeUtil::getTime).sorted().peek(getSOPL()).count());
		System.out.println(jmListTimeseries);
	}

}
