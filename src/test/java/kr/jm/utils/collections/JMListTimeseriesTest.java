package kr.jm.utils.collections;

import static kr.jm.utils.helper.JMConsumer.getSOPL;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import kr.jm.utils.helper.JMThread;
import kr.jm.utils.time.JMTimeUtil;

public class JMListTimeseriesTest {
	@Test
	public void testJMTimeseries() throws Exception {
		JMListTimeseries<Long> jmTimeseries = new JMListTimeseries<>(2);
		long timestamp = System.currentTimeMillis();
		jmTimeseries.add(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeseries.add(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeseries.add(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeseries.add(timestamp, timestamp);
		JMThread.sleep(1000);
		timestamp = System.currentTimeMillis();
		jmTimeseries.add(timestamp, timestamp);

		assertEquals(3, jmTimeseries.getTimestampKeySet().stream()
				.map(JMTimeUtil::getTime).sorted().peek(getSOPL()).count());
		System.out.println(jmTimeseries);
	}

}
