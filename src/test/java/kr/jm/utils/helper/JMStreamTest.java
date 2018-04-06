
package kr.jm.utils.helper;

import kr.jm.utils.datastructure.JMCollections;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The Class JMStreamTest.
 */
public class JMStreamTest {

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test build reversed stream.
     *
     * @throws Exception the exception
     */
    @Test
    public void testBuildReversedStream() {
        Integer[] ints = {1, 3, 2, 4, 5};
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
        String delimiter = " ";
        List<String> list = JMCollections.buildList(string.split(delimiter));

        System.out.println(
                JMStream.buildStream(list.iterator()).collect(toList()));
        System.out.println(
                JMStream.buildStream(() -> list.iterator()).collect(toList()));
        System.out.println(
                JMStream.buildStream(new StringTokenizer(string, delimiter))
                        .collect(toList()));
        assertTrue(JMStream.buildStream(list.iterator()).collect(toList())
                .toString().equals(JMStream.buildStream(() -> list.iterator())
                        .collect(toList()).toString()));
        assertTrue(JMStream.buildStream(list.iterator()).collect(toList())
                .toString()
                .equals(JMStream
                        .buildStream(new StringTokenizer(string, delimiter))
                        .collect(toList()).toString()));
    }

    /**
     * Test number range with count float float int.
     *
     * @throws Exception the exception
     */
    @Test
    public void testNumberRangeWithCountFloatFloatInt() {
        System.out.println(JMStream.numberRangeWithCount(0, 10, 100).boxed()
                .collect(toList()));
        System.out.println(JMStream.numberRangeWithCount(0, 10, 100)
                .mapToDouble(i -> (double) i).boxed()
                .collect(toList()));
        LongSummaryStatistics longSummaryStatistics =
                JMStream.numberRangeWithCount(0, 10, 100)
                        .mapToLong(i -> (long) i).summaryStatistics();
        DoubleSummaryStatistics doubleSummaryStatistics =
                JMStream.numberRangeWithCount(0, 10, 100)
                        .mapToDouble(i -> (double) i).summaryStatistics();
        assertTrue(longSummaryStatistics.getMin() == 0);
        assertTrue(doubleSummaryStatistics.getMin() == 0);
        assertTrue(doubleSummaryStatistics
                .getAverage() == doubleSummaryStatistics.getAverage());
    }

    /**
     * Test build random number stream.
     *
     * @throws Exception the exception
     */
    @Test
    public void testBuildRandomNumberStream() {
        List<Double> randomNumberList =
                JMStream.buildRandomNumberStream(100).boxed().collect(toList());
        System.out.println(randomNumberList);
        assertEquals(100, randomNumberList.size());
    }

}
