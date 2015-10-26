package kr.jm.utils.helper;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JMStreamTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetReversedStream() throws Exception {
		Integer[] ints = { 1, 3, 2, 4, 5 };
		List<Integer> sourceList = Arrays.asList(ints);
		System.out.println(JMStream.getReversedStream(sourceList).collect(
				toList()));
		assertEquals("[5, 4, 2, 3, 1]", JMStream.getReversedStream(sourceList)
				.collect(toList()).toString());
		System.out.println(sourceList);
		assertEquals("[1, 3, 2, 4, 5]", sourceList.toString());

	}

}
