package kr.jm.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class JavascriptEvaluatorTest {

    @Test
    public void eval() {
        Object eval = JavascriptEvaluator.eval("(2.33 + 4) * 3.141592");
        System.out.println(eval);
        assertEquals((2.33 + 4) * 3.141592, eval);
        double expected = 9d + 14d / 3d - 6d * (113d + 43d);
        System.out.println(expected);
        assertEquals(expected,
                JavascriptEvaluator.eval("9 + 14 / 3 - 6 * (113 + 43)"));
        assertEquals(1560,
                JavascriptEvaluator.eval("(10 + 15 / 3 - 5) * (113 + 43)"));
        assertNull(JavascriptEvaluator.eval("1+3 = 4"));
        long millis = System.currentTimeMillis() - Double.valueOf(
                JavascriptEvaluator.eval("Date.now()").toString())
                .longValue();
        System.out.println(millis);
        assertTrue(millis <= 0);

    }
}