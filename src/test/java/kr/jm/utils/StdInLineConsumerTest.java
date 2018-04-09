package kr.jm.utils;

import kr.jm.utils.helper.JMThread;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StdInLineConsumerTest {

    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
    }

    private StdInLineConsumer stdInLineConsumer;
    private List<String> resultList;
    private PrintWriter printWriter;

    @Before
    public void setUp() throws Exception {
        PipedInputStream pin = new PipedInputStream();
        this.printWriter = new PrintWriter(new PipedOutputStream(pin));
        System.setIn(pin);
        this.resultList = new ArrayList<>();
        this.stdInLineConsumer = new StdInLineConsumer(this.resultList::add);
    }

    @After
    public void tearDown() {
        printWriter.close();
        stdInLineConsumer.close();
    }

    @Test
    public void consumeStdIn() {
        stdInLineConsumer.consumeStdIn();
        printWriter.println("Hello World !!!");
        printWriter.println("Hello World !!!");
        printWriter.flush();
        JMThread.sleep(1000);
        printWriter.print("Hello World !!!");
        printWriter.flush();
        printWriter.print("Hello World !!!");
        printWriter.flush();
        printWriter.println("Hello World !!!");
        printWriter.flush();
        JMThread.sleep(1000);
        System.out.println(resultList);
        Assert.assertEquals(3, resultList.size());
    }
}