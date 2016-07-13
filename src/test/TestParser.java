package test;

import static org.junit.Assert.*;

import org.junit.Test;

import expression.*;
import parser.Parser;

public class TestParser {

	@Test
	public void test() {
		Parser parser = new Parser();
		parser.reset("1 + 1");
		
		Expression e = parser.parseExpression();
		assertEquals(e, "(1.0+1.0)");
		System.out.println(e);
	}

}
