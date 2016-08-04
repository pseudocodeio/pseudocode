package instruction;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

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
	String word;
	Block.Variable type;
	
	/**
	 * An Assign object must have a symbol and expression object.
	 * @param symbol - the Symbol object being assigned to
	 * @param values - the Expression object
	 */
	public Assign(SymbolTerminal symbol, ArrayList<Expression> values) {
		this.symbol = symbol;
		this.expression = values;
		type = Block.Variable.List;
	}
	
	public Assign(SymbolTerminal symbol, Expression expression) {
		this.symbol = symbol;
		this.expression.add(expression);
		type = Block.Variable.List;
	}

	public Assign(SymbolTerminal symbol, Expression expression, Expression index) {
		this.symbol = symbol;
		this.value = expression;
		this.index = index;
		type = Block.Variable.Number;
	}
	
	public Assign(SymbolTerminal symbol, String word){
		this.symbol = symbol;
		this.word = word;
		type = Block.Variable.String;
	}

	/**
	 * Calls the Block object's assign method with the given symbol and expression object.
	 */
	public void execute(Graphics graphics, Block block) {
		HashMap<String, HashMap<String, Double>> scope = block.currentScope();
		switch(type){
		case List:
				block.assign(symbol, expression, type);
			break;
		case Number:
			int i = -1;
			if(index != null){
				i = (int) index.evaluate(block);
				if(i > scope.get(symbol).get("length") || i < 0){
					i = 0;
				}
			}
			block.assign(symbol, value, index, type);
			break;
		case String:
			block.assign(symbol, word, type);
			break;
		}
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
