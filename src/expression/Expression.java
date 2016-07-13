package expression;

import instruction.Block;

/**
 * An Expression object represents a mathematical expression.
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
public class Expression {
	private Expression left;		// Reference to the left side of the expression, or the single value
	private Operator operator;		// Reference to an optional operator to apply between the left and right values
	private Expression right;		// Reference to the right side of the expression
	
	/**
	 * Constructs an empty (default) expression.
	 */
	public Expression() {}
		
	/**
	 * Constructs a single value expression.
	 * @param left
	 */
	public Expression(Expression left) {
		this.left = left;
	}
	
	/**
	 * Constructs a unary expression.
	 * @param left - the single value for the unary expression
	 * @param operator - the unary operator
	 */
	public Expression(Expression left, Operator operator) {
		this.left = left;
		this.operator = operator;
	}
	
	/**
	 * Constructs a binary expression.
	 * @param left - left subexpression of the binary expression
	 * @param operator - the binary opeator
	 * @param right - the right subexpression of the binary expression
	 */
	public Expression(Expression left, Operator operator, Expression right) {
		this.left = left;
		this.operator = operator;
		this.right = right;
	}
	
	/**
	 * Returns the precedence of this expression.
	 * @return the precedence of the operator of this expression if there is one.
	 */
	public int getPrecedence() {
		if (operator == null) return 0;
		return getPrecedence(operator);
	}
	
	/**
	 * Evaluates this expression in the context of the given Block.
	 * @param block - the block evaluating this expression
	 * @return the numeric result from evaluating this expression
	 */
	public double evaluate(Block block) {
		if (left != null) {
			if (operator != null) {
				// Check for unary operator (only "not")
				if (operator != Operator.Not && right == null)
					return left.evaluate(block);
				
				switch(operator) {
				// Basic math operators
				case Add:			return left.evaluate(block) + right.evaluate(block);
				case Subtract: 		return left.evaluate(block) - right.evaluate(block);
				case Multiply: 		return left.evaluate(block) * right.evaluate(block);
				case Divide:		return left.evaluate(block) / right.evaluate(block);
				
				// Equality and inequality operators
				case Equal:			return (left.evaluate(block) == right.evaluate(block)) ? 1 : 0;
				case NotEqual: 		return (left.evaluate(block) != right.evaluate(block)) ? 1 : 0;
				
				// Comparison operators
				case GreaterThan:			return (left.evaluate(block) > right.evaluate(block)) ? 1 : 0;
				case GreaterThanOrEqual:	return (left.evaluate(block) >= right.evaluate(block)) ? 1 : 0;
				case LessThan:				return (left.evaluate(block) < right.evaluate(block)) ? 1 : 0;
				case LessThanOrEqual:		return (left.evaluate(block) <= right.evaluate(block)) ? 1 : 0;
				
				// Logic operators
				case And:		return (left.evaluate(block) == 1 && right.evaluate(block) == 1) ? 1 : 0;
				case Or: 		return (left.evaluate(block) == 1 || right.evaluate(block) == 1) ? 1 : 0;
				case Not: 		return (left.evaluate(block) == 0) ? 1 : 0;
				
				// Return 0 by default.
				default: return 0;
				}
			}
			// Terminal value
			else return left.evaluate(block);
		}
		return 0;
	}
	
	/**
	 * Returns the String representation of this expression.
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		if (left != null) {
			if (operator != null) {
				// Binary operator
				if (right != null) {
					builder.append(left.toString());
					builder.append(getString(operator));
					builder.append(right.toString());
				}
				// Unary operator
				else {
					builder.append(getString(operator));
					builder.append(left.toString());
				}
			}
			else builder.append(left.toString());
		}
		builder.append(")");
		return builder.toString();
	}
	
	/**
	 * Returns true if this expression is equivalent to another expression.
	 * @param other
	 * @return
	 */
	public boolean equals(Expression other) {
		// If there is an initial expression value.
		if (left != null) {
			// If there is an operator in this expression, check equivalence of the entire expression.
			if (operator != null)
				return left.equals(other.left) && 
					   operator == other.operator && 
					  ((right == null && other.right == null) || right.equals(other.right));
			
			// Otherwise check equivalence of the single sub-expression
			else return left.equals(other.left);
		}
		// Return false if this is a null expression
		return false;
	}
	
	/**
	 * Returns the string value of this terminal.
	 * @return
	 */
	public String getStringValue() {
		return toString();
	}
	
	/**
	 * Returns true if this expression has a string value instead of a numeric one.
	 */
	public boolean isString() {
		return false;
	}
	
	/**
	 * Static method for retrieving the precedence of a given operator.
	 * @param op - the operator
	 * @return precedence of the operator
	 */
	public static int getPrecedence(Operator op) {
		switch (op) {
		case And: 		
		case Or:
		case Not: 				return 1;
		case Equal:
		case NotEqual:			return 2;
		case GreaterThan:
		case GreaterThanOrEqual:
		case LessThan:
		case LessThanOrEqual:	return 3;
		case Add: 
		case Subtract:		 	return 4;
		case Multiply:
		case Divide:			return 5;
		default:				return 0;
		}
	}
	
	/**
	 * Returns the String representation of the operator.
	 * @param operator - the operator
	 * @return the String representation of the operator
	 */
	public static String getString(Operator operator) {
		switch (operator) {
		case And: 				return "&&";
		case Or:				return "||";
		case Not: 				return "!";
		case Equal:				return "==";
		case NotEqual:			return "!=";
		case GreaterThan:		return ">";
		case GreaterThanOrEqual:return ">=";
		case LessThan:			return "<";
		case LessThanOrEqual:	return "<=";
		case Add: 				return "+";
		case Subtract:		 	return "-";
		case Multiply:			return "*";
		case Divide:			return "/";
		default:				return "";
		}
	}
}
