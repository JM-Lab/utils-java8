package kr.jm.utils.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		JMThread.supplyAsync(() -> 1 / 0, () -> 0);
	}

	/**
	 * Test get thread queue.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetThreadQueue() throws Exception {
		ExecutorService es = Executors.newFixedThreadPool(1);
		System.out.println(JMThread.getThreadQueue(es).size());
		assertEquals(0, JMThread.getThreadQueue(es).size());
		es = Executors.newCachedThreadPool();
		System.out.println(JMThread.getThreadQueue(es).size());
		assertEquals(0, JMThread.getThreadQueue(es).size());
		es = Executors.newSingleThreadExecutor();
		try {
			assertEquals(0, JMThread.getThreadQueue(es).size());
			System.out.println(JMThread.getThreadQueue(es).size());
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Unsupport ExecutorService"));
		}
		es = JMThread.newSingleThreadPool();
		System.out.println(JMThread.getThreadQueue(es).size());
		assertEquals(0, JMThread.getThreadQueue(es).size());
	}

}
