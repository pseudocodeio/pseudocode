package instruction;

import java.awt.Graphics;

import expression.Expression;

/**
 * Represents an else if statement and its corresponding block in a pseudocode program. 
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
public class ElseIfBlock extends IfBlock {
	
	/**
	 * An else if block performs the given block if its expression evaluates to true and no 
	 * previous if block evaluated to true.
	 * @param expression
	 * @param block
	 */
	public ElseIfBlock(Expression expression, Block block) {
		super(expression, block);
	}
	
	/**
	 * Evaluates the branch instruction, and returns true if it evaluates to a positive non-zero number.
	 */
	public boolean shouldExecute(Block block) {
		Instruction previous = parentBlock.previousInstruction();
		if (previous != null && previous instanceof IfBlock) {
			IfBlock parentIf = (IfBlock) previous;
			if (! parentIf.didExecute()) {
				evaluated.setValue(expression.evaluate(block));
				return evaluated.getValue() > 0;
			}
			else evaluated.setValue(0);
		}
		return false;
	}
	
	/**
	 * Returns the String representation of this block.
	 */
	public String toString() {
		return "else " + super.toString();
	}
}
