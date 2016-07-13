package instruction;

import java.awt.Color;
import java.awt.Graphics;

import expression.Expression;
import expression.Operator;
import expression.Terminal;

public class Draw extends Instruction {
	
	private static final double DEFAULT_SIZE = 50;
	
	private enum Shape {
		Circle, Square, Oval, Rectangle, Line
	};
	
	Shape type;
	Expression x;
	Expression y;
	Expression width;
	Expression height;
	Color color;
	boolean randomColor = false;
	
	/**
	 * Constructs a drawing instruction from the given String description of the shape to be drawn.
	 * @param name
	 */
	public Draw(String name) {
		if (name.equals("circle"))
			type = Shape.Circle;
		if (name.equals("square"))
			type = Shape.Square;
		if (name.equals("oval"))
			type = Shape.Oval;
		if (name.equals("rectangle"))
			type = Shape.Rectangle;
		if (name.equals("line"))
			type = Shape.Line;
		
		color = Color.BLACK;
	}
	
	/**
	 * Sets the center/initial x position of this shape.
	 * @param x - an Expression object describing the x position 
	 */
	public void setX(Expression x) {
		this.x = x;
	}
	
	/**
	 * Sets the center/initial y position of this shape.
	 * @param y - an Expression object describing the y position
	 */
	public void setY(Expression y) {
		this.y = y;
	}
	
	/**
	 * For lines only, sets the endpoint's x position of this shape.
	 * @param x - an Expression object describing the x position of the endpoint
	 */
	public void setEndX(Expression x) {
		this.width = x;
	}
	
	/**
	 * For lines only, sets the endpoint's y position of this shape.
	 * @param y - an Expression object describing the y position of the endpoint
	 */
	public void setEndY(Expression y) {
		this.height = y;
	}
	
	/**
	 * Sets the size of this shape to the given value.
	 * @param size - an Expression object describing the size of the object
	 */
	public void setSize(Expression size) {
		this.width = size;
		this.height = size;
	}
	
	/**
	 * Sets the radius of this shape to the given value, assuming it is a circle or oval.
	 * @param size - an Expression object describing the radius of the object
	 */
	public void setRadius(Expression radius) {
		this.width = this.height = new Expression(radius, Operator.Multiply, new Terminal(2));
	}
	
	/**
	 * Sets the diameter of this shape to the given value, assuming it is a circle or oval.
	 * @param size - an Expression object describing the diameter of the object
	 */
	public void setDiameter(Expression diameter) {
		this.width = this.height = diameter;
	}
	
	/**
	 * Sets the width of this shape.
	 * @param x - an Expression object describing the width
	 */
	public void setWidth(Expression width) {
		this.width = width;
	}
	
	/**
	 * Sets the height of this shape.
	 * @param x - an Expression object describing the height
	 */
	public void setHeight(Expression height) {
		this.height = height;
	}
	
	/**
	 * Sets the color of this shape.
	 * @param color - a Color instance describing the color of this shape
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Sets the color of this shape to a random color.
	 */
	public void setRandomColor(boolean randomColor) {
		this.randomColor = randomColor;
	}
	
	@Override
	public void execute(Graphics g, Block algorithm) {
		// Evaluate expressions for this shape
		double x = (this.x != null) ? this.x.evaluate(algorithm) : 0;
		double y = (this.y != null) ? this.y.evaluate(algorithm) : 0;
		double width = (this.width != null) ? this.width.evaluate(algorithm) : DEFAULT_SIZE;
		double height = (this.height != null) ? this.height.evaluate(algorithm) : DEFAULT_SIZE;
		
		// Set the drawing color
		if (randomColor)
			g.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
		else
			g.setColor(color);
		
		// Draw the corresponding shape.
		switch (type) {
		case Circle:
		case Oval:
			g.fillOval((int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height);
			break;
		case Square:
		case Rectangle:
			g.fillRect((int) (x - width / 2), (int) (y - height / 2), (int) width, (int) height);
			break;
		case Line:
			if (x != width || y != height)
				g.drawLine((int) x, (int) y, (int) width, (int) height);
		}
	}
	
	@Override
	public boolean equals(Instruction instruction, Block block) {
		// Checks if they are draw instructions for the same thing.
		if (instruction instanceof Draw) {
			Draw other = (Draw) instruction;
			if (this.color != other.color)
				return false;
			if (this.x != null && ! this.x.equals(other.x))
				return false;
			if (this.y != null && ! this.x.equals(other.y))
				return false;
			if (this.width != null && ! this.width.equals(other.width))
				return false;
			if (this.height != null && ! this.height.equals(other.height))
				return false;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		switch (type) {
		case Circle: return "Window.out.circle(" + x + ", " + y + ", " + width + ");\n";
		case Square: return "Window.out.square(" + x + ", " + y + ", " + width + ");\n";
		case Oval: return "Window.out.oval(" + x + ", " + y + ", " + width + ", " + height + ");\n";
		case Rectangle: return "Window.out.rectangle(" + x + ", " + y + ", " + width + ", " + height + ");\n";
		case Line: return "Window.out.line(" + x + ", " + y + ", " + width + ", " + height + ");\n";
		}
		return "";
	}
	
	public boolean isRectangle() {
		return type == Shape.Rectangle || type == Shape.Square;
	}
	
	public boolean isOval() {
		return type == Shape.Circle || type == Shape.Oval;
	}

	public boolean isLine() {
		return type == Shape.Line;
	}
	
}
