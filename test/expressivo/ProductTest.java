package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the Product class.
 */
public class ProductTest {
    // Testing strategy
    // * Test assertions enabled
    //
    // * the basic combinations of operand types
    // ** the left operand is a Number, Variable, Sum, or Product
    // ** the right operand is a Number, Variable, Sum, or Product
    //
    // * Test toString() method
    // ** Partition on the basic combinations of operand types
    //
    // * Test equals() and hashCode() method
    // ** Partition the input space as follows:
    // *** equal products
    // **** Partition on the basic combinations of operand types
    // *** unequal products with different left operands
    // *** unequal products with different right operands
    // *** unequal products with same operands but different structure
    // *** unequal products with different operands and different structure
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

    // test toString() method

    /* TS01: left Number, right Number */
    @Test
    public void testToString01() {
        Product product = new Product(new Number(2), new Number(3));
        assertEquals("(2.0000 * 3.0000)", product.toString());
    }

    /* TS02: left Number, right Variable */
    @Test
    public void testToString02() {
        Product product = new Product(new Number(5), new Variable("z"));
        assertEquals("(5.0000 * z)", product.toString());
    }

    /* TS03: left Number, right Sum */
    @Test
    public void testToString03() {
        Product product = new Product(new Number(1), new Sum(new Variable("x"), new Number(2)));
        assertEquals("(1.0000 * (x + 2.0000))", product.toString());
    }

    /* TS04: left Number, right Product */
    @Test
    public void testToString04() {
        Product product = new Product(new Number(4), new Product(new Variable("x"), new Number(2)));
        assertEquals("(4.0000 * (x * 2.0000))", product.toString());
    }

    /* TS05: left Variable, right Number */
    @Test
    public void testToString05() {
        Product product = new Product(new Variable("a"), new Number(10));
        assertEquals("(a * 10.0000)", product.toString());
    }

    /* TS06: left Variable, right Variable */
    @Test
    public void testToString06() {
        Product product = new Product(new Variable("x"), new Variable("y"));
        assertEquals("(x * y)", product.toString());
    }

    /* TS07: left Variable, right Sum */
    @Test
    public void testToString07() {
        Product product = new Product(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        assertEquals("(x * (2.0000 + y))", product.toString());
    }

    /* TS08: left Variable, right Product */
    @Test
    public void testToString08() {
        Product product = new Product(new Variable("x"), new Product(new Number(3), new Variable("y")));
        assertEquals("(x * (3.0000 * y))", product.toString());
    }

    /* TS09: left Product, right Number */
    @Test
    public void testToString09() {
        Product product = new Product(new Product(new Number(1), new Number(2)), new Number(3));
        assertEquals("((1.0000 * 2.0000) * 3.0000)", product.toString());
    }

    /* TS10: left Product, right Variable */
    @Test
    public void testToString10() {
        Product product = new Product(new Product(new Variable("x"), new Number(2)), new Variable("y"));
        assertEquals("((x * 2.0000) * y)", product.toString());
    }

    /* TS11: left Product, right Product */
    @Test
    public void testToString11() {
        Product product = new Product(new Product(new Number(1), new Number(2)),
                new Product(new Number(3), new Number(4)));
        assertEquals("((1.0000 * 2.0000) * (3.0000 * 4.0000))", product.toString());
    }

    /* TS12: left Product, right Sum */
    @Test
    public void testToString12() {
        Product product = new Product(new Product(new Variable("x"), new Number(2)),
                new Sum(new Number(3), new Variable("y")));
        assertEquals("((x * 2.0000) * (3.0000 + y))", product.toString());
    }

    /* TS13: left Sum, right Number */
    @Test
    public void testToString13() {
        Product product = new Product(new Sum(new Number(1), new Number(2)), new Number(3));
        assertEquals("((1.0000 + 2.0000) * 3.0000)", product.toString());
    }

    /* TS14: left Sum, right Variable */
    @Test
    public void testToString14() {
        Product product = new Product(new Sum(new Number(3), new Variable("y")), new Variable("z"));
        assertEquals("((3.0000 + y) * z)", product.toString());
    }

    /* TS15: left Sum, right Product */
    @Test
    public void testToString15() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        assertEquals("((x + 2.0000) * (3.0000 * y))", product.toString());
    }

