package kr.jm.utils.collections;

import static kr.jm.utils.helper.JMConsumer.getSOPL;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import kr.jm.utils.helper.JMThread;
import kr.jm.utils.time.JMTimeUtil;

public class JMTimeseriesTest {
	@Test
	public void testJMTimeseries() throws Exception {
		JMTimeseries<Long> jmTimeseries = new JMTimeseries<>(2);
		long timestamp = System.currentTimeMillis();
		jmTimeseries.put(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeseries.put(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeseries.put(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeseries.put(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeseries.put(timestamp, timestamp);

		assertEquals(3, jmTimeseries.getTimestampKeyList().stream()
				.map(JMTimeUtil::getTime).sorted().peek(getSOPL()).count());
		System.out.println(jmTimeseries);
		System.out.println(jmTimeseries.getTimestampKeyList());

	}

}
