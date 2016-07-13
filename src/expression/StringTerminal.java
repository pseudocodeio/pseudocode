package expression;

public class StringTerminal extends Terminal {
	private String value;
	
	public StringTerminal(String value) {
		// Save the value of this terminal
		this.value = value;
	}
	
	
	public String getStringValue() {
		return value;
	}
	
	/**
	 * Returns the string representation of this value.
	 */
	public String toString() {
		return "\"" + value + "\"";
	}
	
	/**
	 * Overrides the default behavior of returning false if this is a string.
	 */
	public boolean isString() {
		return true;
	}
}