    /* TS16: left Sum, right Sum */
    @Test
    public void testToString16() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)),
                new Sum(new Number(3), new Variable("y")));
        assertEquals("((x + 2.0000) * (3.0000 + y))", product.toString());
    }

    // test equals() and hashCode() method

    /* 01: left Number, right Number */
    @Test
    public void testEqualsAndHashCodeEqual01() {
        Product p1 = new Product(new Number(2), new Number(3));
        Product p2 = new Product(new Number(2), new Number(3));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 02: left Number, right Variable */
    @Test
    public void testEqualsAndHashCodeEqual02() {
        Product p1 = new Product(new Number(5), new Variable("z"));
        Product p2 = new Product(new Number(5), new Variable("z"));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 03: left Number, right Sum */
    @Test
    public void testEqualsAndHashCodeEqual03() {
        Product p1 = new Product(new Number(1), new Sum(new Variable("x"), new Number(2)));
        Product p2 = new Product(new Number(1), new Sum(new Variable("x"), new Number(2)));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 04: left Number, right Product */
    @Test
    public void testEqualsAndHashCodeEqual04() {
        Product p1 = new Product(new Number(4), new Product(new Variable("x"), new Number(2)));
        Product p2 = new Product(new Number(4), new Product(new Variable("x"), new Number(2)));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 05: left Variable, right Number */
    @Test
    public void testEqualsAndHashCodeEqual05() {
        Product p1 = new Product(new Variable("a"), new Number(10));
        Product p2 = new Product(new Variable("a"), new Number(10));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 06: left Variable, right Variable */
    @Test
    public void testEqualsAndHashCodeEqual06() {
        Product p1 = new Product(new Variable("x"), new Variable("y"));
        Product p2 = new Product(new Variable("x"), new Variable("y"));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 07: left Variable, right Sum */
    @Test
    public void testEqualsAndHashCodeEqual07() {
        Product p1 = new Product(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        Product p2 = new Product(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 08: left Variable, right Product */
    @Test
    public void testEqualsAndHashCodeEqual08() {
        Product p1 = new Product(new Variable("x"), new Product(new Number(3), new Variable("y")));
        Product p2 = new Product(new Variable("x"), new Product(new Number(3), new Variable("y")));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 09: left Product, right Number */
    @Test
    public void testEqualsAndHashCodeEqual09() {
        Product p1 = new Product(new Product(new Number(1), new Number(2)), new Number(3));
        Product p2 = new Product(new Product(new Number(1), new Number(2)), new Number(3));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 10: left Product, right Variable */
    @Test
    public void testEqualsAndHashCodeEqual10() {
        Product p1 = new Product(new Product(new Variable("x"), new Number(2)), new Variable("y"));
        Product p2 = new Product(new Product(new Variable("x"), new Number(2)), new Variable("y"));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 11: left Product, right Product */
    @Test
    public void testEqualsAndHashCodeEqual11() {
        Product p1 = new Product(new Product(new Number(1), new Number(2)), new Product(new Number(3), new Number(4)));
        Product p2 = new Product(new Product(new Number(1), new Number(2)), new Product(new Number(3), new Number(4)));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 12: left Product, right Sum */
    @Test
    public void testEqualsAndHashCodeEqual12() {
        Product p1 = new Product(new Product(new Variable("x"), new Number(2)),
                new Sum(new Number(3), new Variable("y")));
        Product p2 = new Product(new Product(new Variable("x"), new Number(2)),
                new Sum(new Number(3), new Variable("y")));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 13: left Sum, right Number */
    @Test
    public void testEqualsAndHashCodeEqual13() {
        Product p1 = new Product(new Sum(new Number(1), new Number(2)), new Number(3));
        Product p2 = new Product(new Sum(new Number(1), new Number(2)), new Number(3));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 14: left Sum, right Variable */
    @Test
    public void testEqualsAndHashCodeEqual14() {
        Product p1 = new Product(new Sum(new Number(3), new Variable("y")), new Variable("z"));
        Product p2 = new Product(new Sum(new Number(3), new Variable("y")), new Variable("z"));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 15: left Sum, right Product */
    @Test
    public void testEqualsAndHashCodeEqual15() {
        Product p1 = new Product(new Sum(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        Product p2 = new Product(new Sum(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* 16: left Sum, right Sum */
    @Test
    public void testEqualsAndHashCodeEqual16() {
        Product p1 = new Product(new Sum(new Variable("x"), new Number(2)), new Sum(new Number(3), new Variable("y")));
        Product p2 = new Product(new Sum(new Variable("x"), new Number(2)), new Sum(new Number(3), new Variable("y")));
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    /* NEQ01: left operand different */
    @Test
    public void testEqualsAndHashCodeNotEquals01() {
        Product product1 = new Product(new Number(2), new Number(3));
        Product product2 = new Product(new Number(4), new Number(3));
        assertFalse(product1.equals(product2));
    }

    /* NEQ02: right operand different */
    @Test
    public void testEqualsAndHashCodeNotEquals02() {
        Product product1 = new Product(new Variable("x"), new Variable("y"));
        Product product2 = new Product(new Variable("x"), new Variable("z"));
        assertFalse(product1.equals(product2));
    }

    /* NEQ03: structure different but operands same */
    @Test
    public void testEqualsAndHashCodeNotEquals03() {
        Product product1 = new Product(new Number(2), new Product(new Number(3), new Number(4)));
        Product product2 = new Product(new Product(new Number(2), new Number(3)), new Number(4));
        assertFalse(product1.equals(product2));
    }

    /* NEQ04: different operands and structure */
    @Test
    public void testEqualsAndHashCodeNotEquals04() {
        Product product1 = new Product(new Number(2), new Product(new Variable("x"), new Number(4)));
        Product product2 = new Product(new Product(new Number(3), new Number(2)), new Variable("y"));
        assertFalse(product1.equals(product2));
    }

    // differentiate() tests
    /* D01: left Number, right Number, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate01() {
        Product product = new Product(new Number(2), new Number(3));
        Expression derivative = product.differentiate("x");
        assertEquals(new Number(0), derivative);
    }

    /* D02: left Number, right Variable, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate02() {
        Product product = new Product(new Number(5), new Variable("z"));
        Expression derivative = product.differentiate("x");
        assertEquals(new Number(0), derivative);
    }

    /*
     * D03: left Number, right Variable, differentiate w.r.t right operand variable
     */
    @Test
    public void testDifferentiate03() {
        Product product = new Product(new Number(5), new Variable("z"));
        Expression derivative = product.differentiate("z");
        assertEquals(new Number(5), derivative);
    }

    /* D04: left Variable, right Number, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate04() {
        Product product = new Product(new Variable("a"), new Number(10));
        Expression derivative = product.differentiate("x");
        assertEquals(new Number(0), derivative);
    }

    /*
     * D05: left Variable, right Number, differentiate w.r.t left operand variable
     */
    @Test
    public void testDifferentiate05() {
        Product product = new Product(new Variable("a"), new Number(10));
        Expression derivative = product.differentiate("a");
        assertEquals(new Number(10), derivative);
    }

    /*
     * D06: left Variable, right Variable, differentiate w.r.t no operand variable
     */
    @Test
    public void testDifferentiate06() {
        Product product = new Product(new Variable("x"), new Variable("y"));
        Expression derivative = product.differentiate("z");
        assertEquals(new Number(0), derivative);
    }

    /*
     * D07: left Variable, right Variable, differentiate w.r.t left operand variable
     */
    @Test
    public void testDifferentiate07() {
        Product product = new Product(new Variable("x"), new Variable("y"));
        Expression derivative = product.differentiate("x");
        assertEquals(new Variable("y"), derivative);
    }

    /*
     * D08: left Variable, right Variable, differentiate w.r.t right operand
     * variable
     */
    @Test
    public void testDifferentiate08() {
        Product product = new Product(new Variable("x"), new Variable("y"));
        Expression derivative = product.differentiate("y");
        assertEquals(new Variable("x"), derivative);
    }

    /* D09: left Variable, right Variable (same), differentiate w.r.t x */
    @Test
    public void testDifferentiate09() {
        Product product = new Product(new Variable("x"), new Variable("x"));
        Expression derivative = product.differentiate("x");
        assertEquals(new Sum(new Variable("x"), new Variable("x")), derivative);
    }

    /* D10: left Product, right Number, derivative uses product rule on left */
    @Test
    public void testDifferentiate10() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product product = new Product(left, new Number(3));
        Expression derivative = product.differentiate("y");
        // derivative = Sum( Product(left', 3), Product(left, 0) )
        // left' = Sum(Product(0,2), Product(x,0))
        assertEquals(new Number(0), derivative);
    }

    /* D11: left Product, right Number, differentiate w.r.t x */
    @Test
    public void testDifferentiate11() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product product = new Product(left, new Number(3));
        Expression derivative = product.differentiate("x");
        assertEquals(new Number(6), derivative);
    }

    /* D12: left Number, right Product, derivative uses product rule on right */
    @Test
    public void testDifferentiate12() {
        Product right = new Product(new Number(3), new Variable("y"));
        Product product = new Product(new Number(4), right);
        Expression derivative = product.differentiate("z");
        assertEquals(new Number(0), derivative);
    }

    /* D13: left Product, right Product, differentiate w.r.t both variables */
    @Test
    public void testDifferentiate13() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product right = new Product(new Number(3), new Variable("x"));
        Product product = new Product(left, right);
        Expression derivative = product.differentiate("x");
        // derivative = Sum( Product(left', right), Product(left, right') )
        assertEquals(new Sum(
                new Product(new Number(2), new Product(new Number(3), new Variable("x"))),
                new Product(new Product(new Variable("x"), new Number(2)), new Number(3))), derivative);
    }

    /* D14: left Variable, right Sum, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate14() {
        Product product = new Product(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        Expression derivative = product.differentiate("z");
        assertEquals(new Number(0), derivative);
    }

    /* D15: left Variable, right Sum, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate15() {
        Product product = new Product(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        Expression derivative = product.differentiate("x");
        assertEquals(new Sum(new Number(2), new Variable("y")), derivative);
    }

    /* D16: left Variable, right Sum, differentiate w.r.t right operand variable */
    @Test
    public void testDifferentiate16() {
        Product product = new Product(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        Expression derivative = product.differentiate("y");
        assertEquals(new Variable("x"), derivative);
    }

    /* D17: left Variable, right Product, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate17() {
        Product product = new Product(new Variable("x"), new Product(new Number(3), new Variable("y")));
        Expression derivative = product.differentiate("z");
        assertEquals(new Number(0), derivative);
    }

    /*
     * D18: left Variable, right Product, differentiate w.r.t left operand variable
     */
    @Test
    public void testDifferentiate18() {
        Product product = new Product(new Variable("x"), new Product(new Number(3), new Variable("y")));
        Expression derivative = product.differentiate("x");
        assertEquals(new Product(new Number(3), new Variable("y")), derivative);
    }

    /*
     * D19: left Variable, right Product, differentiate w.r.t right operand variable
     */
    @Test
    public void testDifferentiate19() {
        Product product = new Product(new Variable("x"), new Product(new Number(3), new Variable("y")));
        Expression derivative = product.differentiate("y");
        assertEquals(new Product(new Variable("x"), new Number(3)), derivative);
    }

    /* D20: left Sum, right Number, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate20() {
        Product product = new Product(new Sum(new Number(1), new Number(2)), new Number(3));
        Expression derivative = product.differentiate("x");
        assertEquals(new Number(0), derivative);
    }

    /* D21: left Sum, right Number, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate21() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)), new Number(3));
        Expression derivative = product.differentiate("x");
        assertEquals(new Number(3), derivative);
    }

    /* D22: left Sum, right Variable, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate22() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)), new Variable("y"));
        Expression derivative = product.differentiate("z");
        assertEquals(new Number(0), derivative);
    }

    /* D23: left Sum, right Variable, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate23() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)), new Variable("y"));
        Expression derivative = product.differentiate("x");
        assertEquals(new Variable("y"), derivative);
    }

    /* D24: left Sum, right Variable, differentiate w.r.t right operand variable */
    @Test
    public void testDifferentiate24() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)), new Variable("y"));
        Expression derivative = product.differentiate("y");
        assertEquals(new Sum(new Variable("x"), new Number(2)), derivative);
    }

    /* D25: left Sum, right Sum, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate25() {
        Product product = new Product(new Sum(new Number(1), new Number(2)), new Sum(new Number(3), new Number(4)));
        Expression derivative = product.differentiate("x");
        assertEquals(new Number(0), derivative);
    }

    /* D26: left Sum, right Sum, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate26() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)), new Sum(new Number(3), new Number(4)));
        Expression derivative = product.differentiate("x");
        assertEquals(new Sum(new Number(3), new Number(4)), derivative);
    }

    /* D27: left Sum, right Sum, differentiate w.r.t right operand variable */
    @Test
    public void testDifferentiate27() {
        Product product = new Product(new Sum(new Number(1), new Number(2)), new Sum(new Variable("y"), new Number(4)));
        Expression derivative = product.differentiate("y");
        assertEquals(new Sum(new Number(1), new Number(2)), derivative);
    }

    /* D28: left Sum, right Sum, differentiate w.r.t both operand variables */
    @Test
    public void testDifferentiate28() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)),
                new Sum(new Variable("x"), new Number(4)));
        Expression derivative = product.differentiate("x");
        assertEquals(new Sum(new Sum(new Variable("x"), new Number(4)), new Sum(new Variable("x"), new Number(2))),
                derivative);
    }

    /* D29: left Sum, right Product, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate29() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        Expression derivative = product.differentiate("z");
        assertEquals(new Number(0), derivative);
    }

    /* D30: left Sum, right Product, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate30() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        Expression derivative = product.differentiate("x");
        assertEquals(new Product(new Number(3), new Variable("y")), derivative);
    }

    /* D31: left Sum, right Product, differentiate w.r.t right operand variable */
    @Test
    public void testDifferentiate31() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        Expression derivative = product.differentiate("y");
        assertEquals(new Product(new Sum(new Variable("x"), new Number(2)), new Number(3)), derivative);
    }

    /* D32: left Sum, right Product, differentiate w.r.t both operand variables */
    @Test
    public void testDifferentiate32() {
        Product product = new Product(new Sum(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("x")));
        Expression derivative = product.differentiate("x");
        assertEquals(new Sum(new Product(new Number(3), new Variable("x")),
                new Product(new Sum(new Variable("x"), new Number(2)), new Number(3))), derivative);
    }

    /* D33: left Product, right Number, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate33() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product product = new Product(left, new Number(3));
        Expression derivative = product.differentiate("y");
        assertEquals(new Number(0), derivative);
    }

    /* D34: left Product, right Number, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate34() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product product = new Product(left, new Number(3));
        Expression derivative = product.differentiate("x");
        assertEquals(new Number(6), derivative);
    }

    /* D35: left Product, right Variable, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate35() {
        Product left = new Product(new Number(3), new Variable("y"));
        Product product = new Product(left, new Variable("z"));
        Expression derivative = product.differentiate("x");
        assertEquals(new Number(0), derivative);
    }

    /*
     * D36: left Product, right Variable, differentiate w.r.t left operand variable
     */
    @Test
    public void testDifferentiate36() {
        Product left = new Product(new Number(3), new Variable("y"));
        Product product = new Product(left, new Variable("z"));
        Expression derivative = product.differentiate("y");
        assertEquals(new Product(new Number(3), new Variable("z")), derivative);
    }

    /*
     * D37: left Product, right Variable, differentiate w.r.t right operand variable
     */
    @Test
    public void testDifferentiate37() {
        Product left = new Product(new Number(3), new Variable("y"));
        Product product = new Product(left, new Variable("z"));
        Expression derivative = product.differentiate("z");
        assertEquals(new Product(new Number(3), new Variable("y")), derivative);
    }

    /* D38: left Product, right Sum, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate38() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product product = new Product(left, new Sum(new Number(3), new Variable("y")));
        Expression derivative = product.differentiate("z");
        assertEquals(new Number(0), derivative);
    }

    /* D39: left Product, right Sum, differentiate w.r.t left operand variable */
    @Test
    public void testDifferentiate39() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product product = new Product(left, new Sum(new Number(3), new Variable("y")));
        Expression derivative = product.differentiate("x");
        assertEquals(new Product(new Number(2), new Sum(new Number(3), new Variable("y"))), derivative);
    }

    /* D40: left Product, right Sum, differentiate w.r.t right operand variable */
    @Test
    public void testDifferentiate40() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product product = new Product(left, new Sum(new Number(3), new Variable("y")));
        Expression derivative = product.differentiate("y");
        assertEquals(new Product(new Variable("x"), new Number(2)), derivative);
    }

    /* D41: left Product, right Product, differentiate w.r.t no operand variable */
    @Test
    public void testDifferentiate41() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product right = new Product(new Number(3), new Variable("y"));
        Product product = new Product(left, right);
        Expression derivative = product.differentiate("z");
        assertEquals(new Number(0), derivative);
    }

    /*
     * D42: left Product, right Product, differentiate w.r.t left operand variable
     */
    @Test
    public void testDifferentiate42() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product right = new Product(new Number(3), new Variable("y"));
        Product product = new Product(left, right);
        Expression derivative = product.differentiate("x");
        assertEquals(new Product(new Number(2), new Product(new Number(3), new Variable("y"))), derivative);
    }

    /*
     * D43: left Product, right Product, differentiate w.r.t right operand variable
     */
    @Test
    public void testDifferentiate43() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product right = new Product(new Number(3), new Variable("y"));
        Product product = new Product(left, right);
        Expression derivative = product.differentiate("y");
        assertEquals(new Product(new Product(new Variable("x"), new Number(2)), new Number(3)), derivative);
    }

    /*
     * D44: left Product, right Product, differentiate w.r.t both operand variables
     */
    @Test
    public void testDifferentiate44() {
        Product left = new Product(new Variable("x"), new Number(2));
        Product right = new Product(new Number(3), new Variable("x"));
        Product product = new Product(left, right);
        Expression derivative = product.differentiate("x");
        assertEquals(new Sum(new Product(new Number(2), new Product(new Number(3), new Variable("x"))),
                new Product(new Product(new Variable("x"), new Number(2)), new Number(3))), derivative);
    }

    // simplify() tests (empty environment) - basic combinations

    /* S01: left Number, right Number */
    @Test
    public void testSimplify01() {
        Product p = new Product(new Number(2), new Number(3));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Number(6), s);
    }

    /* S02: left Number, right Variable */
    @Test
    public void testSimplify02() {
        Product p = new Product(new Number(5), new Variable("z"));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Product(new Number(5), new Variable("z")), s);
    }

    /* S03: left Number, right Sum */
    @Test
    public void testSimplify03() {
        Product p = new Product(new Number(1), new Sum(new Variable("x"), new Number(2)));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Sum(new Variable("x"), new Number(2)), s);
    }

    /* S04: left Number, right Product */
    @Test
    public void testSimplify04() {
        Product p = new Product(new Number(4), new Product(new Variable("x"), new Number(2)));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Product(new Number(4), new Product(new Variable("x"), new Number(2))), s);
    }

    /* S05: left Variable, right Number */
    @Test
    public void testSimplify05() {
        Product p = new Product(new Variable("a"), new Number(10));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Product(new Variable("a"), new Number(10)), s);
    }

    /* S06: left Variable, right Variable */
    @Test
    public void testSimplify06() {
        Product p = new Product(new Variable("x"), new Variable("y"));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Product(new Variable("x"), new Variable("y")), s);
    }

    /* S07: left Variable, right Sum */
    @Test
    public void testSimplify07() {
        Product p = new Product(new Variable("x"), new Sum(new Number(2), new Variable("y")));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Product(new Variable("x"), new Sum(new Number(2), new Variable("y"))), s);
    }

    /* S08: left Variable, right Product */
    @Test
    public void testSimplify08() {
        Product p = new Product(new Variable("x"), new Product(new Number(3), new Variable("y")));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Product(new Variable("x"), new Product(new Number(3), new Variable("y"))), s);
    }

    /* S09: left Product, right Number */
    @Test
    public void testSimplify09() {
        Product p = new Product(new Product(new Number(1), new Number(2)), new Number(3));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Number(6), s);
    }

    /* S10: left Product, right Variable */
    @Test
    public void testSimplify10() {
        Product p = new Product(new Product(new Variable("x"), new Number(2)), new Variable("y"));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Product(new Product(new Variable("x"), new Number(2)), new Variable("y")), s);
    }

    /* S11: left Product, right Product */
    @Test
    public void testSimplify11() {
        Product p = new Product(new Product(new Number(1), new Number(2)), new Product(new Number(3), new Number(4)));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Number(24), s);
    }

    /* S12: left Product, right Sum */
    @Test
    public void testSimplify12() {
        Product p = new Product(new Product(new Variable("x"), new Number(2)),
                new Sum(new Number(3), new Variable("y")));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(
                new Product(new Product(new Variable("x"), new Number(2)), new Sum(new Number(3), new Variable("y"))),
                s);
    }

    /* S13: left Sum, right Number */
    @Test
    public void testSimplify13() {
        Product p = new Product(new Sum(new Number(1), new Number(2)), new Number(3));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Number(9), s);
    }

    /* S14: left Sum, right Variable */
    @Test
    public void testSimplify14() {
        Product p = new Product(new Sum(new Number(3), new Variable("y")), new Variable("z"));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Product(new Sum(new Number(3), new Variable("y")), new Variable("z")), s);
    }

    /* S15: left Sum, right Product */
    @Test
    public void testSimplify15() {
        Product p = new Product(new Sum(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(
                new Product(new Sum(new Variable("x"), new Number(2)), new Product(new Number(3), new Variable("y"))),
                s);
    }

    /* S16: left Sum, right Sum */
    @Test
    public void testSimplify16() {
        Product p = new Product(new Sum(new Variable("x"), new Number(2)), new Sum(new Number(3), new Variable("y")));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Product(new Sum(new Variable("x"), new Number(2)), new Sum(new Number(3), new Variable("y"))),
                s);
    }

    // Product environment-partition tests (S17..S32)

    /* S17: number*number unchanged with non-empty env */
    @Test
    public void testSimplify17() {
        Product p = new Product(new Number(2), new Number(3));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 9.0);
        Expression s = p.simplify(env);
        assertEquals(new Number(6), s);
    }

    /* S18: number * variable where variable in env */
    @Test
    public void testSimplify18() {
        Product p = new Product(new Number(2), new Variable("x"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 3.0);
        Expression s = p.simplify(env);
        assertEquals(new Number(6), s);
    }

    /* S19: number * variable with unrelated env */
    @Test
    public void testSimplify19() {
        Product p = new Product(new Number(2), new Variable("x"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 3.0);
        Expression s = p.simplify(env);
        assertEquals(new Product(new Number(2), new Variable("x")), s);
    }

    /* S20: variable * number where left variable is in env */
    @Test
    public void testSimplify20() {
        Product p = new Product(new Variable("x"), new Number(3));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 4.0);
        Expression s = p.simplify(env);
        assertEquals(new Number(12), s);
    }

    /* S21: variable * number with unrelated env */
    @Test
    public void testSimplify21() {
        Product p = new Product(new Variable("x"), new Number(3));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 2.0);
        Expression s = p.simplify(env);
        assertEquals(new Product(new Variable("x"), new Number(3)), s);
    }

    /* S22: var * var with only left in env */
    @Test
    public void testSimplify22() {
        Product p = new Product(new Variable("x"), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 2.0);
        Expression s = p.simplify(env);
        assertEquals(new Product(new Number(2), new Variable("y")), s);
    }

    /* S23: var * var with only right in env */
    @Test
    public void testSimplify23() {
        Product p = new Product(new Variable("x"), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 3.0);
        Expression s = p.simplify(env);
        assertEquals(new Product(new Variable("x"), new Number(3)), s);
    }

    /* S24: var * var with both in env */
    @Test
    public void testSimplify24() {
        Product p = new Product(new Variable("x"), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 2.0);
        env.put("y", 3.0);
        Expression s = p.simplify(env);
        assertEquals(new Number(6), s);
    }

    /* S25: var * (number * var) with right inner var in env */
    @Test
    public void testSimplify25() {
        Product p = new Product(new Variable("x"), new Product(new Number(2), new Variable("y")));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 3.0);
        Expression s = p.simplify(env);
        assertEquals(new Product(new Variable("x"), new Number(6)), s);
    }

    /* S26: var * (number * var) with both vars in env */
    @Test
    public void testSimplify26() {
        Product p = new Product(new Variable("x"), new Product(new Number(2), new Variable("y")));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 4.0);
        env.put("y", 3.0);
        Expression s = p.simplify(env);
        assertEquals(new Number(24), s);
    }

    /* S27: (number * var) * var with left inner var in env */
    @Test
    public void testSimplify27() {
        Product p = new Product(new Product(new Number(1), new Variable("x")), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 2.0);
        Expression s = p.simplify(env);
        assertEquals(new Product(new Number(2), new Variable("y")), s);
    }

    /* S28: (number * var) * var with both inner vars in env */
    @Test
    public void testSimplify28() {
        Product p = new Product(new Product(new Number(1), new Variable("x")), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 2.0);
        env.put("y", 5.0);
        Expression s = p.simplify(env);
        assertEquals(new Number(10), s);
    }

    /* S29: (3 * y) * z where y in env */
    @Test
    public void testSimplify29() {
        Product p = new Product(new Product(new Number(3), new Variable("y")), new Variable("z"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 2.0);
        Expression s = p.simplify(env);
        assertEquals(new Product(new Number(6), new Variable("z")), s);
    }

    /* S30: (3 * y) * z with both y and z in env */
    @Test
    public void testSimplify30() {
        Product p = new Product(new Product(new Number(3), new Variable("y")), new Variable("z"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 2.0);
        env.put("z", 1.0);
        Expression s = p.simplify(env);
        assertEquals(new Number(6), s);
    }

    /* S31: x * (y * 3) with y in env */
    @Test
    public void testSimplify31() {
        Product p = new Product(new Variable("x"), new Product(new Variable("y"), new Number(3)));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 2.0);
        Expression s = p.simplify(env);
        assertEquals(new Product(new Variable("x"), new Number(6)), s);
    }

    /* S32: x * (y * 3) with both in env */
    @Test
    public void testSimplify32() {
        Product p = new Product(new Variable("x"), new Product(new Variable("y"), new Number(3)));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 4.0);
        env.put("y", 2.0);
        Expression s = p.simplify(env);
        assertEquals(new Number(24), s);
    }

    /* S33: 0 * x -> 0 */
    @Test
    public void testSimplify33() {
        Product p = new Product(new Number(0), new Variable("x"));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Number(0), s);
    }

    /* S34: x * 1 -> x */
    @Test
    public void testSimplify34() {
        Product p = new Product(new Variable("x"), new Number(1));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Variable("x"), s);
    }

    /* S35: (1 * x) * y -> x * y */
    @Test
    public void testSimplify35() {
        Product p = new Product(new Product(new Number(1), new Variable("x")), new Variable("y"));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Product(new Variable("x"), new Variable("y")), s);
    }

    /* S36: 0 * (2 * 3) -> 0 */
    @Test
    public void testSimplify36() {
        Product p = new Product(new Number(0), new Product(new Number(2), new Number(3)));
        Expression s = p.simplify(java.util.Collections.emptyMap());
        assertEquals(new Number(0), s);
    }

    /* S37: case-sensitivity: env key 'X' should not bind 'x' */
    @Test
    public void testSimplify37() {
        Product p = new Product(new Variable("x"), new Number(2));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("X", 10.0);
        Expression s = p.simplify(env);
        assertEquals(new Product(new Variable("x"), new Number(2)), s);
    }

    /* S38: multi-letter variable binding */
    @Test
    public void testSimplify38() {
        Product p = new Product(new Variable("foo"), new Number(1));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("foo", 7.0);
        Expression s = p.simplify(env);
        assertEquals(new Number(7), s);
    }

    /* S39: extra unrelated env mappings are ignored */
    @Test
    public void testSimplify39() {
        Product p = new Product(new Variable("x"), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("z", 1.0);
        env.put("w", 2.0);
        Expression s = p.simplify(env);
        assertEquals(new Product(new Variable("x"), new Variable("y")), s);
    }

    /* S40: floating-point bindings fold correctly (1.5 * 2.0 = 3.0) */
    @Test
    public void testSimplify40() {
        Product p = new Product(new Variable("x"), new Variable("y"));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 1.5);
        env.put("y", 2.0);
        Expression s = p.simplify(env);
        assertEquals(new Number(3.0), s);
    }

    /* S41: multi-letter name case mismatch is not bound */
    @Test
    public void testSimplify41() {
        Product p = new Product(new Variable("foo"), new Number(1));
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("Foo", 7.0);
        Expression s = p.simplify(env);
        assertEquals(new Variable("foo"), s);
    }
}