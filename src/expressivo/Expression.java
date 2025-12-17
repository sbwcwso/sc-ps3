/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionParser;

/**
 * An immutable data type representing a polynomial expression of:
 * + and *
 * nonnegative integers and floating-point numbers
 * variables (case-sensitive nonempty strings of letters)
 * 
 * <p>
 * PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of
 * existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing
 * methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {

    // Datatype definition
    // Expression = Number(n: double) + Variable(name: string) + Sum(left:
    // Expression, right: Expression) + Product(left: Expression, right: Expression)

    /**
     * Parse an expression.
     * 
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        return ExpressionParserHelper.parse(input);
    }

    /**
     * Differentiate this expression with respect to a variable.
     * 
     * @param variable the variable to differentiate by, a case-sensitive
     *                 nonempty string of letters.
     * @return expression's derivative with respect to variable. Must be a valid
     *         expression equal to the derivative, but doesn't need to be in
     *         simplest or canonical form.
     * @throws IllegalArgumentException if the variable is invalid
     */
    public Expression differentiate(String variable);

    /**
     * Simplify this expression with respect to the given environment.
     * 
     * @param environment maps variables to values. Variables are required to be
     *                    case-sensitive nonempty strings of letters. The set of
     *                    variables in environment is allowed to be different than
     *                    the set of variables actually found in this expression.
     *                    Values must be nonnegative numbers.
     * @return
     */
    public Expression simplify(Map<String, Double> environment);

    /**
     * @return a parsable representation of this expression, such that
     *         for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override
    public String toString();

    /**
     * Structural equality defines two expressions to be equal if:
     * 1. the expressions contain the same variables, numbers, and operators;
     * 2. those variables, numbers, and operators are in the same order, read
     * left-to-right;
     * 3. and they are grouped in the same way.
     * 
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     *         Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);

    /**
     * @return hash code value consistent with the equals() definition of structural
     *         equality, such that for all e1,e2:Expression,
     *         e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();

}

interface ExpressionWithOperation extends Expression {
    /**
     * Add this expression to the right expression.
     * 
     * @param right the right expression
     * @return a new Sum expression object
     */
    default Expression addToRight(Expression right) {
        // Delegate to right operand to allow symmetric simplifications
        return ((ExpressionWithOperation) right).addToLeft(this);
    }

    /**
     * Helper for double dispatch: add a Number to this expression.
     * This method is called when the left operand of the addition is known to be a
     * Number.
     *
     * @param leftNumber the number on the left side of the addition
     * @return the sum of left and this
     */
    default Expression addWithLeftNumber(Number leftNumber) {
        return new Sum(leftNumber, this);
    }

    /**
     * Internal hook for symmetric addition: handle (left + this).
     * Default behavior constructs a Sum without simplification.
     * 
     * @param left the left expression
     * @return the sum of left and this
     */
    default Expression addToLeft(Expression left) {
        return new Sum(left, this);
    }

    /**
     * Helper for double dispatch: add this expression to a Number on the right.
     * This method is called when the right operand of the addition is known to be a
     * Number.
     * 
     * @param rightNumber the number on the right side of the addition
     * @return the sum of this and rightNumber
     */
    default Expression addWithRightNumber(Number rightNumber) {
        return new Sum(this, rightNumber);
    }

    /**
     * Multiply this expression to the right expression.
     * 
     * @param right the right expression
     * @return a new Product expression object
     */
    default Expression mulRight(Expression right) {
        // Delegate to right operand to allow symmetric simplifications
        return ((ExpressionWithOperation) right).mulLeft(this);
    }

    /**
     * Internal hook for symmetric multiplication: handle (left * this).
     * Default behavior constructs a Product without simplification.
     * 
     * @param left the left expression
     * @return the product of left and this
     */
    default Expression mulLeft(Expression left) {
        return new Product(left, this);
    }

    /**
     * Helper for double dispatch: multiply a Number to this expression.
     * This method is called when the left operand of the multiplication is known to
     * be a Number.
     *
     * @param leftNumber the number on the left side of the multiplication
     * @return the product of left and this
     */
    default Expression mulLeftNumber(Number leftNumber) {
        return new Product(leftNumber, this);
    }

    /**
     * Helper for double dispatch: multiply this expression to a Number on the
     * right.
     * This method is called when the right operand of the multiplication is known
     * to be a Number.
     * 
     * @param rightNumber the number on the right side of the multiplication
     * @return the product of this and rightNumber
     */
    default Expression mulRightNumber(Number rightNumber) {
        return new Product(this, rightNumber);
    }

}

