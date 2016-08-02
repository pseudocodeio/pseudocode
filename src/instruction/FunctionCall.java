package instruction;

import java.awt.Graphics;
import java.util.HashMap;

import expression.Expression;

public class FunctionCall extends Instruction {

	private String name;
	private HashMap <String, Expression> arguments;
	
	public FunctionCall(String name) {
		this.name = name;
	}
	
	public void addArgument(String parameter, Expression argument) {
		if (arguments == null)
			arguments = new HashMap <String, Expression> ();
		if(argument != null && parameter != null)
			arguments.put(parameter, argument);
	}
	
	@Override
	public void execute(Graphics graphics, Block block) {
		Block functionBlock = block.getDefinition(name);
		if (functionBlock != null) {
			if (arguments != null)
				functionBlock.setParameters(block, arguments);
			functionBlock.execute(graphics, block);
		}
	}

	@Override
	public String toString() {
		return "";
	}

	@Override
	public boolean equals(Instruction instruction, Block block) {
		return false;
	}

}
