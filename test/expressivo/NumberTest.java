package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the Number immutable data type.
 */
public class NumberTest {
    // Testing strategy
    // * Test toString() method
    // ** Partition the input space as follows:
    // *** integer values (e.g., 0, 1, 42)
    // *** floating-point values (e.g., 0.5, 3.1415, 2.7182)
    // *** boundary values (e.g., 0.0, very large values)
    // *** invalid values (e.g., negative numbers)
    // ** rounding behavior (e.g., values that test round half up)
    // * Test equals() and hashCode() method
    // ** Partition the input space as follows:
    // *** equal int numbers
    // *** equal floating-point numbers
    // *** unequal numbers
    //

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testToString() {
        Number num;

        // Integer values
        num = new Number(42);
        assertEquals("42.0000", num.toString());

        // Floating-point values
        num = new Number(3.1415);
        assertEquals("3.1415", num.toString());

        // Boundary values
        num = new Number(0.0);
        assertEquals("0.0000", num.toString());
    }

    @Test
    public void testToStringLargeValue() {
        Number num;

        // 2^53, largest integer precisely representable as a double
        num = new Number(9007199254740992L);
        assertEquals("9007199254740992.0000", num.toString());

        // the first value that cannot be precisely represented as a double
        num = new Number(9007199254740993L);
        assertEquals("9007199254740992.0000", num.toString());

        num = new Number(9007199254740994L);
        assertEquals("9007199254740994.0000", num.toString());

        num = new Number(9007199254740995L);
        assertEquals("9007199254740996.0000", num.toString());

        num = new Number(9007199254740997L);
        assertEquals("9007199254740996.0000", num.toString());
    }

    @Test
    public void testToStringRoundingBehavior() {
        Number num;

        num = new Number(1.23459);
        assertEquals("1.2346", num.toString());

        num = new Number(1.23455);
        assertEquals("1.2346", num.toString());

        num = new Number(1.23445);
        assertEquals("1.2345", num.toString());

        num = new Number(1.23454);
        assertEquals("1.2345", num.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNegativeNumber() {
        new Number(-5);
    }

    @Test
    public void testEqualsAndHashCode() {
        Number num1, num2;

        // Equal integer numbers
        num1 = new Number(10);
        num2 = new Number(10);
        assertEquals(num1, num2);
        assertEquals(num1.hashCode(), num2.hashCode());

        // Equal floating-point numbers
        num1 = new Number(2.718234);
        num2 = new Number(2.718234);
        assertEquals(num1, num2);
        assertEquals(num1.hashCode(), num2.hashCode());
    }
}