class ExpressionParserHelper {
    // helper that parses input using the generated ANTLR parser and builds AST
    public static Expression parse(String input) {
        try {
            // ANTLR 4.5.1: use ANTLRInputStream
            ANTLRInputStream ais = new ANTLRInputStream(input);
            ExpressionLexer lexer = new ExpressionLexer(ais);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ExpressionParser parser = new ExpressionParser(tokens);

            // make parser throw exceptions on errors
            parser.reportErrorsAsExceptions();
            lexer.removeErrorListeners();
            lexer.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                        int line, int charPositionInLine, String msg, RecognitionException e) {
                    throw new ParseCancellationException(msg, e);
                }
            });

            ParseTree tree = parser.root();
            AstBuilder listener = new AstBuilder();
            ParseTreeWalker.DEFAULT.walk(listener, tree);
            Expression result = listener.getResult();
            return result;
        } catch (ParseCancellationException e) {
            throw new IllegalArgumentException("Invalid expression: " + e.getMessage(), e);
        }
    }

    // AST builder implemented here to avoid touching parser/ package files.
    private static class AstBuilder extends expressivo.parser.ExpressionBaseListener {
        private java.util.Deque<Expression> stack = new java.util.ArrayDeque<>();

        public Expression getResult() {
            if (stack.isEmpty()) {
                throw new IllegalArgumentException("No expression parsed");
            }
            return stack.peek();
        }

        @Override
        public void enterRoot(expressivo.parser.ExpressionParser.RootContext ctx) {
        }

        @Override
        public void exitRoot(expressivo.parser.ExpressionParser.RootContext ctx) {
        }

        @Override
        public void enterSum(expressivo.parser.ExpressionParser.SumContext ctx) {
        }

        @Override
        public void exitSum(expressivo.parser.ExpressionParser.SumContext ctx) {
            int n = ctx.product().size();
            if (n == 0)
                return;
            Expression[] elms = new Expression[n];
            for (int i = n - 1; i >= 0; i--) {
                elms[i] = stack.pop();
            }
            Expression left = elms[0];
            for (int i = 1; i < n; i++) {
                left = new Sum(left, elms[i]);
            }
            stack.push(left);
        }

        @Override
        public void enterProduct(expressivo.parser.ExpressionParser.ProductContext ctx) {
        }

        @Override
        public void exitProduct(expressivo.parser.ExpressionParser.ProductContext ctx) {
            int n = ctx.primitive().size();
            if (n == 0)
                return;
            Expression[] elms = new Expression[n];
            for (int i = n - 1; i >= 0; i--) {
                elms[i] = stack.pop();
            }
            Expression left = elms[0];
            for (int i = 1; i < n; i++) {
                left = new Product(left, elms[i]);
            }
            stack.push(left);
        }

        @Override
        public void enterPrimitive(expressivo.parser.ExpressionParser.PrimitiveContext ctx) {
        }

        @Override
        public void exitPrimitive(expressivo.parser.ExpressionParser.PrimitiveContext ctx) {
            if (ctx.NUMBER() != null) {
                String text = ctx.NUMBER().getText();
                double value = Double.parseDouble(text);
                stack.push(new Number(value));
            } else if (ctx.sum() != null) {
                // sum has already pushed its result on stack
            } else if (ctx.VARIABLE() != null) {
                // VARIABLE is a token in the grammar: VARIABLE : [a-zA-Z]+ ;
                String txt = ctx.VARIABLE().getText();
                stack.push(new Variable(txt));
            } else {
                // This branch should be unreachable given the grammar
                throw new IllegalStateException("Unexpected primitive: " + ctx.getText());
            }
        }

        @Override
        public void enterConfiguration(expressivo.parser.ExpressionParser.ConfigurationContext ctx) {
        }

        @Override
        public void exitConfiguration(expressivo.parser.ExpressionParser.ConfigurationContext ctx) {
        }
    }
}
