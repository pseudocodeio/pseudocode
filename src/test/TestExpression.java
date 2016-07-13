package test;

import static org.junit.Assert.*;

import org.junit.Test;

import expression.*;

public class TestExpression {

	@Test
	public void testTerminal() {
		Terminal one = new Terminal(1);
		Terminal anotherOne = new Terminal(1);
		Terminal two = new Terminal(2);
		
		assertFalse(one.evaluate(null) == two.evaluate(null));
		assertTrue(one.evaluate(null) == anotherOne.evaluate(null));
	}
	
	@Test
	public void testToString() {
		Terminal t1 = new Terminal(10);
		SymbolTerminal s = new SymbolTerminal("dog");
		System.out.println(t1);
		
		Expression e = new Expression(t1, Operator.Add, s);
		System.out.println(e);
		
		Expression f = new Expression(e, Operator.Multiply, t1);
		System.out.println(f);
	}

}
