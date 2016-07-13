package instruction;

import java.awt.Graphics;

import expression.Expression;
import expression.Terminal;

public class ElseBlock extends ElseIfBlock {
	
	Block block;

	/**
	 * Constructs an else block that performs the given block if the previous IfBlock instruction
	 * or ElseIf block instruction evaluated to false.
	 * @param block
	 */
	public ElseBlock(Block block) {
		super(new Terminal(1), block);
	}
	
	/**
	 * Returns the String representation of this block.
	 */
	public String toString() {
		if (block == null)
			return "else {}";
		return "else " + block.toString();
	}
}
