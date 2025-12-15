package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the Sum class.
 */
public class SumTest {
    // Testing strategy
    // * Test assertions enabled
    // * Test toString() method
    // ** Partition the input space as follows:
    // *** sums with two Number operands
    // *** sums with two Variable operands
    // *** sums with mixed Number and Variable operands
    // *** sums with nested Sum operands
    // * Test equals() and hashCode() method
    // ** Partition the input space as follows:
    // *** equal sums with only Number operands
    // *** equal sums with only Variable operands
    // *** equal sums with mixed operands
    // *** equal sums with nested Sum operands
    // *** unequal sums with different left operands
    // *** unequal sums with different right operands
    // *** unequal sums with same operands but different structure
    // *** unequal sums with different operands and different structure

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // test toString() method
    @Test
    public void testToStringTwoNumbers() {
        Sum sum = new Sum(new Number(2), new Number(3));
        assertEquals("(2.0000 + 3.0000)", sum.toString());
    }

    @Test
    public void testToStringTwoVariables() {
        Sum sum = new Sum(new Variable("x"), new Variable("y"));
        assertEquals("(x + y)", sum.toString());
    }

    @Test
    public void testToStringLeftNumberRightVariable() {
        Sum sum = new Sum(new Number(5), new Variable("z"));
        assertEquals("(5.0000 + z)", sum.toString());
    }

    @Test
    public void testToStringLeftVariableRightNumber() {
        Sum sum = new Sum(new Variable("a"), new Number(10));
        assertEquals("(a + 10.0000)", sum.toString());
    }

    @Test
    public void testToStringNestedSums() {
        Sum innerSum = new Sum(new Variable("x"), new Number(1));
        Sum outerSum = new Sum(innerSum, new Variable("y"));
        assertEquals("((x + 1.0000) + y)", outerSum.toString());
    }

    // test equals() and hashCode() methods
    @Test
    public void testEqualsAndHashCodeEqualNumbersOnlyNumberOperands() {
        Sum sum1 = new Sum(new Number(2), new Number(3));
        Sum sum2 = new Sum(new Number(2), new Number(3));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeEqualVariablesOnlyVariableOperands() {
        Sum sum1 = new Sum(new Variable("x"), new Variable("y"));
        Sum sum2 = new Sum(new Variable("x"), new Variable("y"));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeEqualLeftNumberRightVariable() {
        Sum sum1 = new Sum(new Number(5), new Variable("z"));
        Sum sum2 = new Sum(new Number(5), new Variable("z"));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeEqualLeftVariableRightNumber() {
        Sum sum1 = new Sum(new Variable("a"), new Number(10));
        Sum sum2 = new Sum(new Variable("a"), new Number(10));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeEqualNestedSums() {
        Sum innerSum1 = new Sum(new Variable("x"), new Number(1));
        Sum outerSum1 = new Sum(innerSum1, new Variable("y"));
        Sum innerSum2 = new Sum(new Variable("x"), new Number(1));
        Sum outerSum2 = new Sum(innerSum2, new Variable("y"));
        assertTrue(outerSum1.equals(outerSum2));
        assertEquals(outerSum1.hashCode(), outerSum2.hashCode());
    }

    @Test
    public void testEqualsNotEqualDifferentLeftOperands() {
        Sum sum1 = new Sum(new Number(2), new Number(3));
        Sum sum2 = new Sum(new Number(4), new Number(3));
        assertFalse(sum1.equals(sum2));
    }

    @Test
    public void testEqualsNotEqualDifferentRightOperands() {
        Sum sum1 = new Sum(new Variable("x"), new Variable("y"));
        Sum sum2 = new Sum(new Variable("x"), new Variable("z"));
        assertFalse(sum1.equals(sum2));
    }

    @Test
    public void testEqualsNotEqualSameOperandsDifferentStructure() {
        Sum innerSum = new Sum(new Number(1), new Number(2));
        Sum sum1 = new Sum(innerSum, new Number(3));
        Sum sum2 = new Sum(new Number(1), new Sum(new Number(2), new Number(3)));
        assertFalse(sum1.equals(sum2));
    }

    @Test
    public void testEqualsNotEqualDifferentOperandsDifferentStructure() {
        Sum sum1 = new Sum(new Number(2), new Number(3));
        Sum sum2 = new Sum(new Variable("x"), new Sum(new Number(2), new Number(3)));
        assertFalse(sum1.equals(sum2));
    }
}