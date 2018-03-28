package kr.jm.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JMWordSplitterTest {

    @Test
    public void testSplit() {
        String text = "a}) \"b [c";
        List<String> stringList = JMWordSplitter.splitAsList(text);
        List<String> stringList1 = Arrays.asList(JMWordSplitter.split(text));
        List<String> stringList2 = Arrays.asList(text.split("\\W+"));
        List<String> stringList3 = Arrays.asList(text.split("\\s+"));
        System.out.println(stringList);
        System.out.println(stringList1);
        System.out.println(stringList2);
        System.out.println(stringList3);

        Assert.assertEquals(stringList.toString(), stringList1.toString());
        Assert.assertEquals(stringList1.toString(), stringList2.toString());
        Assert.assertNotSame(stringList.toString(), stringList3.toString());
    }

}