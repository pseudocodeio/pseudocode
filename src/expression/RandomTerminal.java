package expression;

import instruction.Block;

public class RandomTerminal extends Terminal {
	
	Expression min;
	Expression max;
	
	public RandomTerminal() {
		min = new Terminal(0);
		max = new SymbolTerminal("width");
	}
	
	public RandomTerminal(Expression max) {
		min = new Terminal(0);
		this.max = max;
	}
	
	public RandomTerminal(Expression min, Expression max){
		this.min = min;
		this.max = max;
	}
	
	
	public double evaluate(Block block) {
		// Evaluate the min and max expressions
		double minValue = min.evaluate(block);
		double maxValue = max.evaluate(block);
		
		// Swap max and min if range given in reverse
		if (maxValue < minValue) {
			double temp = minValue;
			minValue = maxValue;
			maxValue = temp;
		}
		
		// Return the random value
		return minValue + Math.random() * (1 + maxValue - minValue);
	}
	
}
