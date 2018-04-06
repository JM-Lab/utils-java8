
package kr.jm.utils.datastructure;

import kr.jm.utils.helper.JMStream;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * The Class JMMapTest.
 */
public class JMMapTest {

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test.
     */
    @Test
    public void test() {
        Map<String, String> map = new HashMap<>();
        String key = "key";
        String newValue = "abc";
        JMMap.putGetNew(map, key, newValue);
        JMMap.putGetNew(map, key, "ccc");
        System.out.println(map.get(key));
        System.out.println(JMMap.getOrPutGetNew(map, key, () -> newValue));
        System.out.println(map.get(key));
        assertEquals("ccc", map.get(key));
    }

    /**
     * Test sorted stream by value.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSortedStreamByValue() {
        Map<Integer, Double> map = new HashMap<>();
        JMStream.numberRangeClosed(0, 100, 1)
                .forEach(num -> map.put(num, Math.random() % 100));
        System.out.println(map);
        JMMap.sortedStreamByValue(map).limit(10).forEach(System.out::println);
    }

    /**
     * Test sorted.
     *
     * @throws Exception the exception
     */
    @Test
    public void testSorted() {
        Map<String, Integer> map = new HashMap<>();
        map.put("99", 90);
        map.put("90", 95);
        map.put("95", 99);
        System.out.println(map);
        System.out.println(JMMap.sort(map));
        assertEquals("{90=95, 95=99, 99=90}", JMMap.sort(map).toString());
        System.out.println(JMMap.sortByValue(map));
        assertEquals("{99=90, 90=95, 95=99}",
                JMMap.sortByValue(map).toString());
    }

    @Test
    public void testNewFlatKeyMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", 90);
        map.put("key2", new HashMap<Object, Object>() {{
            put(2, new HashMap<Object, Object>() {{
                put("key2-3", 100);
            }});
            put("nestedKey2", "value2");
        }});
        map.put("key3", "value3");
        Map<String, Object> newFlatKeyMap = JMMap.newFlatKeyMap(map);
        System.out.println(newFlatKeyMap);
        assertEquals(4, newFlatKeyMap.size());
        assertEquals("value2", newFlatKeyMap.get("key2.nestedKey2"));
        assertEquals(100, newFlatKeyMap.get("key2.2.key2-3"));
    }
}
