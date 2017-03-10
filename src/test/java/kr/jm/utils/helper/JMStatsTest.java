package kr.jm.utils.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The Class JMStatsTest.
 */
public class JMStatsTest {

	/**
	 * Test cal percent.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testCalPercent() throws Exception {
		assertEquals(0, JMStats.calPercent(1, 101));
		assertEquals(49, JMStats.calPercent(50, 101));
		assertEquals(50, JMStats.calPercent(50, 100));
		assertEquals(101, JMStats.calPercent(101, 100));
		assertEquals(0.9900990099009901d, JMStats.calPercentPrecisely(1, 101),
				0);
		assertEquals("0.990", JMStats.calPercent(1, 101, 3));
		assertEquals("18.797", JMStats.calPercent(100, 532, 3));
	}

	@Test
	public void testRoundWithDecimalPlace() throws Exception {
		double x = 100.125093920394890283;
		System.out.println(x);
		System.out.println(JMStats.roundWithDecimalPlace(x, 2));
		assertTrue(100.13 == JMStats.roundWithDecimalPlace(x, 2));
	}

	@Test
	public void testRoundWithDecimal() throws Exception {
		double x = 10022250.02394;
		System.out.println(x);
		System.out.println(JMStats.roundWithPlace(x, 2));
		assertTrue(10022300 == JMStats.roundWithPlace(x, 2));
		long l = 10022250;
		System.out.println(l);
		System.out.println(JMStats.roundWithPlace(l, 2));
		assertTrue(10022300 == JMStats.roundWithPlace(l, 2));
	}

}
