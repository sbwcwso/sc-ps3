package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the Variable immutable data type.
 */
public class VariableTest {
    // Testing strategy
    // * Test assertions enabled
    // * Test constructor with valid and invalid variable names
    // * Test toString() method
    // ** Partition the input space as follows:
    // *** single-letter variable names (e.g., "x", "Y")
    // *** multi-letter variable names (e.g., "var", "Variable")
    // * Test equals() and hashCode() method
    // ** Partition the input space as follows:
    // *** equal variable names
    // *** unequal variable names
    // *** case-sensitive variable names

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testConstructorValidNames() {
        new Variable("x");
        new Variable("Y");
        new Variable("var");
        new Variable("Variable");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidNameEmpty() {
        new Variable("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidNameWithDigits() {
        new Variable("var1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidNameWithSpecialChars() {
        new Variable("var$");
    }

    @Test
    public void testToStringSingleLetter() {
        Variable var = new Variable("x");
        assertEquals("x", var.toString());
    }

    @Test
    public void testToStringMultiLetter() {
        Variable var = new Variable("Variable");
        assertEquals("Variable", var.toString());
    }

    @Test
    public void testEqualsAndHashCodeEquals() {
        Variable var1 = new Variable("x");
        Variable var2 = new Variable("x");
        assertTrue(var1.equals(var2));
        assertEquals(var1.hashCode(), var2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeNotEquals() {
        Variable var1 = new Variable("x");
        Variable var2 = new Variable("y");
        assertFalse(var1.equals(var2));
    }

    @Test
    public void testEqualsCaseSensitivity() {
        Variable var1 = new Variable("x");
        Variable var2 = new Variable("X");
        assertFalse(var1.equals(var2));
    }
}