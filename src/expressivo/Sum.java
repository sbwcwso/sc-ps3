package expressivo;

/**
 * An immutable sum expression of two Expressions.
 */
public class Sum implements ExpressionWithOperation {
    private final Expression left;
    private final Expression right;

    // Abstraction function:
    // AF(left, right) = a sum expression representing the sum of 'left' and
    // 'right'
    // Representation invariant:
    // left and right are non-null Expressions
    // left and right are all immutable Expressions
    // Safety from rep exposure:
    // all fields are private and final, and of immutable types

    /**
     * Create a Sum expression
     * 
     * @param left
     * @param right
     */
    public Sum(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        checkRep();
    }

    private void checkRep() {
        assert left != null : "Left expression must be non-null";
        assert right != null : "Right expression must be non-null";
    }

    /**
     * @return a string representation of this sum.
     *         It is in the form of "(left.toString() + right.toString())".
     */
    @Override
    public String toString() {
        checkRep();
        return "(" + left.toString() + " + " + right.toString() + ")";
    }

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     *         Sums.
     */
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Sum)) {
            return false;
        }
        Sum that = (Sum) thatObject;
        return this.left.equals(that.left) && this.right.equals(that.right);
    }

    /**
     * @return hash code value consistent with the equals() definition of structural
     *         equality, such that for all s1,s2:Sum,
     *         s1.equals(s2) implies s1.hashCode() == s2.hashCode()
     */
    @Override
    public int hashCode() {
        return left.hashCode() * 31 + right.hashCode();
    }

    /**
     * Differentiate this sum with respect to a variable.
     * d/dx[left + right] = d/dx[left] + d/dx[right]
     * x is the variable to differentiate by
     * So the result is Sum(left.differentiate(x), right.differentiate(x))
     * Simpify:
     ** If either left or right is 0, drop it;
     ** If both left and right are numbers, fold them into a single number
     * 
     * @param variable the variable to differentiate by, a case-sensitive
     *                 nonempty string of letters.
     * @return Expression representing the derivative of this sum with respect to
     *         the given variable.
     */
    @Override
    public Expression differentiate(String variable) {
        return ((ExpressionWithOperation) left.differentiate(variable)).addToRight(right.differentiate(variable));
    }
}
