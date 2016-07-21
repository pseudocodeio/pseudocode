package instruction;

import java.awt.Graphics;

import expression.Expression;

public class Wait extends Instruction{
	Expression time;
	
	/**
	 * Constructs a wait instruction for the given time expression
	 * @param time 
	 */
	public Wait(Expression time){
		this.time = time;
	}
	
	
	public void execute(Graphics graphics, Block algorithm) {
		long time = (long) ((this.time != null) ? this.time.evaluate(algorithm) : 0);
		
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public boolean equals(Instruction instruction, Block block) {
		return false;
	}

}
