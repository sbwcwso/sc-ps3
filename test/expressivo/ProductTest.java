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
}