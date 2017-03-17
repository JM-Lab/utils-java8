
package kr.jm.utils.helper;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.StringTokenizer;

import org.junit.Before;
import org.junit.Test;

import kr.jm.utils.datastructure.JMCollections;

/**
 * The Class JMStreamTest.
 */
public class JMStreamTest {

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
	 * Test build reversed stream.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testBuildReversedStream() throws Exception {
		Integer[] ints = { 1, 3, 2, 4, 5 };
		List<Integer> sourceList = Arrays.asList(ints);
		System.out.println(
				JMStream.buildReversedStream(sourceList).collect(toList()));
		assertEquals("[5, 4, 2, 3, 1]", JMStream.buildReversedStream(sourceList)
				.collect(toList()).toString());
		System.out.println(sourceList);
		assertEquals("[1, 3, 2, 4, 5]", sourceList.toString());

	}

	/**
	 * Test build stream.
	 */
	@Test
	public void testBuildStream() {
		String string = "a b c d ef g";
		String delimeter = " ";
		List<String> list = JMCollections.buildList(string.split(delimeter));

		System.out.println(
				JMStream.buildStream(list.iterator()).collect(toList()));
		System.out.println(
				JMStream.buildStream(() -> list.iterator()).collect(toList()));
		System.out.println(
				JMStream.buildStream(new StringTokenizer(string, delimeter))
						.collect(toList()));
		assertTrue(JMStream.buildStream(list.iterator()).collect(toList())
				.toString().equals(JMStream.buildStream(() -> list.iterator())
						.collect(toList()).toString()));
		assertTrue(JMStream.buildStream(list.iterator()).collect(toList())
				.toString()
				.equals(JMStream
						.buildStream(new StringTokenizer(string, delimeter))
						.collect(toList()).toString()));
	}

	@Test
	public void testNumberRangeWithCountFloatFloatInt() throws Exception {
		System.out.println(JMStream.numberRangeWithCount(0, 10, 100).boxed()
				.collect(toList()));
		System.out.println(JMStream.numberRangeWithCount(0f, 10f, 100).boxed()
				.collect(toList()));
		LongSummaryStatistics longSummaryStatistics =
				JMStream.numberRangeWithCount(0, 10, 100).summaryStatistics();
		DoubleSummaryStatistics doubleSummaryStatistics =
				JMStream.numberRangeWithCount(0f, 10f, 100).summaryStatistics();
		assertTrue(longSummaryStatistics.getMin() == 0);
		assertTrue(doubleSummaryStatistics.getMin() == 0);
		assertTrue(doubleSummaryStatistics
				.getAverage() == doubleSummaryStatistics.getAverage());
	}

	@Test
	public void testBuildRandomNumberStream() throws Exception {
		List<Double> randomNumberList =
				JMStream.buildRandomNumberStream(100).boxed().collect(toList());
		System.out.println(randomNumberList);
		assertEquals(100, randomNumberList.size());
	}

}
