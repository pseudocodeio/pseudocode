package instruction;

import java.awt.Graphics;

public class FunctionDefinition extends Instruction {
	
	String symbol;
	Block block;
	
	public FunctionDefinition(String symbol, Block block) {
		this.symbol = symbol;
		this.block = block;
	}

	@Override
	public void execute(Graphics graphics, Block rootBlock) {
		rootBlock.define(symbol, block);
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
