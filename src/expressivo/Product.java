package expressivo;

/**
 * An immutable product expression of two Expressions.
 */
public class Product implements ExpressionWithOperation {
    private final Expression left;
    private final Expression right;

    // Abstraction function:
    // AF(left, right) = a product expression representing the product of 'left' and
    // 'right'
    // Representation invariant:
    // left and right are non-null Expressions
    // left and right are all immutable Expressions
    // Safety from rep exposure:
    // all fields are private and final, and of immutable types

    /**
     * Create a Product expression
     * 
     * @param left
     * @param right
     */
    public Product(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        checkRep();
    }

    private void checkRep() {
        assert left != null : "Left expression must be non-null";
        assert right != null : "Right expression must be non-null";
    }

    /**
     * @return a string representation of this product.
     *         It is in the form of "(left.toString() * right.toString())".
     */
    @Override
    public String toString() {
        checkRep();
        return "(" + left.toString() + " * " + right.toString() + ")";
    }

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     *         Products.
     */
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Product)) {
            return false;
        }
        Product that = (Product) thatObject;
        return this.left.equals(that.left) && this.right.equals(that.right);
    }

    /**
     * @return hash code value consistent with the equals() definition of structural
     *         equality, such that for all p1,p2:Product,
     *         p1.equals(p2) implies p1.hashCode() == p2.hashCode()
     */
    @Override
    public int hashCode() {
        return 31 * left.hashCode() + right.hashCode();
    }

    /**
     * Differentiate this product with respect to a variable.
     * d/dx[left * right] = d/dx[left] * right + left * d/dx[right]
     * x is the variable to differentiate by
     * So the result is Sum(Product(left.differentiate(x), right), Product(left,
     * right.differentiate(x)))
     * Simplify:
     ** If left.differentiate(x) is 0, then the first part is 0, drop it;
     ** If right.differentiate(x) is 0, then the second part is 0, drop it;
     ** If both parts are 0, return Number(0);
     ** If either part is Number(1), drop it from the product.
     ** The Sum of the two parts also need to be simplify, like in
     * Sum.differentiate().
     * 
     * @param variable the variable to differentiate by, a case-sensitive
     *                 nonempty string of letters.
     * @return expression's derivative of this product with respect to variable.
     */
    @Override
    public Expression differentiate(String variable) {
        Expression leftDerivative = left.differentiate(variable);
        Expression rightDerivative = right.differentiate(variable);
        Expression leftPart = ((ExpressionWithOperation) leftDerivative).mulRight(right);
        Expression rightPart = ((ExpressionWithOperation) left).mulRight(rightDerivative);
        return ((ExpressionWithOperation) leftPart).addToRight(rightPart);
    }

    /**
     * Simplify this product with respect to an environment.
     * 
     * @param environment maps variables to values. Variables are required to be
     *                    case-sensitive nonempty strings of letters. The set of
     *                    variables in environment is allowed to be different than
     *                    the set of variables actually found in expression. Values
     *                    must be nonnegative numbers.
     * @return the simplified expression after simplifying left and right with
     *         respect to the environment. The simplified rule:
     *         If either left or right is 0, return Number(0);
     *         If either left or right is 1, drop it;
     *         If both left and right are numbers, fold them into a single number
     */
    public Expression simplify(java.util.Map<String, Double> environment) {
        Expression simplifiedLeft = left.simplify(environment);
        Expression simplifiedRight = right.simplify(environment);
        return ((ExpressionWithOperation) simplifiedLeft).mulRight(simplifiedRight);
    }
}
