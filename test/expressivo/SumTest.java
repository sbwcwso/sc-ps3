package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the Sum class.
 */
public class SumTest {
    // Testing strategy
    // * Test assertions enabled
    //
    // * the basic combinations of operand types
    // ** the left operand is a Number, Variable, Sum, or Product
    // ** the right operand is a Number, Variable, Sum, or Product
    //
    // * Test toString() method
    // ** test the basic combinations of operand types
    //
    // * Test equals() and hashCode() method
    // ** Partition the input space as follows:
    // *** equals sums
    // **** test the basic combinations of operand types
    // *** unequal sums
    // **** the left operand is different
    // **** the right operand is different
    // **** both operands are different
    // **** the structure is different but operands are the same
    // **** both operands and structure are different
    //
    // * Test differentiate() method
    // ** the basic combinations of operand types
    // *** differentiate with respect to no operand variable
    // *** differentiate with respect to left operand variable
    // *** differentiate with respect to right operand variable
    // *** differentiate with respect to both operand variables
    //
    // * Test simplify() method
    // ** the basic combinations of operand types
    // ** empty environment, 1, 2, or more mappings
    // ** environment with extra variables not in expression
    // ** variables with different case in environment
    // ** variables with multi-letter names in environment
    // ** all variables not in environment
    // ** all variables in environment
    // ** some variables in environment
    // *** Only left operand variable in environment
    // *** Only right operand variable in environment
    // *** Both operand variables in environment

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // test toString() cases split into small tests

    /* 01: left Number, right Number */
    @Test
    public void testToString01() {
        Sum sum = new Sum(new Number(2), new Number(3));
        assertEquals("(2.0000 + 3.0000)", sum.toString());
    }

    /* 02: left Number, right Variable */
    @Test
    public void testToString02() {
        Sum sum = new Sum(new Number(5), new Variable("z"));
        assertEquals("(5.0000 + z)", sum.toString());
    }

    /* 03: left Number, right Sum */
    @Test
    public void testToString03() {
        Sum sum = new Sum(new Number(1), new Sum(new Variable("x"), new Number(2)));
        assertEquals("(1.0000 + (x + 2.0000))", sum.toString());
    }

    /* 04: left Number, right Product */
    @Test
    public void testToString04() {
        Sum sum = new Sum(new Number(4), new Product(new Variable("x"), new Number(2)));
        assertEquals("(4.0000 + (x * 2.0000))", sum.toString());
    }

    /* 05: left Variable, right Number */
    @Test
    public void testToString05() {
        Sum sum = new Sum(new Variable("a"), new Number(10));
        assertEquals("(a + 10.0000)", sum.toString());
    }

    /* 06: left Variable, right Variable */
    @Test
    public void testToString06() {
        Sum sum = new Sum(new Variable("x"), new Variable("y"));
        assertEquals("(x + y)", sum.toString());
    }

