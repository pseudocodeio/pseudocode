package expression;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Stores references to constant values that are referred to by the parser, certain
 * expressions, the lexer, and the instructions.
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
@SuppressWarnings("serial")
public class Constant {
	
	// Size constants
	public static final Terminal BIG = new Terminal(50);
	public static final Terminal MEDIUM = new Terminal(20);
	public static final Terminal SMALL = new Terminal(10);
	
	// A set of reserved keywords
	public static HashSet <String> keyword = new HashSet <String> () {{
		add("draw");
		add("set");
		add("set background");
		add("increment");
		add("decrement");
		add("increase");
		add("decrease");
		add("change");
		add("invert");
		add("if");
		add("forever");
		add("else if");
		add("otherwise if");
		add("else");
		add("otherwise");
		add("print");
	}};
	
	public static HashSet <String> property = new HashSet <String> () {{
		add("width");
		add("height");
		add("size");
		add("radius");
	}};
	
	public static HashSet <String> operator = new HashSet <String> () {{
		add("+");
		add("-");
		add("*");
		add("/");
		add("and");
		add("or");
		add("not");
		add("<");
		add(">");
		add("<=");
		add(">=");
		add("==");
		add("is");
		add("!=");
	}};
}
