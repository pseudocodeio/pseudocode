package instruction;

import java.awt.Graphics;

import expression.Expression;
import expression.StringTerminal;

/**
 * 
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 *
 * @license MIT
 */
public class Print extends Instruction {
	
	// The expression to be printed
	Expression expression;
	
	/**
	 * Constructs a print statement with the given expression to be printed.
	 * @param expression - the expression that will be printed when this instruction is executed
	 */
	public Print(Expression expression) {
		this.expression = expression;
	}
	
	/**
	 * When this instruction is executed, print the expression to standard output.
	 */
	public void execute(Graphics graphics, Block algorithm) {
		// Print the string value of a StringTerminal expression
		if (expression instanceof StringTerminal)
			algorithm.print(evaluateString(((StringTerminal) expression).getStringValue(), algorithm));
		
		// Otherwise print the evaluated value of the expression.
		else 
			algorithm.print(evaluateString("" + expression.evaluate(algorithm), algorithm));
	}
	
	/**
	 * Returns the print String with all interpolated variables as their values.
	 */
	public String evaluateString(String text, Block algorithm){
		String words[] = text.split(" ");
		String print = "";
		for(String i: words){
			int index = -1;
			if(i.startsWith("#")){
				i = i.substring(1);
				if(i.contains(":")){
					String[] split = i.split(":");
					if(split.length == 2){
						i = split[0];
						try{
							index = Integer.parseInt(split[1]);
						}
						catch (NumberFormatException e){
							index = 0;
						}
						
					}
					
				}
				print += algorithm.printVal(i, index) + " ";
			}
			else
				print += i;
		}

		return print;
		
	}
	
	/**
	 * Returns the String value of this print instruction.
	 */
	public String toString() {
		return "System.out.println( " + expression.toString() + " );";
	}

	/**
	 * Returns true if this instruction is functionally equivalent to another.
	 */
	public boolean equals(Instruction instruction, Block block) {
		if (instruction instanceof Print) {
			Print other = (Print) instruction;
			return other.expression.equals(this.expression);
		}
		return false;
	}
}
