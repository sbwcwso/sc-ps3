package expressivo;

/**
 * An immutable data type representing a nonnegative integers or floating-point
 * numbers
 * If the number is negative, throw IllegalArgumentException
 */
public class Number implements ExpressionWithOperation {
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

    /**
     * Differentiate this number with respect to a variable.
     * 
     * @param variable the variable to differentiate by, a case-sensitive
     *                 nonempty string of letters.
     * @return return Number(0).
     */
    @Override
    public Expression differentiate(String variable) {
        return new Number(0);
    }

    /**
     * Simplify this number with respect to an environment.
     * 
     * @param environment maps variables to values. Variables are required to be
     *                    case-sensitive nonempty strings of letters. The set of
     *                    variables in environment is allowed to be different than
     *                    the set of variables actually found in expression. Values
     *                    must be nonnegative numbers.
     * @return this number itself.
     */
    @Override
    public Expression simplify(java.util.Map<String, Double> environment) {
        return this;
    }

    /**
     * Helper for double dispatch: add this number to an expression on the right.
     * This method is called when the left operand of the addition is known to be a
     * Number.
     * 
     * @param right the right expression
     * @return the sum of this and right
     *         if this number is 0, return right
     *         otherwise, delegate to right.plusLeftNumber(this)
     */
    @Override
    public Expression addToRight(Expression right) {
        // this is the left operand; drop it if it's zero
        if (value == 0) {
            return right;
        }
        return ((ExpressionWithOperation) right).addWithLeftNumber(this);
    }

    /**
     * Helper for double dispatch: add this number to an expression on the left.
     * This method is called when the right operand of the addition is known to be a
     * Number.
     * 
     * @param leftNumber the left number
     * @return the sum of leftNumber and this
     */
    public Expression addWithLeftNumber(Number leftNumber) {
        return new Number(leftNumber.value + this.value);
    }

    /**
     * Helper for double dispatch: add this number to an expression on the left.
     * This method is called when the right operand of the addition is known to be
     * not a Number.
     * 
     * @param left the left expression
     * @return the sum of left and this
     *         if this number is 0, return left
     *         otherwise, delegate to left.plusRightNumber(this)
     */
    @Override
    public Expression addToLeft(Expression left) {
        // this is the right operand; drop it if it's zero
        if (this.value == 0) {
            return left;
        }
        return ((ExpressionWithOperation) left).addWithRightNumber(this);
    }

    /**
     * Helper for double dispatch: add this number to a Number on the right.
     * This method is called when the right operand of the addition is known to be a
     * Number.
     * 
     * @param rightNumber the number on the right side of the addition
     * @return the sum of this and rightNumber
     */
    @Override
    public Expression addWithRightNumber(Number left) {
        // both operands are numbers: fold
        return new Number(left.value + this.value);
    }

    /**
     * Helper for double dispatch: multiply this number to an expression on the
     * right.
     * This method is called when the left operand of the multiplication is known
     * to be a Number.
     * 
     * @param right the right expression
     * @return the product of this and right
     *         if this number is 0, return Number(0)
     *         if this number is 1, return right
     *         otherwise, delegate to right.mulLeft(this)
     */
    @Override
    public Expression mulRight(Expression right) {
        if (this.value == 0) {
            return new Number(0);
        }
        if (this.value == 1) {
            return right;
        }
        return ((ExpressionWithOperation) right).mulLeftNumber(this);
    }

    /**
     * Helper for double dispatch: multiply this number to an expression on the
     * left.
     * This method is called when the right operand of the multiplication is known
     * to be a Number.
     * 
     * @param rightNumber the number on the right side of the multiplication
     * @return the product of this and rightNumber
     */
    public Expression mulLeftNumber(Number rightNumber) {
        // both operands are numbers: fold
        return new Number(this.value * rightNumber.value);
    }

    /**
     * Helper for double dispatch: multiply this number to an expression on the
     * left.
     * This method is called when the right operand of the multiplication is known
     * to be a Number.
     * 
     * @param left the left expression
     * @return the product of left and this
     *         if this number is 0, return Number(0)
     *         if this number is 1, return left
     *         otherwise, delegate to left.mulRightNumber(this)
     */
    @Override
    public Expression mulLeft(Expression left) {
        if (this.value == 0) {
            return new Number(0);
        }
        if (this.value == 1) {
            return left;
        }
        return ((ExpressionWithOperation) left).mulRightNumber(this);
    }

    /**
     * Helper for double dispatch: multiply this number to a Number on the left.
     * This method is called when the right operand of the multiplication is known
     * to be a Number.
     * 
     * @param leftNumber the left number
     * @return the product of this and leftNumber
     */
    @Override
    public Expression mulRightNumber(Number leftNumber) {
        // both operands are numbers: fold
        return new Number(this.value * leftNumber.value);
    }
}
