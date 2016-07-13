package expression;

import instruction.Block;

/**
 * A Terminal object represents a numeric value.
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
public class Terminal extends Expression {
	
	// The underlying value of this terminal expression
	private double value = 0;
	
	/**
	 * Constructs a terminal object with the default value of 0.
	 */
	public Terminal() {
		this(0);
	}
	
	/**
	 * Constructs a terminal object with the given value.
	 * @param value - the numeric value of this terminal object.
	 */
	public Terminal(double value) {
		this.value = value;
	}
	
	/**
	 * Sets the value of this terminal.
	 * @param value - the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
	
	/**
	 * Returns the underlying value of this terminal.
	 * @return
	 */
	public double getValue() {
		return this.value;
	}
	
	/**
	 * Get the value of this terminal as a string.
	 */
	public String getStringValue() {
		return "" + this.value;
	}
	
	/**
	 * Overridden by a subtype that is a string.
	 * @return false by default, true by StringTerminals 
	 */
	public boolean isString() {
		return false;
	}
	
	/**
	 * Gets the evaluation precedence of this terminal.
	 */
	public int getPrecedence() {
		return 0;
	}
	
	/**
	 * Evaluates in the context of a given block. Since this is a numeric value, it always
	 * evaluates to its double value.
	 */
	public double evaluate(Block block) {
		return value;
	}
	
	/**
	 * Returns the String representation of this terminal value.
	 */
	public String toString() {
		return "" + value;
	}
	
	/**
	 * Returns true if this terminal is equivalent to another Terminal object.
	 */
	public boolean equals(Object terminal) {
		if (terminal instanceof Terminal) {
			Terminal other = (Terminal) terminal;
			return value == other.value;
		}
		return false;
	}
}
