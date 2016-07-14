package expression;

import instruction.Block;

public class StringTerminal extends Terminal {
	private String value;
	
	public StringTerminal(String value) {
		// Save the value of this terminal
		this.value = value;
	}
	
	
	public String getStringValue() {
		return value;
	}
	
	public String evaluateString(Block block) {
		if (value.indexOf('$') < 0)
			return value;
		
		String interpolate = value;
		
		for (int i = 0 ; i < interpolate.length() ; i++) {
			if (interpolate.charAt(i) == '$') {
				StringBuilder var = new StringBuilder();
				while (i + 1 < interpolate.length() && Character.isLetter(interpolate.charAt(i + 1)) ) {
					var.append(interpolate.charAt(i + 1));
					i++;
				}
				
			}
		}
		
		return interpolate;
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
