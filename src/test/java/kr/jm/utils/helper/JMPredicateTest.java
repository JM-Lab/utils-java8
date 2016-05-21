package kr.jm.utils.helper;

import static kr.jm.utils.helper.JMConsumer.getSOPL;
import static kr.jm.utils.helper.JMPredicate.peek;
import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

public class JMPredicateTest {

	@Test
	public void negate() throws Exception {
		String testString = "a";
		Optional.ofNullable(testString)
				.filter(JMPredicate.negate(string -> string.equals("")))
				.filter(peek(getSOPL()))
				.ifPresent(string -> assertEquals("a", string));
		Optional.ofNullable(testString).filter(JMPredicate.negate(false))
				.filter(peek(getSOPL()))
				.ifPresent(string -> assertEquals("a", string));
	}

}
