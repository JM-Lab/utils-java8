
package kr.jm.utils.datastructure;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import kr.jm.utils.helper.JMStream;

/**
 * The Class JMMapTest.
 */
public class JMMapTest {

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
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
		System.out.println(JMMap.getOrPutGetNew(map, key, newValue));
		System.out.println(map.get(key));
		assertEquals("ccc", map.get(key));
	}

	/**
	 * Test sorted stream by value.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testSortedStreamByValue() throws Exception {
		Map<Integer, Integer> map = new HashMap<>();
		JMStream.numberRangeClosed(0, 100, 1).forEach(num -> map.put(num, num));
		System.out.println(map);
		JMMap.sortedStreamByValue(map).limit(10).forEach(System.out::println);
	}

}
