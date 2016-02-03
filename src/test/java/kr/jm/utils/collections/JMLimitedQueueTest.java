package kr.jm.utils.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.helper.JMStream;

/**
 * The Class JMLimitedQueueTest.
 */
public class JMLimitedQueueTest {
	private int capacity = 3;
	private JMLimitedQueue<Integer> jmLimitedQueue;

	/**
	 * Creates the jm limited stack.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void createJMLimitedStack() throws Exception {
		jmLimitedQueue = new JMLimitedQueue<>(capacity);
	}

	/**
	 * Test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void test() throws Exception {
		JMStream.numberRange(1, 10, 1).forEach(jmLimitedQueue::add);
		System.out.println(JMCollections.buildList(jmLimitedQueue));
		assertTrue(7 == jmLimitedQueue.peek().get());
		System.out.println(jmLimitedQueue.poll());
		assertTrue(8 == jmLimitedQueue.peek().get());
		System.out.println(jmLimitedQueue.poll());
		assertTrue(9 == jmLimitedQueue.peek().get());
		System.out.println(jmLimitedQueue.peek());
		System.out.println(jmLimitedQueue.poll());
		System.out.println(jmLimitedQueue.poll());
		assertEquals(Optional.empty(), jmLimitedQueue.peek());
		System.out.println(jmLimitedQueue.peek());
	}
}
