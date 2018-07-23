package kr.jm.utils.helper;

import org.junit.Assert;
import org.junit.Test;

public class JMRandomTest {

    @Test
    public void testForeachRandomInt() {
        int inclusiveLowerBound = 0;
        int exclusiveUpperBound = 100;
        JMRandom.foreachRandomInt(10_000, inclusiveLowerBound,
                exclusiveUpperBound,
                i -> {
                    System.out.println(i);
                    Assert.assertTrue(inclusiveLowerBound <= i);
                    Assert.assertTrue(exclusiveUpperBound > i);
                });
    }
}