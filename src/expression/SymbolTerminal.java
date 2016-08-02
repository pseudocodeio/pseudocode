package expression;

import instruction.Block;

/**
 * A Symbol is a type of Expression object that stores a symbolic representation of 
 * a value(s), i.e. a variable or list.
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
public class SymbolTerminal extends Terminal {
	// Stores the String representation of this symbol.
	private String symbol;
	private Expression index;
	
	/**
	 * Creates a Symbol object with the given name.
	 * @param symbol
	 */
	public SymbolTerminal(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Creates a Symbol object with the given name.
	 * @param symbol
	 * @param index
	 */
	public SymbolTerminal(String symbol, Expression index) {
		this.symbol = symbol;
		this.index = index;
	}
	
	/**
	 * Evaluates the value of this symbol in the context of the given block.
	 */
	public double evaluate(Block block) {
		// If a block object is given, return the value of this symbol in 
		// the block object's symbol table.
		if (block != null)
			return block.get(this,index);
		else return 0;
	}
	
	/**
	 * Returns the mathematical precedence of this symbol.
	 */
	public int getPrecedence() {
		return 0;
	}

	/**
	 * Returns the String representation of this symbol.
	 * @return
	 */
	public String toString() {
		return symbol;
	}
}
