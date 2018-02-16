package kr.jm.utils.stats;

import kr.jm.utils.helper.JMStream;
import org.junit.Test;

import java.util.DoubleSummaryStatistics;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class NumberSummaryStatisticsTest {

    @Test
    public void testNumberSummaryStatistics() {
        int[] ints = JMStream.buildRandomNumberStream(10).map(d -> d * 100000)
                .mapToInt(d -> (int) d).toArray();
        NumberSummaryStatistics intNumberSummaryStatistics =
                NumberSummaryStatistics.of().addAll(ints);
        System.out.println(intNumberSummaryStatistics);
        DoubleSummaryStatistics doubleSummaryStatistics =
                IntStream.of(ints).mapToDouble(i -> i).summaryStatistics();
        assertEquals(doubleSummaryStatistics.getCount(),
                intNumberSummaryStatistics.getCount());
        assertEquals(doubleSummaryStatistics.getSum(),
                intNumberSummaryStatistics.getSum());
        assertEquals(doubleSummaryStatistics.getMin(),
                intNumberSummaryStatistics.getMin());
        assertEquals(doubleSummaryStatistics.getMax(),
                intNumberSummaryStatistics.getMax());
        assertEquals(doubleSummaryStatistics.getAverage(),
                intNumberSummaryStatistics.getAverage());
        NumberSummaryStatistics longNumberSummaryStatistics =
                NumberSummaryStatistics.of()
                        .addAll(IntStream.of(ints).mapToLong(i -> i).toArray());
        System.out.println(longNumberSummaryStatistics);
        assertEquals(longNumberSummaryStatistics.getCount(),
                intNumberSummaryStatistics.getCount());
        assertEquals(longNumberSummaryStatistics.getSum(),
                intNumberSummaryStatistics.getSum());
        assertEquals(longNumberSummaryStatistics.getMin(),
                intNumberSummaryStatistics.getMin());
        assertEquals(longNumberSummaryStatistics.getMax(),
                intNumberSummaryStatistics.getMax());
        assertEquals(longNumberSummaryStatistics.getAverage(),
                intNumberSummaryStatistics.getAverage());
        NumberSummaryStatistics doubleNumberSummaryStatistics =
                NumberSummaryStatistics.of()
                        .addAll(IntStream.of(ints).mapToDouble(i -> i)
                                .toArray());
        System.out.println(doubleNumberSummaryStatistics);
        assertEquals(longNumberSummaryStatistics.getCount(),
                doubleNumberSummaryStatistics.getCount());
        assertEquals(longNumberSummaryStatistics.getSum(),
                doubleNumberSummaryStatistics.getSum());
        assertEquals(longNumberSummaryStatistics.getMin(),
                doubleNumberSummaryStatistics.getMin());
        assertEquals(longNumberSummaryStatistics.getMax(),
                doubleNumberSummaryStatistics.getMax());
        assertEquals(longNumberSummaryStatistics.getAverage(),
                doubleNumberSummaryStatistics.getAverage());
        intNumberSummaryStatistics.combine(longNumberSummaryStatistics)
                .combine(doubleNumberSummaryStatistics);
        assertEquals(intNumberSummaryStatistics.getCount(),
                doubleNumberSummaryStatistics.getCount().longValue() * 3);
        assertEquals(intNumberSummaryStatistics.getSum(),
                doubleNumberSummaryStatistics.getSum().doubleValue() * 3);
        assertEquals(intNumberSummaryStatistics.getMin(),
                doubleNumberSummaryStatistics.getMin());
        assertEquals(intNumberSummaryStatistics.getMax(),
                doubleNumberSummaryStatistics.getMax());
        assertEquals(intNumberSummaryStatistics.getAverage(),
                doubleNumberSummaryStatistics.getAverage());
    }
}