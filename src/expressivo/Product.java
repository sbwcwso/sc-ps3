package expressivo;

/**
 * An immutable product expression of two Expressions.
 */
public class Product implements Expression {
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

}
