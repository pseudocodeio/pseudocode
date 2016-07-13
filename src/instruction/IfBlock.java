package instruction;

import java.awt.Graphics;

import expression.Expression;
import expression.Terminal;

/**
 * Represents an if statement and its corresponding block in a pseudocode program. 
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
public class IfBlock extends Instruction {
	
	Expression expression;	// The expression that controls whether this block executes
	Terminal evaluated; 	// Caches the last evaluated value for the expression
	Block block;			// The block of instructions
	
	/**
	 * Constructs an IfBlock object that conditionally performs the given block based on 
	 * the result of the given expression.
	 * 
	 * @param expression - the expression controlling the if
	 * @param block - the block to be performed if the expression evaluates to a positive number
	 */
	public IfBlock(Expression expression, Block block) {
		this.expression = expression;
		this.evaluated = new Terminal();
		this.block = block;
	}
	
	/**
	 * Assumes that the shouldExecute method returned true to the block parent of this instruction.
	 * Executes this instruction by first resetting the statement's block, then running every instruction.
	 */
	public void execute(Graphics graphics, Block rootBlock) {
		block.reset();
		while (! block.isComplete())
			block.execute(graphics, rootBlock);
	}
	
	/**
	 * Evaluates the branch instruction, and returns true if it evaluates to a positive non-zero number.
	 */
	public boolean shouldExecute(Block rootBlock) {
		evaluated.setValue(expression.evaluate(rootBlock));
		return evaluated.getValue() >= 1;
	}
	
	/**
	 * Returns true if in the last test of execution, this block executed.
	 */
	public boolean didExecute() {
		return evaluated.getValue() >= 1;
	}
	
	/**
	 * Returns the String representation of this block.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("if( ");
		sb.append(expression.toString());
		sb.append(" ) ");
		sb.append(block.toString());
		return sb.toString();
	}

	/**
	 * Returns true if this instruction object is equivalent to another in the parse tree.
	 */
	public boolean equals(Instruction instruction, Block block) {
		if (instruction instanceof IfBlock) {
			IfBlock other = (IfBlock) instruction;
			return other.expression.equals(this.expression) &&
				   other.block.equals(this.block);
		}
		return false;
	}
}
