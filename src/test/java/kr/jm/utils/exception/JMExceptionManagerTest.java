package kr.jm.utils.exception;

import kr.jm.utils.helper.JMStream;
import kr.jm.utils.helper.JMThread;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JMExceptionManagerTest {

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(JMExceptionManagerTest.class);

    @Before
    public void setUp() {
    }

    @Test
    public final void testLogException() {

        long count =
                JMStream.numberRangeClosed(1, 503, 1).parallel().peek(i -> {
                    try {
                        JMExceptionManager.handleException(log,
                                new RuntimeException("Exception - " + i),
                                "testLogException");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).count();

        System.out.println(count);
        JMThread.sleep(100);
        assertEquals(500,
                JMExceptionManager.getErrorMessageHistoryList().size());

    }

}
