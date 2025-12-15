package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the Product class.
 */
public class ProductTest {
    // Testing strategy
    // * Test assertions enabled
    // * Test toString() method
    // ** Partition the input space as follows:
    // *** products with two Number operands
    // *** products with two Variable operands
    // *** products with mixed Number and Variable operands
    // *** products with nested Product operands
    // * Test equals() and hashCode() method
    // ** Partition the input space as follows:
    // *** equal products with only Number operands
    // *** equal products with only Variable operands
    // *** equal products with mixed operands
    // *** equal products with nested Product operands
    // *** unequal products with different left operands
    // *** unequal products with different right operands
    // *** unequal products with same operands but different structure
    // *** unequal products with different operands and different structure

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // test toString() method
    @Test
    public void testToStringTwoNumbers() {
        Product product = new Product(new Number(2), new Number(3));
        assertEquals("(2.0000 * 3.0000)", product.toString());
    }

    @Test
    public void testToStringTwoVariables() {
        Product product = new Product(new Variable("x"), new Variable("y"));
        assertEquals("(x * y)", product.toString());
    }

    @Test
    public void testToStringLeftNumberRightVariable() {
        Product product = new Product(new Number(5), new Variable("z"));
        assertEquals("(5.0000 * z)", product.toString());
    }

    @Test
    public void testToStringLeftVariableRightNumber() {
        Product product = new Product(new Variable("a"), new Number(10));
        assertEquals("(a * 10.0000)", product.toString());
    }

    @Test
    public void testToStringNestedProducts() {
        Product innerProduct = new Product(new Variable("x"), new Number(1));
        Product outerProduct = new Product(innerProduct, new Variable("y"));
        assertEquals("((x * 1.0000) * y)", outerProduct.toString());
    }

    // test equals() and hashCode() methods
    @Test
    public void testEqualsAndHashCodeEqualsOnlyNumbers() {
        Product product1 = new Product(new Number(2), new Number(3));
        Product product2 = new Product(new Number(2), new Number(3));
        assertTrue(product1.equals(product2));
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeEqualsOnlyVariables() {
        Product product1 = new Product(new Variable("x"), new Variable("y"));
        Product product2 = new Product(new Variable("x"), new Variable("y"));
        assertTrue(product1.equals(product2));
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeEqualsLeftNumberRightVariable() {
        Product product1 = new Product(new Number(5), new Variable("z"));
        Product product2 = new Product(new Number(5), new Variable("z"));
        assertTrue(product1.equals(product2));
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeEqualsLeftVariableRightNumber() {
        Product product1 = new Product(new Variable("a"), new Number(10));
        Product product2 = new Product(new Variable("a"), new Number(10));
        assertTrue(product1.equals(product2));
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeEqualsNestedProducts() {
        Product innerProduct1 = new Product(new Variable("x"), new Number(1));
        Product outerProduct1 = new Product(innerProduct1, new Variable("y"));
        Product innerProduct2 = new Product(new Variable("x"), new Number(1));
        Product outerProduct2 = new Product(innerProduct2, new Variable("y"));
        assertTrue(outerProduct1.equals(outerProduct2));
        assertEquals(outerProduct1.hashCode(), outerProduct2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeNotEqualsDifferentLeftOperand() {
        Product product1 = new Product(new Number(2), new Number(3));
        Product product2 = new Product(new Number(4), new Number(3));
        assertFalse(product1.equals(product2));
    }

    @Test
    public void testEqualsAndHashCodeNotEqualsDifferentRightOperand() {
        Product product1 = new Product(new Variable("x"), new Variable("y"));
        Product product2 = new Product(new Variable("x"), new Variable("z"));
        assertFalse(product1.equals(product2));
    }

    @Test
    public void testEqualsAndHashCodeNotEqualsDifferentStructure() {
        Product product1 = new Product(new Number(2), new Product(new Number(3), new Number(4)));
        Product product2 = new Product(new Product(new Number(2), new Number(3)), new Number(4));
        assertFalse(product1.equals(product2));
    }

    @Test
    public void testEqualsAndHashCodeNotEqualsDifferentOperandsAndStructure() {
        Product product1 = new Product(new Number(2), new Product(new Variable("x"), new Number(4)));
        Product product2 = new Product(new Product(new Number(3), new Number(2)), new Variable("y"));
        assertFalse(product1.equals(product2));
    }
}