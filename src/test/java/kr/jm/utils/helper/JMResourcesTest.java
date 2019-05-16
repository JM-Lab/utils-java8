package kr.jm.utils.helper;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JMResourcesTest {

    @Test
    public void testGetURL() {
        System.out.println(JMResources.getURL("LICENSE"));
    }


    @Test
    public void testReadStringForZip() {
        Assert.assertEquals("hello\n",
                JMResources.readStringForZip("src/test/resources/test.zip",
                        "test/test.txt"));
    }

    @Test
    public void testReadLinesForZip() {
        List<String> linesForZip = JMResources.readLinesForZip
                ("src/test/resources/test.zip",
                        "test/test.txt");
        Assert.assertEquals("[hello]", linesForZip.toString());
    }
}
