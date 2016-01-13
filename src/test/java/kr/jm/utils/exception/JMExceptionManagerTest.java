package kr.jm.utils.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import kr.jm.utils.helper.JMStream;
import kr.jm.utils.helper.JMThread;

public class JMExceptionManagerTest {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(JMExceptionManagerTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testLogException() throws Exception {

		long count = JMStream.numberRangeClosed(1, 503, 1).parallel()
				.peek(i -> {
					try {
						JMExceptionManager.logException(log,
								new RuntimeException("Exception - " + i),
								"testLogException");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}).count();

		System.out.println(count);
		JMThread.sleep(100);
		assertEquals(500,
				JMExceptionManager.getErrorMessageHistoryList().size());

	}

}
