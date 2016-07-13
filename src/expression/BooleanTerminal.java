package expression;

public class BooleanTerminal {
	
	private boolean value = false;
	
	public BooleanTerminal(boolean value) {
		this.value = value;
	}
	
	public int getValue() {
		return (value) ? 1 : 0;
	}
}
