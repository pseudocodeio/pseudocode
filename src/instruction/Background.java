package instruction;

import java.awt.Color;
import java.awt.Graphics;

import expression.RGB;

/**
 * Represents an instruction in an algorithm to set the background to a given color.
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
public class Background extends Instruction {

	Color color;
	
	public Background() {
		color = Color.WHITE;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void execute(Graphics graphics, Block block) {
		graphics.setColor(color);
		graphics.fillRect(0, 0, (int) block.get("width"), (int) block.get("height"));
	}
	
	/**
	 * Returns true if this object is equal to another instruction in the parse tree.
	 */
	public boolean equals(Instruction instruction, Block block) {
		if (instruction instanceof Background) {
			Background other = (Background) instruction;
			return other.color == this.color;
		}
		return false;
	}
	
	public String toString() {
		return "Window.out.background(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ");";
	}
}
