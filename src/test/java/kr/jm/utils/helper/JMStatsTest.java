package kr.jm.utils.helper;

import kr.jm.utils.datastructure.JMCollections;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The Class JMStatsTest.
 */
public class JMStatsTest {

    /**
     * Test cal percent.
     *
     * @throws Exception the exception
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

    /**
     * Test round with decimal place.
     *
     * @throws Exception the exception
     */
    @Test
    public void testRoundWithDecimalPlace() throws Exception {
        double x = 100.125093920394890283;
        System.out.println(x);
        System.out.println(JMStats.roundWithDecimalPlace(x, 2));
        assertTrue(100.13 == JMStats.roundWithDecimalPlace(x, 2));
    }

    /**
     * Test round with decimal.
     *
     * @throws Exception the exception
     */
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

    /**
     * Test pow.
     *
     * @throws Exception the exception
     */
    @Test
    public void testPow() throws Exception {
        assertTrue(4 == JMStats.pow(2, 2));
        assertTrue(100 == JMStats.pow(10, 2));
        assertTrue(1 == JMStats.pow(10, 0));
        assertTrue(1024 == JMStats.pow(2, 10));
        System.out.println(JMStats.pow(3, 9));
        assertTrue(19683.0 == JMStats.pow(3, 9));
    }

    /**
     * Test cal variance.
     *
     * @throws Exception the exception
     */
    @Test
    public void testCalVariance() throws Exception {
        assertTrue(0 == JMStats.calVariance(JMCollections.buildList
                (123424235)));
        List<Integer> sample1 = JMCollections.buildList(1, 2, 3, 4, 5);
        System.out.println(JMStats.calVariance(sample1));
        assertTrue(2.5 == JMStats.calVariance(sample1));
        List<Integer> sample2 = JMCollections.buildList(6, 7, -8, 9, 10);
        System.out.println(JMStats.calVariance(sample2));
        assertTrue(53.70000000000001 == JMStats.calVariance(sample2));
        List<Integer> sample3 =
                JMStream.buildConcatStream(sample1, sample2).collect(toList());
        System.out.println(JMStats.calVariance(sample3));
        assertTrue(25.87777777777778 == JMStats.calVariance(sample3));
    }

    /**
     * Test cal standard deviation.
     *
     * @throws Exception the exception
     */
    @Test
    public void testCalStandardDeviation() throws Exception {
        List<Integer> sample1 = JMCollections.buildList(9, 2, 5, 4, 12, 7, 8,
                11, 9, 3, 7, 4, 12, 5, 4, 10, 9, 6, 9, 4);
        System.out.println(JMStats.calPopulationStandardDeviation(sample1));
        assertTrue(2.9832867780352594 == JMStats
                .calPopulationStandardDeviation(sample1));
        System.out.println(JMStats.calStandardDeviation(sample1));
        assertTrue(3.0607876523260447 == JMStats.calStandardDeviation(sample1));

        List<Integer> sample2 = JMCollections.buildList(9, 2, 5, 4, 12, 7);
        System.out.println(JMStats.calPopulationStandardDeviation(sample2));
        assertTrue(3.304037933599835 == JMStats
                .calPopulationStandardDeviation(sample2));
        System.out.println(JMStats.calStandardDeviation(sample2));
        assertTrue(3.6193922141707713 == JMStats.calStandardDeviation(sample2));

    }

    /**
     * Test cal pairwise variance.
     */
    @Test
    public void testCalPairwiseVariance() {
        List<Number> sample1 = JMStream.numberRange(1, 1000000, 1)
                .asDoubleStream().boxed().collect(toList());
        List<Number> sample2 = JMStream.numberRange(1000000, 1000200, 1)
                .asDoubleStream().boxed().collect(toList());
        List<Number> sample3 =
                JMStream.buildConcatStream(sample1, sample2).collect(toList());

        double variance1 = JMStats.calVariance(sample1);
        double variance2 = JMStats.calVariance(sample2);
        double variance3 = JMStats.calVariance(sample3);
        System.out.println(variance1);
        System.out.println(variance2);
        System.out.println(variance3);
        double pairwiseVarance = JMStats.calPairwiseVariance(sample1.size(),
                JMStats.sum(sample1).doubleValue(), variance1, sample2.size(),
                JMStats.sum(sample2).doubleValue(), variance2);
        System.out.println(variance3 + " " + pairwiseVarance);
        assertTrue((float) variance3 == (float) pairwiseVarance);

    }
}
