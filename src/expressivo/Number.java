package expressivo;

/**
 * An immutable data type representing a nonnegative integers or floating-point
 * numbers
 * If the number is negative, throw IllegalArgumentException
 */
public class Number implements Expression {
    private final double value;

    // Abstraction function:
    // AF(value) = a numeric expression representing the number 'value'
    // Representation invariant:
    // value is any double greater than or equal to 0
    // Safety from rep exposure:
    // all fields are private and final, and of primitive types

    /**
     * Create a Number expression
     * 
     * @param value
     */
    public Number(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Number value must be nonnegative");
        }
        this.value = value;
        checkRep();
    }

    private void checkRep() {
        assert Double.isFinite(value) : "Number value must be a finite double";
        assert value != Double.NaN : "Number value must be a valid double";
        assert value >= 0 : "Number value must be nonnegative";
    }

    /**
     * @return a string representation of this number, formatted to 4 decimal
     *         places(Round half up).
     */
    @Override
    public String toString() {
        checkRep();
        return String.format("%.4f", value);
    }

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     *         Numbers.
     */
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Number)) {
            return false;
        }
        Number that = (Number) thatObject;
        return this.value == that.value;
    }

    /**
     * @return hash code value consistent with the equals() definition of structural
     *         equality, such that for all n1,n2:Number,
     *         n1.equals(n2) implies n1.hashCode() == n2.hashCode()
     */
    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
