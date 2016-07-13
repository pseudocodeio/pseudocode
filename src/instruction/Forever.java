package instruction;

import java.awt.Graphics;

/**
 * Represents an infinite loop in a pseudocode program. 
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
public class Forever extends Instruction {
	
	// Reference to the block of instructions being repeated forever.
	private Block block;
	
	/**
	 * Constructs an infinite loop that repeats the given block of instructions.
	 * @param block
	 */
	public Forever(Block block) {
		this.block = block;
	}
	
	/**
	 * Executes this infinite loop by performing the entire block being repeated.
	 */
	public void execute(Graphics graphics, Block algorithm) {
		block.reset();
		while (! block.isComplete())
			block.execute(graphics, algorithm);
	}
	
	/**
	 * Returns true if this block should execute (always true).
	 * @return true always
	 */
	public boolean shouldExecute() {
		return true;
	}
	
	/**
	 * Returns true so this block repeats indefinitely.
	 * @return true always
	 */
	public boolean shouldRepeat() {
		return true;
	}
	
	/**
	 * Returns the String representation of this infinite loop.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("while(true) ");
		sb.append(block.toString());
		return sb.toString();
	}
	
	/**
	 * Returns true if this instruction object is equivalent to another in the parse tree.
	 */
	public boolean equals(Instruction instruction, Block block) {
		if (instruction instanceof IfBlock) {
			Forever other = (Forever) instruction;
			return other.block.equals(this.block);
		}
		return false;
	}
}
