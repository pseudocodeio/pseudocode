package parser;

import java.util.ArrayList;

public class Lexer {

	ArrayList<String> tokens;
	StringBuilder token;

	private static final int WORD = 1;
	private static final int NUMBER = 2;
	private static final int STRING = 3;
	private static final int COMMENT = 4;

	// For number parsing.
	boolean hasDecimal = false;
	boolean hasQuote = false;
	boolean hasEscape = false;

	/**
	 * Creates the lexer.
	 */
	public Lexer() {
		tokens = new ArrayList<String>();
		token = new StringBuilder();
	}

	public String[] lex(String text) {
		return lex(text, false, true);
	}

	/**
	 * 
	 * @param text
	 *            - the raw text
	 * @param all
	 *            - whether to include all tokens, including delimiters
	 * @return
	 */
	public String[] lex(String text, boolean all, boolean includeComments) {
		// Reset the lexer token stream and state.
		tokens.clear();
		int state = 0;
		boolean comment = false;

		// Iterate to each character in the input string.
		for (int index = 0; index < text.length(); index++) {
			char c = text.charAt(index);

			// If the line is a comment ignore the characters until the next next line
			if (!comment) {
				// Space characters terminate current token.
				if (state == STRING) {
					// If the escape flag was activated by an escape character
					if (hasEscape) {
						token.append(c);
						hasEscape = false;
					}
					// The escape character will escape the next character
					else if (c == '\\')
						hasEscape = true;

					// An unescaped quotation mark or new line will end the
					// string token
					else if (c == '"') {
						token.append(c);
						pushToken();
						state = 0;
					}
					// A new line character will also end the string and add a
					// quotation mark that
					// wasn't already there.
					else if (c == '\n') {
						token.append('"');
						pushToken();
						pushToken(c);
						state = 0;
					}
					// Otherwise append the charater
					else
						token.append(c);
				}
				// Comments ignore the whole line
				else if (state == COMMENT) {
					// If there is a second / then the line is a comment, if not
					// then include the forward slash in the lex
					if (c == '/') {
						comment = true;
					} else {
						state = 0;
						pushToken();
						pushToken('/');
						index -= 1;
					}
				} else if (c == ' ') {
					pushToken();
					if (all)
						pushToken(' ');
				}
				// Alphabetic characters are appended to the token if in the
				// WORD state.
				else if (state == WORD && Character.isAlphabetic(c)) {
					token.append(c);
				}
				// One single quote can be added to the word.
				else if (state == WORD && !hasQuote && c == '\'') {
					token.append(c);
					hasQuote = true;
				}
				// Numeric characters are appending to the number if in the
				// NUMBER state.
				else if (state == NUMBER && Character.isDigit(c)) {
					token.append(c);
				}
				// Numbers can have one decimal place character.
				else if (state == NUMBER && !hasDecimal && c == '.') {
					token.append(c);
					hasDecimal = true;
				}
				// If there is a double character operator in the input stream,
				// i.e <= or >=
				else if (index + 1 < text.length() && isDouble(c, text.charAt(index + 1))) {
					pushToken();
					pushToken("" + c + text.charAt(index + 1));
				}
				// If this is a single character operator.
				else if (isSingle(c)) {
					// If the character is a / then initialize a comment
					if (c == '/' && includeComments) {
						state = COMMENT;
					} else {
						pushToken();
						pushToken(c);
					}
				}
				// Otherwise determine a new state for the lexer.
				else {
					// If this is the start of an alphabetic character
					if (Character.isAlphabetic(c)) {
						token.append(c);
						state = WORD;
					}
					// If this is the start of a number.
					else if (Character.isDigit(c)) {
						token.append(c);
						state = NUMBER;
					}
					// If this is a beginning quotation mark for a string
					else if (c == '"') {
						token.append(c);
						state = STRING;
					} else if (all) {
						pushToken(c);
					}
				}

			}
			else{
				if(c == '\n')
					comment = false;
			}
		}
		pushToken();

		return tokens.toArray(new String[tokens.size()]);
	}

	private boolean isSingle(char c) {
		return c == '\n' || c == '\t' || c == '/' || c == ',' || c == '.' || isOperator(c);
	}

	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '<' || c == '>';
	}

	private boolean isDouble(char c, char d) {
		return (c == '>' || c == '<') && d == '=';
	}

	private void pushToken(char token) {
		tokens.add(("" + token).toLowerCase());
	}

	private void pushToken(String token) {
		tokens.add(token.toLowerCase());
	}

	/**
	 * Push the current token.
	 */
	private void pushToken() {
		if (token.length() > 0) {
			tokens.add(token.toString().toLowerCase());
			hasDecimal = false;
			hasQuote = false;
			clearToken();
		}
	}

	/**
	 * Clears the current token in the lexer.
	 */
	private void clearToken() {
		token.delete(0, token.length());
		hasDecimal = false;
	}
}
