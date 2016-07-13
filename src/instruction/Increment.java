package instruction;

import java.awt.Graphics;

import expression.Expression;
import expression.Operator;
import expression.SymbolTerminal;
import expression.Terminal;

/**
 * Represents an instruction that increments/decrements a variable by the numeric
 * result of a certain expression.
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
public class Increment extends Instruction {

	// The variable being incremented/decremented
	SymbolTerminal symbol;
	// The change in the variable (default is 1)
	Expression change = new Terminal(1);
	
	/**
	 * Constructs an increment instruction for the given symbol.
	 * @param symbol
	 */
	public Increment(SymbolTerminal symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Increments the given symbol by the value of the given expression.
	 * @param symbol - the symbol to increment
	 * @param change - the expression describing how the symbol should change
	 */
	public Increment(SymbolTerminal symbol, Expression change) {
		this.symbol = symbol;
		this.change = change;
	}
	
	/**
	 * Returns the String representation of this instruction.
	 */
	public String toString() {
		return symbol.toString() + " += " + change.toString() + ";";
	}

	/**
	 * Executes this increment instruction.
	 */
	public void execute(Graphics graphics, Block block) {
		block.assign(symbol, new Expression(symbol, Operator.Add, change));
	}
	
	/**
	 * Returns true if this instruction object is equivalent to another in the parse tree.
	 */
	public boolean equals(Instruction instruction, Block block) {
		if (instruction instanceof Increment) {
			Increment other = (Increment) instruction;
			return other.symbol.equals(this.symbol) &&
				   other.change.equals(this.change);
		}
		return false;
	}
}
