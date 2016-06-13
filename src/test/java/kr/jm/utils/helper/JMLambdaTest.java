package kr.jm.utils.helper;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMConsumer.getSOPL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class JMLambdaTest {

	Collection<Number> numberCollection;
	Collection<String> stringCollection;

	@Before
	public void setUp() throws Exception {
		numberCollection =
				JMStream.numberRange(0, 100, 1).boxed().collect(toList());
		stringCollection = JMStream.numberRange(0, 100, 1).boxed()
				.map(Object::toString).collect(toList());
	}

	@Test
	public final void testPartitionBy() throws Exception {
		Map<Boolean, List<Number>> partitionBy = JMLambda
				.partitionBy(numberCollection, n -> n.doubleValue() % 2 == 0d);
		System.out.println(partitionBy);
		assertTrue(
				partitionBy.get(true).size() == partitionBy.get(false).size());
	}

	@Test
	public final void testGroupBy() throws Exception {
		Map<Boolean, List<Number>> groupBy = JMLambda.groupBy(numberCollection,
				n -> n.doubleValue() % 2 == 0d);
		System.out.println(groupBy);
		assertTrue(groupBy.get(true).size() == groupBy.get(false).size());
	}

	@Test
	public final void testGroupBy2() throws Exception {
		Map<Boolean, Map<String, String>> groupBy = JMLambda
				.groupBy(stringCollection, s -> s.contains("0"), s -> s);
		System.out.println(groupBy);
		assertTrue(groupBy.get(true).size() == 10);
	}

	@Test
	public final void testConsumeByPredicate() throws Exception {
		JMLambda.consumeByPredicate(numberCollection,
				n -> n.doubleValue() % 2 == 0d,
				n -> assertTrue(n.intValue() % 2 == 0),
				n -> assertTrue(n.intValue() % 2 == 1));
	}

	@Test
	public final void testConsumeByPredicateInParallel() throws Exception {
		JMLambda.consumeByPredicateInParallel(numberCollection,
				n -> n.doubleValue() % 2 == 0d,
				n -> assertTrue(n.intValue() % 2 == 0),
				n -> assertTrue(n.intValue() % 2 == 1));
	}

	@Test
	public final void testConsumeByBoolean() throws Exception {
		String testString = "run by true";
		StringBuilder trueStringBuilder = new StringBuilder();
		StringBuilder falseStringBuilder = new StringBuilder();
		JMLambda.consumeByBoolean(testString.equals(testString), testString,
				trueStringBuilder::append, falseStringBuilder::append);
		assertTrue(trueStringBuilder.toString().equals(testString));
		assertTrue(falseStringBuilder.toString().equals(""));
	}

	@Test
	public final void testConsumeIfNotNull() throws Exception {
		String testString = "run by true";
		StringBuilder trueStringBuilder = new StringBuilder();
		StringBuilder falseStringBuilder = new StringBuilder();
		JMLambda.consumeIfNotNull(testString, trueStringBuilder::append);
		assertTrue(trueStringBuilder.toString().equals(testString));
		JMLambda.consumeIfNotNull(null, trueStringBuilder::append);
		assertTrue(falseStringBuilder.toString().equals(""));
	}

	@Test
	public final void testConsumeIfTure() throws Exception {
		String testString = "run by true";
		String testString2 = "false";
		StringBuilder trueStringBuilder = new StringBuilder();
		StringBuilder trueStringBuilder2 = new StringBuilder();
		JMLambda.consumeIfTrue(true, testString, trueStringBuilder::append);
		assertTrue(trueStringBuilder.toString().equals(testString));

		JMLambda.consumeIfTrue(testString, s -> s.equals(testString2),
				trueStringBuilder::append);
		assertTrue(trueStringBuilder.toString().equals(testString));

		JMLambda.consumeIfTrue(true, testString, testString2,
				(s1, s2) -> trueStringBuilder2.append(s1).append(s2));
		assertTrue(
				trueStringBuilder2.toString().equals(testString + testString2));
		JMLambda.consumeIfTure(testString, testString2,
				(s1, s2) -> s1.equals(s2),
				(s1, s2) -> trueStringBuilder2.append(s1).append(s2));
		assertTrue(
				trueStringBuilder2.toString().equals(testString + testString2));
	}

	@Test
	public final void testGetIfTrue() throws Exception {
		String testString = "run by true";
		assertEquals(JMLambda.getIfTrue(true, () -> testString).get(),
				testString);
		assertFalse(JMLambda.getIfTrue(false, () -> testString).isPresent());
	}

	@Test
	public final void testApplyByBoolean() throws Exception {
		String testString = "run by true";
		String testString2 = "false";
		assertTrue(JMLambda.applyByBoolean(true, testString,
				s -> s.equals(testString), s -> s.equals(testString2)));
		assertFalse(JMLambda.applyByBoolean(false, testString,
				s -> s.equals(testString), s -> s.equals(testString2)));
	}

	@Test
	public final void testGetByBoolean() throws Exception {
		String testString = "run by true";
		String testString2 = "false";
		assertEquals(JMLambda.getByBoolean(true, () -> testString,
				() -> testString2), testString);
		assertEquals(JMLambda.getByBoolean(false, () -> testString,
				() -> testString2), testString2);
	}

	@Test
	public final void testChangeInto() throws Exception {
		String testString = "run by true";
		assertEquals(
				Optional.of(stringCollection)
						.map(JMLambda.changeInto(testString)).get(),
				testString);
	}

	@Test
	public final void testGetElseIfNull() throws Exception {
		String testString = "run by true";
		String testString2 = null;
		assertEquals(JMLambda.getElseIfNull(testString2, () -> testString),
				testString);
	}

	@Test
	public final void testGetTrueAfterRunning() throws Exception {
		String testString = "false";
		assertTrue(JMLambda
				.getTrueAfterRunning(() -> getSOPL().accept(testString)));
	}

	@Test
	public final void testGetFalseAfterRunning() throws Exception {
		String testString = "true";
		assertFalse(JMLambda
				.getFalseAfterRunning(() -> getSOPL().accept(testString)));
	}

	@Test
	public final void testRunIfTrue() throws Exception {
		String testString = "true";
		StringBuilder trueStringBuilder = new StringBuilder();
		JMLambda.runIfTrue(true, () -> trueStringBuilder.append(testString));
		assertEquals(trueStringBuilder.toString(), testString);
	}

	@Test
	public final void testRunByBoolean() throws Exception {
		String testString = "true";
		String testString2 = "false";
		StringBuilder trueStringBuilder = new StringBuilder();
		JMLambda.runByBoolean(false, () -> trueStringBuilder.append(testString),
				() -> trueStringBuilder.append(testString2));
		assertEquals(trueStringBuilder.toString(), testString2);
	}

}
