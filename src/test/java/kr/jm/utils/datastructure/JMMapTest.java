package kr.jm.utils.datastructure;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class JMMapTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void test(){
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

}
