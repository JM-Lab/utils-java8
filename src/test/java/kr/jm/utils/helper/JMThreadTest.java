package kr.jm.utils.helper;

import org.junit.Before;
import org.junit.Test;

/**
 * The Class JMThreadTest.
 */
public class JMThreadTest {

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test run async runnable executor.
	 */
	@Test
	public void testRunAsyncRunnableExecutor() {
		JMThread.runAsync(() -> {
			throw new RuntimeException("Error!!!");
		});

	}

	/**
	 * Test run async supply executor.
	 */
	@Test
	public void testRunAsyncSupplyExecutor() {
		JMThread.supplyAsync(() -> 1 / 0);
	}

}
