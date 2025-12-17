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
    // * All the valid expressions
    // ** Only numbers
    // *** Single number
    // *** Only Sum of two numbers
    // *** Only Product of two numbers
    // *** Only Sums of more than two numbers
    // *** Only Products of more than two numbers
    // *** Nested Sums and Products, no parentheses, testing operator precedence
    // *** Nested Sums and Products with parentheses altering precedence
    // ** Only variables
    // *** Single variable
    // *** Only Sum of two variables
    // *** Only Product of two variables
    // *** Only Sums of more than two variables
    // *** Only Products of more than two variables
    // *** Nested Sums and Products, no parentheses, testing operator precedence
    // *** Nested Sums and Products with parentheses altering precedence
    // ** Mix of numbers and variables
    // *** Sum of a number and a variable
    // *** Product of a number and a variable
    // *** Sums of numbers and variables
    // *** Products of numbers and variables
    // *** Nested Sums and Products, no parentheses, testing operator precedence
    // *** Nested Sums and Products with parentheses altering precedence
    //
    // * All the invalid expressions
    // ** Expressions with invalid characters
    // ** Expressions with unbalanced parentheses
    // ** Expressions with invalid syntax
    // *** Expressions with negative numbers
    // *** Expressions with missing operands
    // *** Expressions with invalid variable names

    // * Test Commands.differentiate() method
    // ** test all the valid expressions:
    // *** On every valid expression, differentiate with respect to:
    // **** the same variable (if variable present)
    // **** a different variable
    // *** test all the invalid expressions

    // * Test Commands.simplify() method
    // ** test all the valid expressions:
    // *** On every valid expression, simplify with respect to:
    // **** an environment where no variables are present in the expression
    // **** an environment where some variables are present in the expression
    // **** an environment where all variables are present in the expression
    // ** test all the invalid expressions

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

    // tests for Commands.simplify()

    // Valid expressions: Only numbers
    /* S01: single number, empty env */
    @Test
    public void testSimplifyOnlyNumber01() {
        String result = Commands.simplify("5", java.util.Collections.emptyMap());
        assertEquals("5.0000", result);
    }

    /* S02: sum of two numbers, empty env */
    @Test
    public void testSimplifyOnlyNumber02() {
        String result = Commands.simplify("2 + 3", java.util.Collections.emptyMap());
        assertEquals("5.0000", result);
    }

    /* S03: product of two numbers, empty env */
    @Test
    public void testSimplifyOnlyNumber03() {
        String result = Commands.simplify("2 * 4", java.util.Collections.emptyMap());
        assertEquals("8.0000", result);
    }

    /* S04: sums of more than two numbers, empty env */
    @Test
    public void testSimplifyOnlyNumber04() {
        String result = Commands.simplify("1 + 2 + 3", java.util.Collections.emptyMap());
        assertEquals("6.0000", result);
    }

    /* S05: products of more than two numbers, empty env */
    @Test
    public void testSimplifyOnlyNumber05() {
        String result = Commands.simplify("2 * 3 * 4", java.util.Collections.emptyMap());
        assertEquals("24.0000", result);
    }

    /* S06: nested sums/products no parentheses (precedence) */
    @Test
    public void testSimplifyOnlyNumber06() {
        String result = Commands.simplify("1 + 2 * 3 + 4", java.util.Collections.emptyMap());
        // parsed as 1 + (2*3) + 4 = 1 + 6 + 4 = 11
        assertEquals("11.0000", result);
    }

    /* S07: nested sums/products with parentheses altering precedence */
    @Test
    public void testSimplifyOnlyNumber07() {
        String result = Commands.simplify("(1 + 2) * (3 + 4)", java.util.Collections.emptyMap());
        // (3) * (7) = 21
        assertEquals("21.0000", result);
    }

    // Valid expressions: Only variables
    /* S08: single variable, empty env */
    @Test
    public void testSimplifyOnlyVariable01() {
        String result = Commands.simplify("x", java.util.Collections.emptyMap());
        assertEquals("x", result);
    }

    /* S09: sum of two variables, empty env */
    @Test
    public void testSimplifyOnlyVariable02() {
        String result = Commands.simplify("x + y", java.util.Collections.emptyMap());
        assertEquals("(x + y)", result);
    }

    /* S10: product of two variables, empty env */
    @Test
    public void testSimplifyOnlyVariable03() {
        String result = Commands.simplify("x * y", java.util.Collections.emptyMap());
        assertEquals("(x * y)", result);
    }

    /* S11: sums of more than two variables */
    @Test
    public void testSimplifyOnlyVariable04() {
        String result = Commands.simplify("a + b + c", java.util.Collections.emptyMap());
        assertEquals("((a + b) + c)", result);
    }

    /* S12: products of more than two variables */
    @Test
    public void testSimplifyOnlyVariable05() {
        String result = Commands.simplify("a * b * c", java.util.Collections.emptyMap());
        assertEquals("((a * b) * c)", result);
    }

    /* S13: nested sums and products no parentheses (precedence) */
    @Test
    public void testSimplifyOnlyVariable06() {
        String result = Commands.simplify("x + y * z", java.util.Collections.emptyMap());
        assertEquals("(x + (y * z))", result);
    }

    /* S14: nested with parentheses altering precedence */
    @Test
    public void testSimplifyOnlyVariable07() {
        String result = Commands.simplify("(x + y) * z", java.util.Collections.emptyMap());
        assertEquals("((x + y) * z)", result);
    }

    /* S15: variable bindings: some present */
    @Test
    public void testSimplifyOnlyVariableBindingsSome() {
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 2.0);
        String result = Commands.simplify("x + y", env);
        assertEquals("(2.0000 + y)", result);
    }

    /* S16: variable bindings: all present */
    @Test
    public void testSimplifyOnlyVariableBindingsAll() {
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 2.0);
        env.put("y", 3.0);
        String result = Commands.simplify("x + y", env);
        assertEquals("5.0000", result);
    }

    // Valid expressions: Mix of numbers and variables
    /* S17: Sum of a number and a variable, empty env */
    @Test
    public void testSimplifyNumVar01() {
        String result = Commands.simplify("5 + x", java.util.Collections.emptyMap());
        assertEquals("(5.0000 + x)", result);
    }

    /* S18: Product of a number and a variable, with variable bound */
    @Test
    public void testSimplifyNumVar02() {
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 3.0);
        String result = Commands.simplify("5 * x", env);
        assertEquals("15.0000", result);
    }

    /* S19: Sums of numbers and variables, some bindings */
    @Test
    public void testSimplifyNumVar03() {
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 4.0);
        String result = Commands.simplify("2 + x + y", env);
        assertEquals("((2.0000 + x) + 4.0000)", result);
    }

    /* S20: Products of numbers and variables, some bindings */
    @Test
    public void testSimplifyNumVar04() {
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 5.0);
        String result = Commands.simplify("2 * x * y", env);
        assertEquals("(10.0000 * y)", result);
    }

    /* S21: nested no parentheses (precedence), some bindings */
    @Test
    public void testSimplifyNumVar05() {
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 2.0);
        String result = Commands.simplify("x + 2 * y", env);
        assertEquals("(2.0000 + (2.0000 * y))", result);
    }

    /* S22: nested with parentheses altering precedence, all bindings */
    @Test
    public void testSimplifyNumVar06() {
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 1.0);
        env.put("y", 2.0);
        String result = Commands.simplify("(x + 2) * y", env);
        // (1+2)*2 = 6
        assertEquals("6.0000", result);
    }

    // Invalid expressions for simplify(): invalid characters
    /* SINV01: expression with invalid character $ */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyInvalidCharacter01() {
        Commands.simplify("x + $y", java.util.Collections.emptyMap());
    }

    /* SINV02: expression with @ character */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyInvalidCharacter02() {
        Commands.simplify("2 @ 3", java.util.Collections.emptyMap());
    }

    /* SINV03: expression with invalid operator & */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyInvalidCharacter03() {
        Commands.simplify("x & y", java.util.Collections.emptyMap());
    }

    // Invalid expressions: unbalanced parentheses
    /* SINV04: missing closing parenthesis */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyUnbalancedParentheses01() {
        Commands.simplify("(x + y", java.util.Collections.emptyMap());
    }

    /* SINV05: missing opening parenthesis */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyUnbalancedParentheses02() {
        Commands.simplify("x + y)", java.util.Collections.emptyMap());
    }

    /* SINV06: extra closing parenthesis in middle */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyUnbalancedParentheses03() {
        Commands.simplify("(x + y) + z)", java.util.Collections.emptyMap());
    }

    // Invalid expressions: invalid syntax
    /* SINV07: consecutive operators */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyInvalidSyntax01() {
        Commands.simplify("x + + y", java.util.Collections.emptyMap());
    }

    /* SINV08: operator at end */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyInvalidSyntax02() {
        Commands.simplify("x + y +", java.util.Collections.emptyMap());
    }

    /* SINV09: operator at beginning */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyInvalidSyntax03() {
        Commands.simplify("+ x + y", java.util.Collections.emptyMap());
    }

    // Invalid expressions: negative numbers
    /* SINV10: negative number */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyNegativeNumber01() {
        Commands.simplify("-5", java.util.Collections.emptyMap());
    }

    /* SINV11: negative number in expression */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyNegativeNumber02() {
        Commands.simplify("x + -3", java.util.Collections.emptyMap());
    }

    /* SINV12: negative number as operand */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyNegativeNumber03() {
        Commands.simplify("-x * 2", java.util.Collections.emptyMap());
    }

    // Invalid expressions: missing operands
    /* SINV13: empty parentheses */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyMissingOperand01() {
        Commands.simplify("()", java.util.Collections.emptyMap());
    }

    /* SINV14: parentheses with only operator */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyMissingOperand02() {
        Commands.simplify("(+)", java.util.Collections.emptyMap());
    }

    /* SINV15: missing right operand */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyMissingOperand03() {
        Commands.simplify("x +", java.util.Collections.emptyMap());
    }

    // Invalid expressions: invalid variable names
    /* SINV16: variable with numbers */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyInvalidVariableName01() {
        Commands.simplify("x1 + y", java.util.Collections.emptyMap());
    }

    /* SINV17: variable with special character */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyInvalidVariableName02() {
        Commands.simplify("x_y + z", java.util.Collections.emptyMap());
    }

    /* SINV18: expression with space in variable name */
    @Test(expected = IllegalArgumentException.class)
    public void testSimplifyInvalidVariableName03() {
        Commands.simplify("x y + z", java.util.Collections.emptyMap());
    }

    // Additional simplify tests (missing categories)
    /* S23: three-term mixed sum, empty env (no vars bound) */
    @Test
    public void testSimplifyNumVar07_emptyEnv() {
        String result = Commands.simplify("2 + x + y", java.util.Collections.emptyMap());
        assertEquals("((2.0000 + x) + y)", result);
    }

    /* S24: three-term mixed sum, all vars bound */
    @Test
    public void testSimplifyNumVar08_allBound() {
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 3.0);
        env.put("y", 4.0);
        String result = Commands.simplify("2 + x + y", env);
        assertEquals("9.0000", result);
    }

    /* S25: additive identity x + 0 simplifies to x */
    @Test
    public void testSimplifyIdentityAddZero1() {
        String result = Commands.simplify("x + 0", java.util.Collections.emptyMap());
        assertEquals("x", result);
    }

    /* S26: additive identity 0 + x simplifies to x */
    @Test
    public void testSimplifyIdentityAddZero2() {
        String result = Commands.simplify("0 + x", java.util.Collections.emptyMap());
        assertEquals("x", result);
    }

    /* S27: multiplicative identity x * 1 simplifies to x */
    @Test
    public void testSimplifyIdentityMulOne1() {
        String result = Commands.simplify("x * 1", java.util.Collections.emptyMap());
        assertEquals("x", result);
    }

    /* S28: multiplicative identity 1 * x simplifies to x */
    @Test
    public void testSimplifyIdentityMulOne2() {
        String result = Commands.simplify("1 * x", java.util.Collections.emptyMap());
        assertEquals("x", result);
    }

    /* S29: multiplicative zero x * 0 simplifies to 0 */
    @Test
    public void testSimplifyMulZero() {
        String result = Commands.simplify("x * 0", java.util.Collections.emptyMap());
        assertEquals("0.0000", result);
    }

    /* S30: multi-letter variable names and case sensitivity, empty env */
    @Test
    public void testSimplifyMultiLetterVars_empty() {
        String result = Commands.simplify("foo + Bar", java.util.Collections.emptyMap());
        assertEquals("(foo + Bar)", result);
    }

    /* S31: multi-letter variable partial binding */
    @Test
    public void testSimplifyMultiLetterVars_someBound() {
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("foo", 2.0);
        String result = Commands.simplify("foo + Bar", env);
        assertEquals("(2.0000 + Bar)", result);
    }

    /* S32: float formatting preserved in simplify */
    @Test
    public void testSimplifyFloatFormatting() {
        String result = Commands.simplify("x + 2.5", java.util.Collections.emptyMap());
        assertEquals("(x + 2.5000)", result);
    }

    /* S33: case-sensitivity in variable bindings */
    @Test
    public void testSimplifyCaseSensitivity() {
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("foo", 2.0);
        String result = Commands.simplify("Foo + foo", env);
        assertEquals("(Foo + 2.0000)", result);
    }
}
