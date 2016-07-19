package instruction;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import expression.Expression;
import expression.Operator;
import expression.Terminal;

public class Draw extends Instruction {
	
	private static final double DEFAULT_SIZE = 50;
	
	private enum Shape {
		Circle, Square, Oval, Rectangle, Line, Image, Polygon
	};
	
	Shape type;
	Expression x;
	Expression y;
	Expression width;
	Expression height;
	Color color;
	boolean randomColor = false;
	BufferedImage image = null;
	boolean filled = false;
	
	//ArrayList for all the points in a arbitrary polygon
	ArrayList<Expression> polyx = new ArrayList<Expression>();
	ArrayList<Expression> polyy = new ArrayList<Expression>();
	
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
		if (name.equals("image"))
			type = Shape.Image;
		if (name.equals("polygon"))
			type = Shape.Polygon;
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
	 * Adds an x coordinate to an array that stores the points of a arbitrary polygon
	 * @param x - and Expression object describing the x position of a point on a arbitrary polygon
	 */
	public void addX(Expression x){
		this.polyx.add(x);
	}
	
	/**
	 * Adds an y coordinate to an array that stores the points of a arbitrary polygon
	 * @param y - and Expression object describing the x position of a point on a arbitrary polygon
	 */
	public void addY(Expression y){
		this.polyy.add(y);
	}
	
	public void isFilled(boolean filled){
		this.filled = filled;
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
	
	/**
	*tries to read the image from the computer and if can't reads from web
	*/
	public void setImageLocation(String loc){
		loc = loc.replace("\"", "");
		// read image from computer
		try {
			this.image = ImageIO.read(new File(loc));
		} catch (IOException e) {
			// if fail read from url
			try{
				URL url = new URL(loc);
				this.image = ImageIO.read(url);
			} catch (IOException e1){
				// if both fail tell the user that the image didn't load
				System.out.println("image not found");
			}
            
			
		}
	}
	
	@Override
	public void execute(Graphics g, Block algorithm) {
		// Evaluate expressions for this shape
		double x = (this.x != null) ? this.x.evaluate(algorithm) : 0;
		double y = (this.y != null) ? this.y.evaluate(algorithm) : 0;
		double width = (this.width != null) ? this.width.evaluate(algorithm) : DEFAULT_SIZE;
		double height = (this.height != null) ? this.height.evaluate(algorithm) : DEFAULT_SIZE;
		
		//converts the Expression ArrayList for the polygon points into int arrayg
		int[] polyx = new int[this.polyy.size()];
		int[] polyy = new int[this.polyy.size()];
		for(int i = 0;i < polyx.length;i++){
			polyx[i] = (this.polyx.get(i) != null) ? (int) this.polyx.get(i).evaluate(algorithm) : 0;
			polyy[i] = (this.polyy.get(i) != null) ? (int) this.polyy.get(i).evaluate(algorithm) : 0;
		}
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
			break;
		case Image:
			g.drawImage(image, (int)x, (int)y, (int)width, (int)height, null);
			break;
		case Polygon:
	        Polygon poly = new Polygon(polyx, polyy, polyx.length);
	        if(filled)
	        	g.fillPolygon(poly);
	        else
	        	g.drawPolygon(poly);
			break;
		}
	}
	
	/**
	 * Returns true if the given instruction draws the same shape in the context of the given block.
	 * @param instruction
	 * @param block
	 * @return
	 */
	public boolean equals(Instruction instruction, Block block) {
		if (instruction instanceof Draw) {
			Draw other = (Draw) instruction;
			if (this.color.getRed() != other.color.getRed() ||
				this.color.getGreen() != other.color.getGreen() ||
				this.color.getBlue() != other.color.getBlue())
				return false;
			if ((this.x != null && ! this.x.equals(other.x)) || (this.x == null && other.x != null))
				return false;
			if ((this.y != null && ! this.x.equals(other.y)) || (this.y == null && other.y != null))
				return false;
			if ((this.width != null && ! this.width.equals(other.width)) || (this.width == null && other.width != null))
				return false;
			if ((this.height != null && ! this.height.equals(other.height)) || (this.height == null && other.height != null))
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
		case Polygon: return "TODO\n";
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
	public boolean isImage(){
		return type == Shape.Image;
	}
	public boolean isPolygon(){
		return type == Shape.Polygon;
	}
	
}