    /* 07: left Variable, right Sum */
    @Test
    public void testToString07() {
        Sum sum = new Sum(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        assertEquals("(x + (2.0000 + y))", sum.toString());
    }

    /* 08: left Variable, right Product */
    @Test
    public void testToString08() {
        Sum sum = new Sum(new Variable("x"), new Product(new Number(3), new Variable("y")));
        assertEquals("(x + (3.0000 * y))", sum.toString());
    }

    /* 09: left Sum, right Number */
    @Test
    public void testToString09() {
        Sum sum = new Sum(new Sum(new Number(1), new Number(2)), new Number(3));
        assertEquals("((1.0000 + 2.0000) + 3.0000)", sum.toString());
    }

    /* 10: left Sum, right Variable */
    @Test
    public void testToString10() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Variable("y"));
        assertEquals("((x + 2.0000) + y)", sum.toString());
    }

    /* 11: left Sum, right Sum */
    @Test
    public void testToString11() {
        Sum sum = new Sum(new Sum(new Number(1), new Number(2)), new Sum(new Number(3), new Number(4)));
        assertEquals("((1.0000 + 2.0000) + (3.0000 + 4.0000))", sum.toString());
    }

    /* 12: left Sum, right Product */
    @Test
    public void testToString12() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Product(new Number(3), new Variable("y")));
        assertEquals("((x + 2.0000) + (3.0000 * y))", sum.toString());
    }

    /* 13: left Product, right Number */
    @Test
    public void testToString13() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)), new Number(3));
        assertEquals("((x * 2.0000) + 3.0000)", sum.toString());
    }

    /* 14: left Product, right Variable */
    @Test
    public void testToString14() {
        Sum sum = new Sum(new Product(new Number(3), new Variable("y")), new Variable("z"));
        assertEquals("((3.0000 * y) + z)", sum.toString());
    }

    /* 15: left Product, right Sum */
    @Test
    public void testToString15() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)), new Sum(new Number(3), new Variable("y")));
        assertEquals("((x * 2.0000) + (3.0000 + y))", sum.toString());
    }

    /* 16: left Product, right Product */
    @Test
    public void testToString16() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        assertEquals("((x * 2.0000) + (3.0000 * y))", sum.toString());
    }

    // test equals() and hashCode() methods split into small tests

    /* 01: left Number, right Number */
    @Test
    public void testEqualsAndHashCodeEqual01() {
        Sum sum1 = new Sum(new Number(2), new Number(3));
        Sum sum2 = new Sum(new Number(2), new Number(3));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 02: left Number, right Variable */
    @Test
    public void testEqualsAndHashCodeEqual02() {
        Sum sum1 = new Sum(new Number(5), new Variable("z"));
        Sum sum2 = new Sum(new Number(5), new Variable("z"));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 03: left Number, right Sum */
    @Test
    public void testEqualsAndHashCodeEqual03() {
        Sum sum1 = new Sum(new Number(1), new Sum(new Variable("x"), new Number(2)));
        Sum sum2 = new Sum(new Number(1), new Sum(new Variable("x"), new Number(2)));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 04: left Number, right Product */
    @Test
    public void testEqualsAndHashCodeEqual04() {
        Sum sum1 = new Sum(new Number(4), new Product(new Variable("x"), new Number(2)));
        Sum sum2 = new Sum(new Number(4), new Product(new Variable("x"), new Number(2)));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 05: left Variable, right Number */
    @Test
    public void testEqualsAndHashCodeEqual05() {
        Sum sum1 = new Sum(new Variable("a"), new Number(10));
        Sum sum2 = new Sum(new Variable("a"), new Number(10));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 06: left Variable, right Variable */
    @Test
    public void testEqualsAndHashCodeEqual06() {
        Sum sum1 = new Sum(new Variable("x"), new Variable("y"));
        Sum sum2 = new Sum(new Variable("x"), new Variable("y"));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 07: left Variable, right Sum */
    @Test
    public void testEqualsAndHashCodeEqual07() {
        Sum sum1 = new Sum(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        Sum sum2 = new Sum(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 08: left Variable, right Product */
    @Test
    public void testEqualsAndHashCodeEqual08() {
        Sum sum1 = new Sum(new Variable("x"), new Product(new Number(3), new Variable("y")));
        Sum sum2 = new Sum(new Variable("x"), new Product(new Number(3), new Variable("y")));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 09: left Sum, right Number */
    @Test
    public void testEqualsAndHashCodeEqual09() {
        Sum sum1 = new Sum(new Sum(new Number(1), new Number(2)), new Number(3));
        Sum sum2 = new Sum(new Sum(new Number(1), new Number(2)), new Number(3));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 10: left Sum, right Variable */
    @Test
    public void testEqualsAndHashCodeEqual10() {
        Sum sum1 = new Sum(new Sum(new Variable("x"), new Number(2)), new Variable("y"));
        Sum sum2 = new Sum(new Sum(new Variable("x"), new Number(2)), new Variable("y"));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 11: left Sum, right Sum */
    @Test
    public void testEqualsAndHashCodeEqual11() {
        Sum sum1 = new Sum(new Sum(new Number(1), new Number(2)), new Sum(new Number(3), new Number(4)));
        Sum sum2 = new Sum(new Sum(new Number(1), new Number(2)), new Sum(new Number(3), new Number(4)));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 12: left Sum, right Product */
    @Test
    public void testEqualsAndHashCodeEqual12() {
        Sum sum1 = new Sum(new Sum(new Variable("x"), new Number(2)), new Product(new Number(3), new Variable("y")));
        Sum sum2 = new Sum(new Sum(new Variable("x"), new Number(2)), new Product(new Number(3), new Variable("y")));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 13: left Product, right Number */
    @Test
    public void testEqualsAndHashCodeEqual13() {
        Sum sum1 = new Sum(new Product(new Variable("x"), new Number(2)), new Number(3));
        Sum sum2 = new Sum(new Product(new Variable("x"), new Number(2)), new Number(3));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 14: left Product, right Variable */
    @Test
    public void testEqualsAndHashCodeEqual14() {
        Sum sum1 = new Sum(new Product(new Number(3), new Variable("y")), new Variable("z"));
        Sum sum2 = new Sum(new Product(new Number(3), new Variable("y")), new Variable("z"));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 15: left Product, right Sum */
    @Test
    public void testEqualsAndHashCodeEqual15() {
        Sum sum1 = new Sum(new Product(new Variable("x"), new Number(2)), new Sum(new Number(3), new Variable("y")));
        Sum sum2 = new Sum(new Product(new Variable("x"), new Number(2)), new Sum(new Number(3), new Variable("y")));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* 16: left Product, right Product */
    @Test
    public void testEqualsAndHashCodeEqual16() {
        Sum sum1 = new Sum(new Product(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        Sum sum2 = new Sum(new Product(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        assertTrue(sum1.equals(sum2));
        assertEquals(sum1.hashCode(), sum2.hashCode());
    }

    /* NEQ01: left operand different */
    @Test
    public void testEqualsAndHashCodeNotEqual01() {
        Sum sum1 = new Sum(new Number(2), new Number(3));
        Sum sum2 = new Sum(new Number(4), new Number(3));
        assertFalse(sum1.equals(sum2));
    }

    /* NEQ02: right operand different */
    @Test
    public void testEqualsAndHashCodeNotEqual02() {
        Sum sum1 = new Sum(new Variable("x"), new Variable("y"));
        Sum sum2 = new Sum(new Variable("x"), new Variable("z"));
        assertFalse(sum1.equals(sum2));
    }

    /* NEQ03: both operands different */
    @Test
    public void testEqualsAndHashCodeNotEqual03() {
        Sum sum1 = new Sum(new Number(2), new Variable("x"));
        Sum sum2 = new Sum(new Number(3), new Variable("y"));
        assertFalse(sum1.equals(sum2));
    }

    /* NEQ04: structure different but operands same */
    @Test
    public void testEqualsAndHashCodeNotEqual04() {
        Sum sum1 = new Sum(new Number(2), new Sum(new Number(3), new Number(4)));
        Sum sum2 = new Sum(new Sum(new Number(2), new Number(3)), new Number(4));
        assertFalse(sum1.equals(sum2));
    }

    /* NEQ05: both operands and structure different */
    @Test
    public void testEqualsAndHashCodeNotEqual05() {
        Sum sum1 = new Sum(new Variable("x"), new Number(5));
        Sum sum2 = new Sum(new Number(3), new Sum(new Variable("x"), new Number(2)));
        assertFalse(sum1.equals(sum2));
    }

    // test differentiate() method
    /* D01: left Number, right Number, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate01() {
        Sum sum = new Sum(new Number(2), new Number(3));
        Expression derivative = sum.differentiate("x");
        // 0 + 0 simplifies to 0
        assertEquals(new Number(0), derivative);
    }

    /* D02: left Number, right Variable, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate02() {
        Sum sum = new Sum(new Number(5), new Variable("z"));
        Expression derivative = sum.differentiate("x");
        // 0 + 0 simplifies to 0
        assertEquals(new Number(0), derivative);
    }

    /*
     * D03: left Number, right Variable, differentiate w.r.t right operand variable
     */
    @Test
    public void testDifferentiate03() {
        Sum sum = new Sum(new Number(5), new Variable("z"));
        Expression derivative = sum.differentiate("z");
        // 0 + 1 simplifies to 1
        assertEquals(new Number(1), derivative);
    }

    /* D04: left Number, right Sum, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate04() {
        Sum sum = new Sum(new Number(1), new Sum(new Variable("x"), new Number(2)));
        Expression derivative = sum.differentiate("y");
        // 0 + (0 + 0) = 0 + 0 = 0
        assertEquals(new Number(0), derivative);
    }

    /* D05: left Number, right Sum, differentiate w.r.t right operand variable */
    @Test
    public void testDifferentiate05() {
        Sum sum = new Sum(new Number(1), new Sum(new Variable("x"), new Number(2)));
        Expression derivative = sum.differentiate("x");
        // 0 + (1 + 0) = 0 + 1 = 1
        assertEquals(new Number(1), derivative);
    }

    /* D06: left Number, right Product, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate06() {
        Sum sum = new Sum(new Number(4), new Product(new Variable("x"), new Number(2)));
        Expression derivative = sum.differentiate("y");
        // 0 + ((0 * 2) + (x * 0)) drops left 0
        assertEquals(new Number(0), derivative);
    }

    /*
     * D07: left Number, right Product, differentiate w.r.t right operand variable
     */
    @Test
    public void testDifferentiate07() {
        Sum sum = new Sum(new Number(4), new Product(new Variable("x"), new Number(2)));
        Expression derivative = sum.differentiate("x");
        // 0 + ((1 * 2) + (x * 0)) drops left 0
        assertEquals(new Number(2), derivative);
    }

    /*
     * D08: left Variable, right Number, differentiate w.r.t none operand variable
     */
    @Test
    public void testDifferentiate08() {
        Sum sum = new Sum(new Variable("a"), new Number(10));
        Expression derivative = sum.differentiate("x");
        // 0 + 0 = 0
        assertEquals(new Number(0), derivative);
    }

    /*
     * D09: left Variable, right Number, differentiate w.r.t left operand variable
     */
    @Test
    public void testDifferentiate09() {
        Sum sum = new Sum(new Variable("a"), new Number(10));
        Expression derivative = sum.differentiate("a");
        // 1 + 0 = 1
        assertEquals(new Number(1), derivative);
    }

    /*
     * D10: left Variable, right Variable, differentiate w.r.t no operand variable
     */
    @Test
    public void testDifferentiate10() {
        Sum sum = new Sum(new Variable("x"), new Variable("y"));
        Expression derivative = sum.differentiate("z");
        // 0 + 0 = 0
        assertEquals(new Number(0), derivative);
    }

    /*
     * D11: left Variable, right Variable, differentiate w.r.t left operand variable
     */
    @Test
    public void testDifferentiate11() {
        Sum sum = new Sum(new Variable("x"), new Variable("y"));
        Expression derivative = sum.differentiate("x");
        // 1 + 0 = 1
        assertEquals(new Number(1), derivative);
    }

    /*
     * D12: left Variable, right Variable, differentiate w.r.t right operand
     * variable
     */
    @Test
    public void testDifferentiate12() {
        Sum sum = new Sum(new Variable("x"), new Variable("y"));
        Expression derivative = sum.differentiate("y");
        // 0 + 1 = 1
        assertEquals(new Number(1), derivative);
    }

    /* D13: left Variable, right Variable (same), differentiate w.r.t both */
    @Test
    public void testDifferentiate13() {
        Sum sum = new Sum(new Variable("x"), new Variable("x"));
        Expression derivative = sum.differentiate("x");
        // 1 + 1 = 2
        assertEquals(new Number(2), derivative);
    }

    /* D14: left Variable, right Sum, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate14() {
        Sum sum = new Sum(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        Expression derivative = sum.differentiate("z");
        // 0 + (0 + 0) = 0 + 0 = 0
        assertEquals(new Number(0), derivative);
    }

    /* D15: left Variable, right Sum, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate15() {
        Sum sum = new Sum(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        Expression derivative = sum.differentiate("x");
        // 1 + (0 + 0) = 1 + 0 = 1
        assertEquals(new Number(1), derivative);
    }

    /* D16: left Variable, right Sum, differentiate w.r.t right operand variable */
    @Test
    public void testDifferentiate16() {
        Sum sum = new Sum(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        Expression derivative = sum.differentiate("y");
        // 0 + (0 + 1) = 0 + 1 = 1
        assertEquals(new Number(1), derivative);
    }

    /* D17: left Variable, right Product, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate17() {
        Sum sum = new Sum(new Variable("x"), new Product(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("z");
        // 0 + ((0 * y) + (3 * 0)) drops left 0
        assertEquals(new Number(0), derivative);
    }

    /*
     * D18: left Variable, right Product, differentiate w.r.t left operand variable
     */
    @Test
    public void testDifferentiate18() {
        Sum sum = new Sum(new Variable("x"), new Product(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("x");
        // 1 + ((0 * y) + (3 * 0))
        assertEquals(new Number(1), derivative);
    }

    /*
     * D19: left Variable, right Product, differentiate w.r.t right operand variable
     */
    @Test
    public void testDifferentiate19() {
        Sum sum = new Sum(new Variable("x"), new Product(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("y");
        // 0 + ((0 * y) + (3 * 1)) drops left 0
        assertEquals(new Number(3), derivative);
    }

    /* D20: left Sum, right Number, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate20() {
        Sum sum = new Sum(new Sum(new Number(1), new Number(2)), new Number(3));
        Expression derivative = sum.differentiate("x");
        // (0 + 0) + 0 = 0 + 0 = 0
        assertEquals(new Number(0), derivative);
    }

    /* D21: left Sum, right Number, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate21() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Number(3));
        Expression derivative = sum.differentiate("x");
        // (1 + 0) + 0 = 1 + 0 = 1
        assertEquals(new Number(1), derivative);
    }

    /* D22: left Sum, right Variable, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate22() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Variable("y"));
        Expression derivative = sum.differentiate("z");
        // (0 + 0) + 0 = 0 + 0 = 0
        assertEquals(new Number(0), derivative);
    }

    /* D23: left Sum, right Variable, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate23() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Variable("y"));
        Expression derivative = sum.differentiate("x");
        // (1 + 0) + 0 = 1 + 0 = 1
        assertEquals(new Number(1), derivative);
    }

    /* D24: left Sum, right Variable, differentiate w.r.t right operand variable */
    @Test
    public void testDifferentiate24() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Variable("y"));
        Expression derivative = sum.differentiate("y");
        // (0 + 0) + 1 = 0 + 1 = 1
        assertEquals(new Number(1), derivative);
    }

    /* D25: left Sum, right Sum, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate25() {
        Sum sum = new Sum(new Sum(new Number(1), new Number(2)), new Sum(new Number(3), new Number(4)));
        Expression derivative = sum.differentiate("x");
        // (0 + 0) + (0 + 0) = 0 + 0 = 0
        assertEquals(new Number(0), derivative);
    }

    /* D26: left Sum, right Sum, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate26() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Sum(new Number(3), new Number(4)));
        Expression derivative = sum.differentiate("x");
        // (1 + 0) + (0 + 0) = 1 + 0 = 1
        assertEquals(new Number(1), derivative);
    }

    /* D27: left Sum, right Sum, differentiate w.r.t right operand variable */
    @Test
    public void testDifferentiate27() {
        Sum sum = new Sum(new Sum(new Number(1), new Number(2)), new Sum(new Variable("y"), new Number(4)));
        Expression derivative = sum.differentiate("y");
        // (0 + 0) + (1 + 0) = 0 + 1 = 1
        assertEquals(new Number(1), derivative);
    }

    /* D28: left Sum, right Sum, differentiate w.r.t both operand variables */
    @Test
    public void testDifferentiate28() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Sum(new Variable("x"), new Number(4)));
        Expression derivative = sum.differentiate("x");
        // (1 + 0) + (1 + 0) = 1 + 1 = 2
        assertEquals(new Number(2), derivative);
    }

    /* D29: left Sum, right Product, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate29() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Product(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("z");
        // (0 + 0) + ((0 * y) + (3 * 0)) = 0 + (...) drops left 0
        assertEquals(new Number(0), derivative);
    }

    /* D30: left Sum, right Product, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate30() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Product(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("x");
        // (1 + 0) + ((0 * y) + (3 * 0)) = 1 + (...)
        assertEquals(new Number(1), derivative);
    }

    /* D31: left Sum, right Product, differentiate w.r.t right operand variable */
    @Test
    public void testDifferentiate31() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Product(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("y");
        // (0 + 0) + ((0 * y) + (3 * 1)) = 0 + (...) drops left 0
        assertEquals(new Number(3), derivative);
    }

    /* D32: left Sum, right Product, differentiate w.r.t both operand variables */
    @Test
    public void testDifferentiate32() {
        Sum sum = new Sum(new Sum(new Variable("x"), new Number(2)), new Product(new Number(3), new Variable("x")));
        Expression derivative = sum.differentiate("x");
        // (1 + 0) + ((0 * x) + (3 * 1)) = 1 + (...)
        assertEquals(new Number(4), derivative);
    }

    /* D33: left Product, right Number, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate33() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)), new Number(3));
        Expression derivative = sum.differentiate("y");
        // ((0 * 2) + (x * 0)) + 0 drops right 0
        assertEquals(new Number(0), derivative);
    }

    /* D34: left Product, right Number, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate34() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)), new Number(3));
        Expression derivative = sum.differentiate("x");
        // ((1 * 2) + (x * 0)) + 0 drops right 0
        assertEquals(new Number(2), derivative);
    }

    /* D35: left Product, right Variable, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate35() {
        Sum sum = new Sum(new Product(new Number(3), new Variable("y")), new Variable("z"));
        Expression derivative = sum.differentiate("x");
        // ((0 * y) + (3 * 0)) + 0 drops right 0
        assertEquals(new Number(0), derivative);
    }

    /*
     * D36: left Product, right Variable, differentiate w.r.t left operand variable
     */
    @Test
    public void testDifferentiate36() {
        Sum sum = new Sum(new Product(new Number(3), new Variable("y")), new Variable("z"));
        Expression derivative = sum.differentiate("y");
        // ((0 * y) + (3 * 1)) + 0 drops right 0
        assertEquals(new Number(3), derivative);
    }

    /*
     * D37: left Product, right Variable, differentiate w.r.t right operand variable
     */
    @Test
    public void testDifferentiate37() {
        Sum sum = new Sum(new Product(new Number(3), new Variable("y")), new Variable("z"));
        Expression derivative = sum.differentiate("z");
        // ((0 * y) + (3 * 0)) + 1
        assertEquals(new Number(1), derivative);
    }

    /* D38: left Product, right Sum, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate38() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)), new Sum(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("z");
        // ((0 * 2) + (x * 0)) + (0 + 0) drops right (0 + 0)
        assertEquals(new Number(0), derivative);
    }

    /* D39: left Product, right Sum, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate39() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)), new Sum(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("x");
        // ((1 * 2) + (x * 0)) + (0 + 0) drops right (0 + 0)
        assertEquals(new Number(2), derivative);
    }

    /* D40: left Product, right Sum, differentiate w.r.t right operand variable */
    @Test
    public void testDifferentiate40() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)), new Sum(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("y");
        // ((0 * 2) + (x * 0)) + (0 + 1)
        assertEquals(new Number(1), derivative);
    }

    /* D41: left Product, right Product, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate41() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("z");
        // ((0 * 2) + (x * 0)) + ((0 * y) + (3 * 0))
        assertEquals(new Number(0), derivative);
    }

    /*
     * D42: left Product, right Product, differentiate w.r.t left operand variable
     */
    @Test
    public void testDifferentiate42() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("x");
        // ((1 * 2) + (x * 0)) + ((0 * y) + (3 * 0))
        assertEquals(new Number(2), derivative);
    }

    /*
     * D43: left Product, right Product, differentiate w.r.t right operand variable
     */
    @Test
    public void testDifferentiate43() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        Expression derivative = sum.differentiate("y");
        // ((0 * 2) + (x * 0)) + ((0 * y) + (3 * 1))
        assertEquals(new Number(3), derivative);
    }

    /*
     * D44: left Product, right Product, differentiate w.r.t both operand variables
     */
    @Test
    public void testDifferentiate44() {
        Sum sum = new Sum(new Product(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("x")));
        Expression derivative = sum.differentiate("x");
        // ((1 * 2) + (x * 0)) + ((0 * x) + (3 * 1))
        assertEquals(new Number(5), derivative);
    }

    // simplify() tests (empty environment) - small numbered cases

    /* S01: left Number, right Number */
    @Test
    public void testSimplify01() {
        Sum sum = new Sum(new Number(2), new Number(3));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Number(5), simplified);
    }

    /* S02: left Number, right Variable */
    @Test
    public void testSimplify02() {
        Sum sum = new Sum(new Number(2), new Variable("x"));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Number(2), new Variable("x")), simplified);
    }

    /* S03: left Number, right Sum */
    @Test
    public void testSimplify03() {
        Sum sum = new Sum(new Number(2), new Sum(new Number(1), new Variable("y")));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Number(2), new Sum(new Number(1), new Variable("y"))), simplified);
    }

    /* S04: left Number, right Product */
    @Test
    public void testSimplify04() {
        Sum sum = new Sum(new Number(2), new Product(new Variable("y"), new Number(3)));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Number(2), new Product(new Variable("y"), new Number(3))), simplified);
    }

    /* S05: left Variable, right Number */
    @Test
    public void testSimplify05() {
        Sum sum = new Sum(new Variable("x"), new Number(3));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Variable("x"), new Number(3)), simplified);
    }

    /* S06: left Variable, right Variable */
    @Test
    public void testSimplify06() {
        Sum sum = new Sum(new Variable("x"), new Variable("y"));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Variable("x"), new Variable("y")), simplified);
    }

    /* S07: left Variable, right Sum */
    @Test
    public void testSimplify07() {
        Sum sum = new Sum(new Variable("x"), new Sum(new Number(1), new Variable("y")));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Variable("x"), new Sum(new Number(1), new Variable("y"))), simplified);
    }

    /* S08: left Variable, right Product */
    @Test
    public void testSimplify08() {
        Sum sum = new Sum(new Variable("x"), new Product(new Variable("y"), new Number(3)));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Variable("x"), new Product(new Variable("y"), new Number(3))), simplified);
    }

    /* S09: left Sum, right Number */
    @Test
    public void testSimplify09() {
        Sum sum = new Sum(new Sum(new Number(1), new Variable("y")), new Number(3));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Sum(new Number(1), new Variable("y")), new Number(3)), simplified);
    }

    /* S10: left Sum, right Variable */
    @Test
    public void testSimplify10() {
        Sum sum = new Sum(new Sum(new Number(1), new Variable("y")), new Variable("x"));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Sum(new Number(1), new Variable("y")), new Variable("x")), simplified);
    }

    /* S11: left Sum, right Sum */
    @Test
    public void testSimplify11() {
        Sum sum = new Sum(new Sum(new Number(1), new Variable("y")), new Sum(new Number(2), new Variable("z")));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Sum(new Number(1), new Variable("y")), new Sum(new Number(2), new Variable("z"))),
                simplified);
    }

    /* S12: left Sum, right Product */
    @Test
    public void testSimplify12() {
        Sum sum = new Sum(new Sum(new Number(1), new Variable("y")), new Product(new Variable("z"), new Number(3)));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Sum(new Number(1), new Variable("y")), new Product(new Variable("z"), new Number(3))),
                simplified);
    }

    /* S13: left Product, right Number */
    @Test
    public void testSimplify13() {
        Sum sum = new Sum(new Product(new Variable("y"), new Number(3)), new Number(4));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Product(new Variable("y"), new Number(3)), new Number(4)), simplified);
    }

    /* S14: left Product, right Variable */
    @Test
    public void testSimplify14() {
        Sum sum = new Sum(new Product(new Variable("y"), new Number(3)), new Variable("x"));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Product(new Variable("y"), new Number(3)), new Variable("x")), simplified);
    }

    /* S15: left Product, right Sum */
    @Test
    public void testSimplify15() {
        Sum sum = new Sum(new Product(new Variable("y"), new Number(3)), new Sum(new Number(1), new Variable("z")));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Product(new Variable("y"), new Number(3)), new Sum(new Number(1), new Variable("z"))),
                simplified);
    }

    /* S16: left Product, right Product */
    @Test
    public void testSimplify16() {
        Sum sum = new Sum(new Product(new Variable("y"), new Number(3)), new Product(new Number(4), new Variable("z")));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(
                new Sum(new Product(new Variable("y"), new Number(3)), new Product(new Number(4), new Variable("z"))),
                simplified);
    }

    /* S17: number+number unchanged with non-empty env */
    @Test
    public void testSimplify17() {
        Sum sum = new Sum(new Number(2), new Number(3));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 9.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Number(5), simplified);
    }

    /* S18: number+variable where variable is in env */
    @Test
    public void testSimplify18() {
        Sum sum = new Sum(new Number(2), new Variable("x"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 3.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Number(5), simplified);
    }

    /* S19: number+variable with unrelated env */
    @Test
    public void testSimplify19() {
        Sum sum = new Sum(new Number(2), new Variable("x"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 3.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Sum(new Number(2), new Variable("x")), simplified);
    }

    /* S20: variable+number where left variable is in env */
    @Test
    public void testSimplify20() {
        Sum sum = new Sum(new Variable("x"), new Number(3));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 4.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Number(7), simplified);
    }

    /* S21: variable+number with unrelated env */
    @Test
    public void testSimplify21() {
        Sum sum = new Sum(new Variable("x"), new Number(3));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 2.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Sum(new Variable("x"), new Number(3)), simplified);
    }

    /* S22: var+var with only left in env */
    @Test
    public void testSimplify22() {
        Sum sum = new Sum(new Variable("x"), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 2.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Sum(new Number(2), new Variable("y")), simplified);
    }

    /* S23: var+var with only right in env */
    @Test
    public void testSimplify23() {
        Sum sum = new Sum(new Variable("x"), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 3.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Sum(new Variable("x"), new Number(3)), simplified);
    }

    /* S24: var+var with both in env */
    @Test
    public void testSimplify24() {
        Sum sum = new Sum(new Variable("x"), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 2.0);
        env.put("y", 3.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Number(5), simplified);
    }

    /* S25: var + (number + var) with right inner var in env */
    @Test
    public void testSimplify25() {
        Sum sum = new Sum(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 3.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Sum(new Variable("x"), new Number(5)), simplified);
    }

    /* S26: var + (number + var) with both vars in env */
    @Test
    public void testSimplify26() {
        Sum sum = new Sum(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 4.0);
        env.put("y", 3.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Number(9), simplified);
    }

    /* S27: (number + var) + var with left inner var in env */
    @Test
    public void testSimplify27() {
        Sum sum = new Sum(new Sum(new Number(1), new Variable("x")), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 2.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Sum(new Number(3), new Variable("y")), simplified);
    }

    /* S28: (number + var) + var with both inner vars in env */
    @Test
    public void testSimplify28() {
        Sum sum = new Sum(new Sum(new Number(1), new Variable("x")), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 2.0);
        env.put("y", 5.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Number(8), simplified);
    }

    /* S29: (3 * y) + z where y in env */
    @Test
    public void testSimplify29() {
        Sum sum = new Sum(new Product(new Number(3), new Variable("y")), new Variable("z"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 2.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Sum(new Number(6), new Variable("z")), simplified);
    }

    /* S30: (3 * y) + z with both y and z in env */
    @Test
    public void testSimplify30() {
        Sum sum = new Sum(new Product(new Number(3), new Variable("y")), new Variable("z"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 2.0);
        env.put("z", 1.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Number(7), simplified);
    }

    /* S31: x + (y * 3) with y in env */
    @Test
    public void testSimplify31() {
        Sum sum = new Sum(new Variable("x"), new Product(new Variable("y"), new Number(3)));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 2.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Sum(new Variable("x"), new Number(6)), simplified);
    }

    /* S32: x + (y * 3) with both in env */
    @Test
    public void testSimplify32() {
        Sum sum = new Sum(new Variable("x"), new Product(new Variable("y"), new Number(3)));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 4.0);
        env.put("y", 2.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Number(10), simplified);
    }

    /* S33: 0 + x -> x (zero left identity) */
    @Test
    public void testSimplify33() {
        Sum sum = new Sum(new Number(0), new Variable("x"));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Variable("x"), simplified);
    }

    /* S34: x + 0 -> x (zero right identity) */
    @Test
    public void testSimplify34() {
        Sum sum = new Sum(new Variable("x"), new Number(0));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Variable("x"), simplified);
    }

    /* S35: (1 * x) + y -> x + y (multiplicative identity) */
    @Test
    public void testSimplify35() {
        Sum sum = new Sum(new Product(new Number(1), new Variable("x")), new Variable("y"));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Variable("x"), new Variable("y")), simplified);
    }

    /* S36: 0 + (2 + 3) -> 5 (nested numeric folding and zero drop) */
    @Test
    public void testSimplify36() {
        Sum sum = new Sum(new Number(0), new Sum(new Number(2), new Number(3)));
        Expression simplified = sum.simplify(java.util.Collections.emptyMap());
        assertEquals(new Number(5), simplified);
    }

    /* S37: case-sensitivity: env key 'X' should not bind 'x' */
    @Test
    public void testSimplify37() {
        Sum sum = new Sum(new Variable("x"), new Number(2));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("X", 10.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Sum(new Variable("x"), new Number(2)), simplified);
    }

    /* S38: multi-letter variable name binding */
    @Test
    public void testSimplify38() {
        Sum sum = new Sum(new Variable("foo"), new Number(1));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("foo", 7.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Number(8), simplified);
    }

    /* S39: extra unrelated env mappings are ignored */
    @Test
    public void testSimplify39() {
        Sum sum = new Sum(new Variable("x"), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("z", 1.0);
        env.put("w", 2.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Sum(new Variable("x"), new Variable("y")), simplified);
    }

    /* S40: floating-point bindings fold correctly (1.5 + 2.25 = 3.75) */
    @Test
    public void testSimplify40() {
        Sum sum = new Sum(new Variable("x"), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 1.5);
        env.put("y", 2.25);
        Expression simplified = sum.simplify(env);
        assertEquals(new Number(3.75), simplified);
    }

    /* S41: multi-letter name mismatch by case is not bound */
    @Test
    public void testSimplify41() {
        Sum sum = new Sum(new Variable("foo"), new Number(1));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("Foo", 7.0);
        Expression simplified = sum.simplify(env);
        assertEquals(new Sum(new Variable("foo"), new Number(1)), simplified);
    }

}