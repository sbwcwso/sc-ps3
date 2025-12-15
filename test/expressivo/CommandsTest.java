/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy
    // * Test assertions enabled
    //
    // * Test Commands.differentiate() method
    // ** Partition the input space as follows:
    // *** expressions with only single numbers
    // *** expressions with only single variables
    // *** expressions with sums only
    // *** expressions with products only
    // *** expressions with both sums and products， no parentheses， testing
    // operator precedence
    // *** expressions with parentheses altering precedence
    // *** expressions with both numbers and variables
    // *** expressions with nested sums and products
    // *** invalid expressions
    // **** expressions with invalid characters
    // **** expressions with unbalanced parentheses
    // **** expressions with invalid syntax
    // ***** expressions with negative numbers
    // ***** expressions with missing operands
    // ***** expressions with invalid variable names

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // tests for Commands.differentiate()

    // Valid expressions: single numbers
    /* D01: single number, differentiate w.r.t any variable */
    @Test
    public void testDifferentiateSingleNumber01() {
        String result = Commands.differentiate("5", "x");
        assertEquals("0.0000", result);
    }

    /* D02: single number, differentiate w.r.t different variable */
    @Test
    public void testDifferentiateSingleNumber02() {
        String result = Commands.differentiate("100", "y");
        assertEquals("0.0000", result);
    }

    // Valid expressions: single variables
    /* D03: single variable, differentiate w.r.t same variable */
    @Test
    public void testDifferentiateSingleVariable01() {
        String result = Commands.differentiate("x", "x");
        assertEquals("1.0000", result);
    }

    /* D04: single variable, differentiate w.r.t different variable */
    @Test
    public void testDifferentiateSingleVariable02() {
        String result = Commands.differentiate("x", "y");
        assertEquals("0.0000", result);
    }

    /* D05: single variable, differentiate w.r.t itself */
    @Test
    public void testDifferentiateSingleVariable03() {
        String result = Commands.differentiate("a", "a");
        assertEquals("1.0000", result);
    }

    // Valid expressions: sums only
    /* D06: simple sum of numbers, differentiate w.r.t x */
    @Test
    public void testDifferentiateSumOnly01() {
        String result = Commands.differentiate("2 + 3", "x");
        // d/dx(2 + 3) = d/dx(2) + d/dx(3) = 0 + 0 = 0
        assertEquals("0.0000", result);
    }

    /* D07: sum of variables, differentiate w.r.t one of them */
    @Test
    public void testDifferentiateSumOnly02() {
        String result = Commands.differentiate("x + y", "x");
        // d/dx(x + y) = d/dx(x) + d/dx(y) = 1 + 0 = 1
        assertEquals("1.0000", result);
    }

    /* D08: nested sums, differentiate w.r.t variable */
    @Test
    public void testDifferentiateSumOnly03() {
        String result = Commands.differentiate("x + (2 + y)", "x");
        // d/dx(x + (2 + y)) = d/dx(x) + d/dx(2 + y) = 1 + (0 + 0) = 1
        assertEquals("1.0000", result);
    }

    // Valid expressions: products only
    /* D09: simple product of numbers, differentiate w.r.t x */
    @Test
    public void testDifferentiateProductOnly01() {
        String result = Commands.differentiate("2 * 3", "x");
        // d/dx(2 * 3) = (d/dx(2) * 3) + (2 * d/dx(3)) = (0 * 3) + (2 * 0)
        assertEquals("0.0000", result);
    }

    /* D10: product of variables, differentiate w.r.t one of them */
    @Test
    public void testDifferentiateProductOnly02() {
        String result = Commands.differentiate("x * y", "x");
        // d/dx(x * y) = (d/dx(x) * y) + (x * d/dx(y)) = (1 * y) + (x * 0)
        assertEquals("y", result);
    }

    /* D11: nested products, differentiate w.r.t variable */
    @Test
    public void testDifferentiateProductOnly03() {
        String result = Commands.differentiate("x * (2 * y)", "x");
        // d/dx(x * (2*y)) = (d/dx(x) * (2*y)) + (x * d/dx(2*y))
        // = (1 * (2*y)) + (x * ((0*y) + (2*0)))
        assertEquals("(2.0000 * y)", result);
    }

    // Valid expressions: both sums and products, no parentheses (operator
    // precedence)
    /*
     * D12: sum and product no parentheses (product has higher precedence),
     * differentiate w.r.t x
     */
    @Test
    public void testDifferentiatePrecedence01() {
        String result = Commands.differentiate("x + 2 * y", "x");
        // Parsed as x + (2 * y): d/dx(x + (2*y)) = d/dx(x) + d/dx(2*y) = 1 + ((0*y) +
        // (2*0))
        assertEquals("1.0000", result);
    }

    /* D13: multiple products and sums, left-to-right */
    @Test
    public void testDifferentiatePrecedence02() {
        String result = Commands.differentiate("2 * x + 3 * y", "x");
        // Parsed as (2*x) + (3*y): d/dx = d/dx(2*x) + d/dx(3*y) = ((0*x) + (2*1)) +
        // ((0*y) + (3*0))
        assertEquals("2.0000", result);
    }

    /* D14: sum of products */
    @Test
    public void testDifferentiatePrecedence03() {
        String result = Commands.differentiate("x * y + a * b", "x");
        // d/dx((x*y) + (a*b)) = d/dx(x*y) + d/dx(a*b) = ((1*y) + (x*0)) + ((0*b) +
        // (a*0))
        assertEquals("y", result);
    }

    // Valid expressions: parentheses altering precedence
    /* D15: parentheses change precedence: (x + y) * z */
    @Test
    public void testDifferentiateParentheses01() {
        String result = Commands.differentiate("(x + y) * z", "x");
        // d/dx((x+y)*z) = (d/dx(x+y) * z) + ((x+y) * d/dx(z)) = ((1+0) * z) + ((x+y) *
        // 0)
        assertEquals("z", result);
    }

    /* D16: nested parentheses */
    @Test
    public void testDifferentiateParentheses02() {
        String result = Commands.differentiate("((x + 2) * y)", "x");
        // d/dx((x+2)*y) = (d/dx(x+2) * y) + ((x+2) * d/dx(y)) = ((1+0) * y) + ((x+2) *
        // 0)
        assertEquals("y", result);
    }

    /* D17: multiple parentheses groups */
    @Test
    public void testDifferentiateParentheses03() {
        String result = Commands.differentiate("(x + 1) * (y + 2)", "x");
        // d/dx((x+1)*(y+2)) = (d/dx(x+1) * (y+2)) + ((x+1) * d/dx(y+2)) = ((1+0) *
        // (y+2)) + ((x+1) * (0+0))
        assertEquals("(y + 2.0000)", result);
    }

    // Valid expressions: both numbers and variables
    /* D18: number + variable, differentiate w.r.t variable */
    @Test
    public void testDifferentiateNumberAndVariable01() {
        String result = Commands.differentiate("5 + x", "x");
        // d/dx(5 + x) = 0 + 1 = 1
        assertEquals("1.0000", result);
    }

    /* D19: number * variable, differentiate w.r.t variable */
    @Test
    public void testDifferentiateNumberAndVariable02() {
        String result = Commands.differentiate("3 * x", "x");
        // d/dx(3 * x) = (d/dx(3) * x) + (3 * d/dx(x)) = (0 * x) + (3 * 1)
        assertEquals("3.0000", result);
    }

    /* D20: complex mix of numbers and variables */
    @Test
    public void testDifferentiateNumberAndVariable03() {
        String result = Commands.differentiate("2 * x + 5 * y + 10", "x");
        // Parsed as (2*x + 5*y) + 10: d/dx = d/dx(2*x) + d/dx(5*y) + d/dx(10) = ((0*x)
        // + (2*1)) + ((0*y) + (5*0)) + 0
        // With simplification dropping trailing +0: omit the final "+ 0.0000"
        assertEquals("2.0000", result);
    }

    // Valid expressions: nested sums and products
    /* D21: nested sum inside product */
    @Test
    public void testDifferentiateNested01() {
        String result = Commands.differentiate("(x + y) * (a + b)", "x");
        // d/dx((x+y)*(a+b)) = (d/dx(x+y) * (a+b)) + ((x+y) * d/dx(a+b))
        // = ((1+0) * (a+b)) + ((x+y) * (0+0))
        assertEquals("(a + b)", result);
    }

    /* D22: deeply nested structure */
    @Test
    public void testDifferentiateNested02() {
        String result = Commands.differentiate("x * (y + (z * a))", "x");
        // d/dx(x * (y + (z*a)))
        // = (d/dx(x) * (y + (z*a))) + (x * d/dx(y + (z*a)))
        // = (1 * (y + (z*a))) + (x * (d/dx(y) + d/dx(z*a)))
        // = (1 * (y + (z*a))) + (x * (0 + ((0*a) + (z*0))))
        // Simplify (0 + E) to E inside the nested sum
        assertEquals("(y + (z * a))", result);
    }

    /* D23: nested sums and products with multiple variables */
    @Test
    public void testDifferentiateNested03() {
        String result = Commands.differentiate("(x + y) * (x * y) + z", "x");
        // d/dx(((x+y)*(x*y)) + z) = d/dx((x+y)*(x*y)) + 0
        // = (d/dx(x+y) * (x*y)) + ((x+y) * d/dx(x*y))
        // = ((1+0) * (x*y)) + ((x+y) * ((1*y) + (x*0)))
        // With outer sum: (... + 0)
        // Drop outer +0 from the final sum
        assertEquals("((x * y) + ((x + y) * y))", result);
    }

    // Invalid expressions: invalid characters
    /* INV01: expression with invalid character $ */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateInvalidCharacter01() {
        Commands.differentiate("x + $y", "x");
    }

    /* INV02: expression with @ character */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateInvalidCharacter02() {
        Commands.differentiate("2 @ 3", "x");
    }

    /* INV03: expression with invalid operator & */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateInvalidCharacter03() {
        Commands.differentiate("x & y", "x");
    }

    // Invalid expressions: unbalanced parentheses
    /* INV04: missing closing parenthesis */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateUnbalancedParentheses01() {
        Commands.differentiate("(x + y", "x");
    }

    /* INV05: missing opening parenthesis */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateUnbalancedParentheses02() {
        Commands.differentiate("x + y)", "x");
    }

    /* INV06: extra closing parenthesis in middle */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateUnbalancedParentheses03() {
        Commands.differentiate("(x + y) + z)", "x");
    }

    // Invalid expressions: invalid syntax
    /* INV07: consecutive operators */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateInvalidSyntax01() {
        Commands.differentiate("x + + y", "x");
    }

    /* INV08: operator at end */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateInvalidSyntax02() {
        Commands.differentiate("x + y +", "x");
    }

    /* INV09: operator at beginning */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateInvalidSyntax03() {
        Commands.differentiate("+ x + y", "x");
    }

    // Invalid expressions: negative numbers
    /* INV10: negative number */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateNegativeNumber01() {
        Commands.differentiate("-5", "x");
    }

    /* INV11: negative number in expression */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateNegativeNumber02() {
        Commands.differentiate("x + -3", "x");
    }

    /* INV12: negative number as operand */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateNegativeNumber03() {
        Commands.differentiate("-x * 2", "x");
    }

    // Invalid expressions: missing operands
    /* INV13: empty parentheses */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateMissingOperand01() {
        Commands.differentiate("()", "x");
    }

    /* INV14: parentheses with only operator */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateMissingOperand02() {
        Commands.differentiate("(+)", "x");
    }

    /* INV15: missing right operand */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateMissingOperand03() {
        Commands.differentiate("x +", "x");
    }

    // Invalid expressions: invalid variable names
    /* INV16: variable with numbers */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateInvalidVariableName01() {
        Commands.differentiate("x1 + y", "x");
    }

    /* INV17: variable with special character */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateInvalidVariableName02() {
        Commands.differentiate("x_y + z", "x");
    }

    /* INV18: expression with space in variable name */
    @Test(expected = IllegalArgumentException.class)
    public void testDifferentiateInvalidVariableName03() {
        Commands.differentiate("x y + z", "x");
    }

}
