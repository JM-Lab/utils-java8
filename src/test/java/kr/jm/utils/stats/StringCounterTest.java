package kr.jm.utils.stats;

import kr.jm.utils.datastructure.JMArrays;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class StringCounterTest {

    @Test
    public void testStringCounter() {
        String testString = "abc ,aa, aa,aa , abc,abc,aa , aa";
        String[] testStrings = JMArrays.buildArrayFromCsv(testString);
        List<String> testStringList = Arrays.asList(testStrings);
        System.out.println(testStringList);
        StringCounter stringCounter = StringCounter.of().addAll(testStringList);
        System.out.println(stringCounter);
        assertEquals(8, stringCounter.getTotalCount());
        assertEquals(6, stringCounter.getUniqueCount());
        assertEquals(testStringList.stream().sorted().collect(
                Collectors.toList()).toString(), stringCounter
                .getSortedStringList().toString());
        StringCounter stringCounter2 =
                StringCounter.of().addAll(testStringList);
        System.out.println(stringCounter2);
        assertEquals(stringCounter.toString(), stringCounter2.toString());
        System.out.println(stringCounter.merge(stringCounter2));
        assertEquals(16, stringCounter.getTotalCount());
        assertEquals(6, stringCounter.getUniqueCount());
    }

}