package kr.jm.utils.helper;

import kr.jm.utils.datastructure.JMCollections;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static kr.jm.utils.helper.JMConsumer.getSOPL;
import static org.junit.Assert.*;

/**
 * The Class JMLambdaTest.
 */
public class JMLambdaTest {

    Collection<Number> numberCollection;
    Collection<String> stringCollection;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        numberCollection =
                JMStream.numberRange(0, 100, 1).boxed().collect(toList());
        stringCollection = JMStream.numberRange(0, 100, 1).boxed()
                .map(Object::toString).collect(toList());
    }

    /**
     * Test partition by.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testPartitionBy() throws Exception {
        Map<Boolean, List<Number>> partitionBy = JMLambda
                .partitionBy(numberCollection, n -> n.doubleValue() % 2 == 0d);
        System.out.println(partitionBy);
        assertTrue(
                partitionBy.get(true).size() == partitionBy.get(false).size());
    }

    @Test
    public final void testMapBy() throws Exception {
        Map<Number, Number> numberMap = JMLambda.mapBy(JMCollections
                        .buildMergedList(numberCollection, numberCollection
                                .stream().filter(number -> number.doubleValue
                                        () % 2 == 0d).map(number -> number
                                        .doubleValue() * 100).collect(toList())),
                number -> number);
        System.out.println(numberMap);
        assertTrue(numberCollection.size() * 1.5 == numberMap.size());
    }

    /**
     * Test group by two key.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testGroupByTwoKey() throws Exception {
        Map<Boolean, List<Number>> groupBy = JMLambda.groupBy(numberCollection,
                n -> n.doubleValue() % 2 == 0d);
        System.out.println(groupBy);
        assertTrue(groupBy.get(true).size() == groupBy.get(false).size());
    }

    /**
     * Test group by two key 2.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testGroupByTwoKey2() throws Exception {
        Map<Boolean, Map<String, String>> groupBy = JMLambda
                .groupByTwoKey(stringCollection, s -> s.contains("0"), s -> s);
        System.out.println(groupBy);
        assertTrue(groupBy.get(true).size() == 10);
    }

    /**
     * Test consume by predicate.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testConsumeByPredicate() throws Exception {
        JMLambda.consumeByPredicate(numberCollection,
                n -> n.doubleValue() % 2 == 0d,
                n -> assertTrue(n.intValue() % 2 == 0),
                n -> assertTrue(n.intValue() % 2 == 1));
    }

    /**
     * Test consume by predicate in parallel.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testConsumeByPredicateInParallel() throws Exception {
        JMLambda.consumeByPredicateInParallel(numberCollection,
                n -> n.doubleValue() % 2 == 0d,
                n -> assertTrue(n.intValue() % 2 == 0),
                n -> assertTrue(n.intValue() % 2 == 1));
    }

    /**
     * Test consume by boolean.
     *
     * @throws Exception the exception
     */
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

    /**
     * Test consume if not null.
     *
     * @throws Exception the exception
     */
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

    /**
     * Test consume if ture.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testConsumeIfTrue() throws Exception {
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
        JMLambda.consumeIfTrue(testString, testString2,
                (s1, s2) -> s1.equals(s2),
                (s1, s2) -> trueStringBuilder2.append(s1).append(s2));
        assertTrue(
                trueStringBuilder2.toString().equals(testString + testString2));
    }

    /**
     * Test supplier if true.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testSupplierIfTrue() throws Exception {
        String testString = "run by true";
        assertEquals(JMLambda.supplierIfTrue(true, () -> testString).get(),
                testString);
        assertFalse(
                JMLambda.supplierIfTrue(false, () -> testString).isPresent());
    }

    /**
     * Test function by boolean.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testFunctionByBoolean() throws Exception {
        String testString = "run by true";
        String testString2 = "false";
        assertTrue(JMLambda.functionByBoolean(true, testString,
                s -> s.equals(testString), s -> s.equals(testString2)));
        assertFalse(JMLambda.functionByBoolean(false, testString,
                s -> s.equals(testString), s -> s.equals(testString2)));
    }

    /**
     * Test supplier by boolean.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testSupplierByBoolean() throws Exception {
        String testString = "run by true";
        String testString2 = "false";
        assertEquals(JMLambda.supplierByBoolean(true, () -> testString,
                () -> testString2), testString);
        assertEquals(JMLambda.supplierByBoolean(false, () -> testString,
                () -> testString2), testString2);
    }

    /**
     * Test change into.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testChangeInto() throws Exception {
        String testString = "run by true";
        assertEquals(
                Optional.of(stringCollection)
                        .map(JMLambda.changeInto(testString)).get(),
                testString);
    }

    /**
     * Test supplier if null.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testSupplierIfNull() throws Exception {
        String testString = "run by true";
        String testString2 = null;
        assertEquals(JMLambda.supplierIfNull(testString2, () -> testString),
                testString);
    }

    /**
     * Test get true after running.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testGetTrueAfterRunning() throws Exception {
        String testString = "false";
        assertTrue(JMLambda
                .getTrueAfterRunning(() -> getSOPL().accept(testString)));
    }

    /**
     * Test get false after running.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testGetFalseAfterRunning() throws Exception {
        String testString = "true";
        assertFalse(JMLambda
                .getFalseAfterRunning(() -> getSOPL().accept(testString)));
    }

    /**
     * Test run if true.
     *
     * @throws Exception the exception
     */
    @Test
    public final void testRunIfTrue() throws Exception {
        String testString = "true";
        StringBuilder trueStringBuilder = new StringBuilder();
        JMLambda.runIfTrue(true, () -> trueStringBuilder.append(testString));
        assertEquals(trueStringBuilder.toString(), testString);
    }

    /**
     * Test run by boolean.
     *
     * @throws Exception the exception
     */
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
