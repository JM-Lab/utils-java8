package kr.jm.utils.helper;

import org.junit.Test;

import java.util.Optional;

import static kr.jm.utils.helper.JMPredicate.peekSOPL;
import static org.junit.Assert.assertEquals;

public class JMPredicateTest {

    @Test
    public void negate() {
        String testString = "a";
        Optional.ofNullable(testString)
                .filter(JMPredicate.negate(string -> string.equals("")))
                .filter(peekSOPL())
                .ifPresent(string -> assertEquals("a", string));
        Optional.ofNullable(testString).filter(JMPredicate.negate(false))
                .filter(peekSOPL())
                .ifPresent(string -> assertEquals("a", string));
    }

}
