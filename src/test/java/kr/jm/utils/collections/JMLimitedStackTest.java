package kr.jm.utils.collections;

import kr.jm.utils.datastructure.JMCollections;
import kr.jm.utils.helper.JMStream;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JMLimitedStackTest {
    private int capacity = 3;
    private JMLimitedStack<Integer> jmLimitedStack;

    @Before
    public void createJMLimitedStack() {
        jmLimitedStack = new JMLimitedStack<>(capacity);
    }

    @Test
    public void test() {
        JMStream.numberRange(1, 10, 1).forEach(jmLimitedStack::add);
        System.out.println(JMCollections.buildList(jmLimitedStack));
        assertTrue(9 == jmLimitedStack.peek().get());
        System.out.println(jmLimitedStack.pop());
        assertTrue(8 == jmLimitedStack.peek().get());
        System.out.println(jmLimitedStack.pop());
        assertTrue(7 == jmLimitedStack.peek().get());
        System.out.println(jmLimitedStack.peek());
        System.out.println(jmLimitedStack.pop());
        System.out.println(jmLimitedStack.pop());
        assertEquals(Optional.empty(), jmLimitedStack.peek());
        System.out.println(jmLimitedStack.peek());
    }

}
