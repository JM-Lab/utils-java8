package kr.jm.utils.helper;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.jm.utils.datastructure.JMCollections;

public class JMStringTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		List<String> buildList = JMCollections.buildList("jm", "jm");
		System.out.println(JMString.joiningWithComma(buildList));
	}

}
