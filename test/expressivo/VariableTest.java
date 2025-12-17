package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the Variable immutable data type.
 */
public class VariableTest {
    // Testing strategy
    // * Test assertions enabled
    //
    // * Test constructor with valid and invalid variable names
    // * Test toString() method
    // ** Partition the input space as follows:
    // *** single-letter variable names (e.g., "x", "Y")
    // *** multi-letter variable names (e.g., "var", "Variable")
    //
    // * Test equals() and hashCode() method
    // ** Partition the input space as follows:
    // *** equal variable names
    // *** unequal variable names
    // *** case-sensitive variable names
    //
    // * Test differentiate() method
    // ** Partition the input space as follows:
    // *** differentiate with the same variable name
    // *** differentiate with a different variable name
    //
    // * Test simplify() method
    // ** Partition the input space as follows:
    // *** variable present in environment
    // *** variable absent in environment
    // *** empty environment, 1, 2, or more mappings
    // *** environment with extra variables not in expression
    // *** variable with different case in environment
    // *** variable with multi-letter name in environment

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

    @Test
    public void testDifferentiateSameVariable() {
        Variable var = new Variable("x");
        Expression derivative = var.differentiate("x");
        assertEquals(new Number(1), derivative);
    }

    @Test
    public void testDifferentiateDifferentVariable() {
        Variable var = new Variable("x");
        Expression derivative = var.differentiate("y");
        assertEquals(new Number(0), derivative);
    }

    // simplify() partition tests
    @Test
    public void testSimplify_VariablePresentInEnv() {
        Variable v = new Variable("x");
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 10.0);
        assertEquals(new Number(10), v.simplify(env));
    }

    @Test
    public void testSimplify_VariableAbsentInEnv() {
        Variable v = new Variable("x");
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("y", 2.0);
        assertEquals(v, v.simplify(env));
    }

    @Test
    public void testSimplify_EmptyEnv_One_Two_ManyMappings() {
        Variable v = new Variable("x");

        // empty
        assertEquals(v, v.simplify(java.util.Collections.emptyMap()));

        // one mapping (not present)
        java.util.Map<String, Double> env1 = new java.util.HashMap<>();
        env1.put("a", 1.0);
        assertEquals(v, v.simplify(env1));

        // two mappings (present)
        java.util.Map<String, Double> env2 = new java.util.HashMap<>();
        env2.put("x", 3.0);
        env2.put("b", 2.0);
        assertEquals(new Number(3), v.simplify(env2));

        // many mappings
        java.util.Map<String, Double> envN = new java.util.HashMap<>();
        envN.put("p", 1.0);
        envN.put("q", 2.0);
        envN.put("x", 4.5);
        envN.put("r", 3.0);
        assertEquals(new Number(4.5), v.simplify(envN));
    }

    @Test
    public void testSimplify_EnvHasExtraVariablesIgnored() {
        Variable v = new Variable("x");
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("x", 7.0);
        env.put("unused1", 0.0);
        env.put("unused2", 9.0);
        assertEquals(new Number(7), v.simplify(env));
    }

    @Test
    public void testSimplify_VariableDifferentCaseInEnv() {
        Variable v = new Variable("x");
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("X", 8.0); // different case should not match
        assertEquals(v, v.simplify(env));
    }

    @Test
    public void testSimplify_MultiLetterVariableName() {
        Variable v = new Variable("varName");
        java.util.Map<String, Double> env = new java.util.HashMap<>();
        env.put("varName", 2.5);
        assertEquals(new Number(2.5), v.simplify(env));
    }
}