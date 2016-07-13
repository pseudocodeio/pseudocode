package instruction;

import java.awt.Graphics;

/**
 * This abstract class represents a single instruction of Pseudocode. 
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
public abstract class Instruction {
	
	// Reference to the Block object that this instruction is a child of.
	protected Block parentBlock;
	
	/**
	 * Called by the Block object when this instruction is added to it.
	 * @param block - the Block that is adding this instruction
	 */
	public void setBlock(Block block) {
		this.parentBlock = block;
	}
	
	/**
	 * Implement this method to execute a Pseudocode instruction. 
	 * 
	 * @param graphics - the Graphics object of the OutputPanel object
	 * @param block - the Block object running this instruction
	 */
	public abstract void execute(Graphics graphics, Block block);
	
	/**
	 * Returns the String representation of this instruction.
	 */
	public abstract String toString();
	
	/**
	 * Returns true if this object is equivalent to another instruction executed within the context
	 * of the given block.
	 */
	public abstract boolean equals(Instruction instruction, Block block);
	
	/**
	 * Must implement the equals method to avoid repaints for functionally similar programs.
	 */
	public boolean equals(Object instruction) {
		if (instruction instanceof Instruction) {
			Instruction other = (Instruction) instruction;
			return this.equals(other, parentBlock);
		}
		else return false;
	}
	
	/**
	 * Can be overridden to prevent execution or conditionally allow execution. 
	 * @param block - the Block object running this instruction
	 * @return true if the execute method should be called, false otherwise
	 */
	public boolean shouldExecute(Block block) { 
		return true; 
	}
	
	/**
	 * Can be overridden to pause program execution of the parent block. If implementing
	 * a new loop, this must be overridden to return true, otherwise your loop will run
	 * at most one time during program execution. 
	 * @return true if this is a loop instruction, false otherwise
	 */
	public boolean shouldRepeat() { 
		return false; 
	}
}
