package expression;

import instruction.Block;

public class DistanceTerminal extends Terminal {
	private Expression x1;
	private Expression y1;
	private Expression x2;
	private Expression y2;
	
	public DistanceTerminal(Expression x1, Expression y1, Expression x2, Expression y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public double evaluate(Block block) {
		return Math.sqrt(
					Math.pow(x1.evaluate(block) - x2.evaluate(block), 2) + 
					Math.pow(y1.evaluate(block) - y2.evaluate(block), 2));
	}
}
