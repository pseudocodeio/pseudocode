package instruction;

import java.awt.Graphics;
import java.util.ArrayList;

import expression.Expression;
import expression.SymbolTerminal;

/**
 * The instruction for assigning the result of an expression to a variable.
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
public class Assign extends Instruction {
	
	SymbolTerminal symbol;
	ArrayList<Expression> expression;
	Expression value;
	Expression index;
	
	/**
	 * An Assign object must have a symbol and expression object.
	 * @param symbol - the Symbol object being assigned to
	 * @param values - the Expression object
	 */
	public Assign(SymbolTerminal symbol, ArrayList<Expression> values) {
		this.symbol = symbol;
		this.expression = values;
	}
	
	public Assign(SymbolTerminal symbol, Expression expression) {
		this.expression.add(expression);
	}

	public Assign(SymbolTerminal symbol, Expression expression, Expression index) {
		this.symbol = symbol;
		this.value = expression;
		this.index = index;
	}

	/**
	 * Calls the Block object's assign method with the given symbol and expression object.
	 */
	public void execute(Graphics graphics, Block block) {
//		if(index !=null)
//			block.assign(symbol, value, index);
		//else
			block.assign(symbol, expression);
	}
	
	/**
	 * Returns the String representation of the assignment.
	 */
	public String toString() {
		return symbol.toString() + " = " + expression.toString() + ";";
	}
	
	/**
	 * Returns true if this object is equal to another instruction in the parse tree.
	 */
	public boolean equals(Instruction instruction, Block block) {
		if (instruction instanceof Assign) {
			Assign other = (Assign) instruction;
			return other.symbol.equals(symbol) && other.expression.equals(expression);
		}
		return false;
	}
}
