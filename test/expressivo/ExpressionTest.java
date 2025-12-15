/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    // * Test assertions enabled
    // * Test static parse() method
    // ** Partition the input space as follows:
    // *** valid expressions
    // **** expressions with only single numbers
    // **** expressions with only single variables
    // **** expressions with sums only
    // **** expressions with products only
    // **** expressions with both sums and products， no parentheses， testing
    // operator precedence
    // **** expressions with parentheses altering precedence
    // **** expressions with both numbers and variables
    // **** expressions with nested sums and products
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

    // * test static parse() method
    // ** valid expressions
    @Test
    public void testParseSingleNumber() {
        Expression expr = Expression.parse("42");
        assertEquals(new Number(42), expr);
    }

    @Test
    public void testParseSingleVariable() {
        Expression expr = Expression.parse("x");
        assertEquals(new Variable("x"), expr);
    }

    @Test
    public void testParseSumOnly() {
        Expression expr = Expression.parse("2 + 3 + x");
        Expression expected = new Sum(new Sum(new Number(2), new Number(3)), new Variable("x"));
        assertEquals(expected, expr);
    }

    @Test
    public void testParseProductOnly() {
        Expression expr = Expression.parse("2 * 3 * x");
        Expression expected = new Product(new Product(new Number(2), new Number(3)), new Variable("x"));
        assertEquals(expected, expr);
    }

    @Test
    public void testParseMixedOperatorsNoParenthesesPrecedence() {
        Expression expr = Expression.parse("2 + 3 * x");
        Expression expected = new Sum(new Number(2), new Product(new Number(3), new Variable("x")));
        assertEquals(expected, expr);
    }

    @Test
    public void testParseWithParenthesesAlteringPrecedence() {
        Expression expr = Expression.parse("(2 + 3) * x");
        Expression expected = new Product(new Sum(new Number(2), new Number(3)), new Variable("x"));
        assertEquals(expected, expr);
    }

    @Test
    public void testParseWithNumbersAndVariables() {
        Expression expr = Expression.parse("2 * x + 3 * y");
        Expression expected = new Sum(new Product(new Number(2), new Variable("x")),
                new Product(new Number(3), new Variable("y")));
        assertEquals(expected, expr);
    }

    @Test
    public void testParseWithNestedSumsAndProducts() {
        Expression expr = Expression.parse("(2 + x) * (3 + y) * (4 + x + z)");
        Expression expected = new Product(
                new Product(new Sum(new Number(2), new Variable("x")), new Sum(new Number(3), new Variable("y"))),
                new Sum(new Sum(new Number(4), new Variable("x")), new Variable("z")));
        assertEquals(expected, expr);
    }

    // ** invalid expressions
    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalidCharacters() {
        Expression.parse("2 + 3 & x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseUnbalancedParentheses() {
        Expression.parse("(2 + 3 * x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseNegativeNumber() {
        Expression.parse("-5 + x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseMissingOperand() {
        Expression.parse("2 + * x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalidVariableName() {
        Expression.parse("2 + 3 * 4x");
    }
}