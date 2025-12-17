package expressivo;

import java.util.Map;

/**
 * An immutable data type representing a variable (case-sensitive nonempty
 * strings of letters)
 * The variable name must be a nonempty string of letters, or else an
 * IllegalArgumentException is thrown
 * 
 */
public class Variable implements ExpressionWithOperation {
    private final String name;

    // Abstraction function:
    // AF(name) = a variable expression representing the variable 'name'
    // Representation invariant:
    // name is a nonempty string of letters
    // Safety from rep exposure:
    // all fields are private and final, and of primitive types

    /**
     * Create a Variable expression
     * throw IllegalArgumentException if the name is invalid
     * 
     * @param name
     */
    public Variable(String name) {
        if (name.matches("[a-zA-z]+") == false) {
            throw new IllegalArgumentException("Variable name must be a nonempty string of letters");
        }
        this.name = name;
        checkRep();
    }

    private void checkRep() {
        assert name != null && name.matches("[a-zA-Z]+") : "Variable name must be a nonempty string of letters";
    }

    /**
     * 
     * @return a string representation of this variable.
     *         It is simply the input variable name.
     */
    @Override
    public String toString() {
        checkRep();
        return name;
    }

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     *         Variables.
     */
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Variable)) {
            return false;
        }
        Variable that = (Variable) thatObject;
        return this.name.equals(that.name);
    }

    /**
     * @return hash code value consistent with the equals() definition of structural
     *         equality, such that for all v1,v2:Variable,
     *         v1.equals(v2) implies v1.hashCode() == v2.hashCode()
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Differentiate this variable with respect to a variable.
     * 
     * @param variable the variable to differentiate by, a case-sensitive
     *                 nonempty string of letters.
     * @return If the variable is the same as this variable, return Number(1); else
     *         return Number(0).
     */
    @Override
    public Expression differentiate(String variable) {
        return new Number(this.name.equals(variable) ? 1 : 0);
    }

    /**
     * Simplify this variable with respect to an environment.
     * 
     * @param environment maps variables to values. Variables are required to be
     *                    case-sensitive nonempty strings of letters. The set of
     *                    variables in environment is allowed to be different than
     *                    the set of variables actually found in expression. Values
     *                    must be nonnegative numbers.
     * @return If this variable's name is in the environment, return Number with the
     *         corresponding value; else return this variable itself.
     */
    @Override
    public Expression simplify(Map<String, Double> environment) {
        return environment.containsKey(this.name) ? new Number(environment.get(this.name)) : this;
    }
}
