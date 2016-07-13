package test;

import static org.junit.Assert.*;

import java.io.PrintStream;

import org.junit.Test;

import expression.Expression;
import expression.Terminal;
import parser.Lexer;

public class TestLexer {

	static Lexer lexer = new Lexer();
	
	@Test
	public void testSimpleLex() {
		assertTrue(test("draw", "draw"));
		assertTrue(test("draw ", "draw"));
		assertTrue(test("draw a", "draw", "a"));
		assertTrue(test("draw a ", "draw", "a"));
		assertTrue(test("draw a circle at", "draw", "a", "circle", "at"));
	}
	
	@Test
	public void testExpressionLex() {
		assertTrue(test("1", "1"));
		assertTrue(test("-5 ", "-", "5"));
		assertTrue(test("1-5", "1", "-", "5"));
		assertTrue(test("1 + 2 * 3-4", "1", "+", "2", "*", "3", "-", "4"));
		assertTrue(test("draw a circle at 100, 200", "draw", "a", "circle", "at", "100", ",", "200"));
		//assertTrue(test("draw a circle at", "draw", "a", "circle", "at"));
	}
	
	@Test
	public void testBranchLex() {
		assertTrue(test("if x < 1\n\tprint x", "if", "x", "<", "1", "\n", "\t", "print", "x"));
		//assertTrue(test("draw a circle at", "draw", "a", "circle", "at"));
	}
	
	@Test
	public void testStringLex() {
		assertTrue(test("\"foo\"", "\"foo\""));
		assertTrue(test("\"foo\" + \"bar\"", "\"foo\"", "+", "\"bar\""));
		assertTrue(test("print \"hello\" at 300, 300", "print", "\"hello\"", "at", "300", ",", "300"));
		assertTrue(test("print \"hello world\" at 300, 300", "print", "\"hello world\"", "at", "300", ",", "300"));
		assertTrue(test("print \"hello world here is a \\\"string quote\" at 300, 300", "print", "\"hello world here is a \"string quote\"", "at", "300", ",", "300"));
		//assertTrue(test("draw a circle at", "draw", "a", "circle", "at"));
	}
	
	@Test
	public void testMultiLineLex() {
		assertTrue(test("foo bar\nbaz quux", "foo", "bar", "\n", "baz", "quux"));
		assertTrue(test("foo bar\n\tbaz quux", "foo", "bar", "\n", "\t", "baz", "quux"));
		assertTrue(test("foo bar\n\t\tbaz quux", "foo", "bar", "\n", "\t", "\t", "baz", "quux"));
		assertTrue(test("foo bar\n\t\tbaz quux\nbar", "foo", "bar", "\n", "\t", "\t", "baz", "quux", "\n", "bar"));
		//assertTrue(test("draw a circle at", "draw", "a", "circle", "at"));
	}
	
	@Test
	public void testPossessiveLex() {
		assertTrue(test("draw a circle at keshav's x, keshav's y", "draw", "a", "circle", "at", "keshav's", "x", ",", "keshav's", "y"));
	}
	
	public boolean test(String ... lex) {
		if (lex.length > 0) {
			String[] tokens = lexer.lex(lex[0]);
			if (tokens.length != lex.length - 1) {
				System.out.println("LENGTH MISMATCH: got " + tokens.length + " tokens, expected " + (lex.length - 1));
				print(tokens);
				print(lex, 1);
				return false;
			}
			for (int i = 0 ; i < tokens.length ; i++) {
				if (! tokens[i].equals(lex[i + 1])) {
					System.out.println("MISMATCH: got " + tokens[i] + ", expected " + lex[i + 1]);
					print(tokens, 0, i);
					print(lex, 1, i + 1);
					return false;
				}
			}
			//System.out.println("PASS: " + lex[0]);
		}
		
		return true;
	}
	
	public void print(String[] tokens) {
		print(tokens, 0, -1);
	}
	
	public void print(String[] tokens, int offset) {
		print(tokens, offset, -1);
	}
	
	/**
	 * Prints out the token stream.
	 * @param tokens
	 */
	public void print(String[] tokens, int offset, int error) {
		// For each token
		PrintStream stream;
		for (int i = offset ; i < tokens.length ; i++) {
			stream = System.out;
			if (i == error) stream = System.err;
			
			stream.print("[");
			// Print out the token
			if (tokens[i].equals("\n"))
				stream.print("\\n");
			else if (tokens[i].equals("\t"))
				stream.print("\\t");
			else
				stream.print(tokens[i]);
			
			stream.print("] ");
		}
		System.out.println();
	}

}
