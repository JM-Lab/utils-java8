package kr.jm.utils.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.helper.JMStream;

/**
 * The Class JMLimitedListTest.
 */
public class JMLimitedListTest {

	private int capacity = 3;
	private JMLimitedList<Integer> jmLimitedList;

	/**
	 * Creates the jm limited stack.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void createJMLimitedStack() throws Exception {
		jmLimitedList = new JMLimitedList<>(capacity);
	}

	/**
	 * Test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void test() throws Exception {
		JMStream.numberRange(1, 10, 1).forEach(jmLimitedList::add);
		System.out.println(JMCollections.buildList(jmLimitedList));
		assertTrue(7 == jmLimitedList.getFirst().get());
		System.out.println(jmLimitedList.getCurrentIndex());
		System.out.println(jmLimitedList.getNext());
		System.out.println(jmLimitedList.getCurrentIndex());
		assertTrue(8 == jmLimitedList.getPrevious().get());
		System.out.println(jmLimitedList.getNext());
		assertTrue(9 == jmLimitedList.getLast().get());
		System.out.println(jmLimitedList.getCurrentIndex());
		System.out.println(jmLimitedList.getPrevious());
		System.out.println(jmLimitedList.getCurrentIndex());
		System.out.println(jmLimitedList.add(100));
		System.out.println(jmLimitedList.getCurrentIndex());
		assertEquals(Optional.empty(), jmLimitedList.getNext());
		System.out.println(JMCollections.buildList(jmLimitedList));
	}

}
