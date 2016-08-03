package instruction;

import java.awt.Graphics;

import expression.Expression;
import expression.Terminal;

/**
 * Repeat statement - for loop
 */
public class RepeatBlock extends Instruction {
	
	Expression expression;	// How many time to loop
	double evaluated; 		// Caches the last evaluated value for the expression
	Block block;			// The block of instructions
	int repeated;			// Times repeated
	
	/**
	 * Constructs a RepeatBlock object that performs the given block a number of times based on 
	 * the expression.
	 * 
	 * @param expression - the number of times to loop
	 * @param block - the block to be performed in the loop
	 */
	public RepeatBlock(Expression expression, Block block) {
		this.expression = expression;
		this.evaluated = expression.evaluate(block);
		this.block = block;
		repeated = 0;
	}
	
	/**
	 * Assumes that the shouldExecute method returned true to the block parent of this instruction.
	 * Executes this instruction by first resetting the statement's block, then running every instruction.
	 */
	public void execute(Graphics graphics, Block rootBlock) {
		repeated++;
		block.reset();
		while (! block.isComplete())
			block.execute(graphics, rootBlock);
	}
	
	public boolean shouldExecute() {
		return true;
	}
	
	/**
	 * Checks how many times it repeated.
	 */
	public boolean shouldRepeat() {
		return evaluated>repeated;
	}
	
	/**
	 * Returns the String representation of this block.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("for( int i = 0; i < ");
		sb.append(expression.toString());
		sb.append("; i++ ) ");
		sb.append(block.toString());
		return sb.toString();
	}

	/**
	 * Returns true if this instruction object is equivalent to another in the parse tree.
	 */
	public boolean equals(Instruction instruction, Block block) {
		if (instruction instanceof RepeatBlock) {
			RepeatBlock other = (RepeatBlock) instruction;
			return other.expression.equals(this.expression) &&
				   other.block.equals(this.block);
		}
		return false;
	}
}
