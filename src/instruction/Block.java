package instruction;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expression.Expression;
import expression.SymbolTerminal;
import expression.Terminal;
import view.Console;

/**
 * Represents a block of pseudocode instructions.
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 * 
 * @license MIT
 */
public class Block extends Instruction {
	
	// Reference to the symbol table. If this block is created by a parent block, this
	// will reference the symbol table of the parent block.
	private HashMap <String, HashMap <String, Double>> symbol;
	private HashMap <String, HashMap <String, Double>> local;
	private HashMap <String, Block> function;
	private Block parent;
	
	//Constants for the types in the symbol tables
	private final static double NUMBER = 0;
	private final static double LIST = 1;
	private final static double STRING = 2;
	
	// The console output view.
	private Console console;
	
	// A list of instructions in the block.
	private ArrayList <Instruction> instructions;
	
	// Parameters for a block that is executed as a function
	private ArrayList <String> parameters;
	
	// Integers for storing the progress through the block and its indent level
	private int currentInstruction = 0;
	private int indentLevel = 0;
	
	/**
	 * Creates the root block.
	 */
	public Block() {
		this(null);
	}
	
	/**
	 * Creates a block within the given parent block.
	 * @param parent
	 */
	public Block(Block parent) {
		instructions = new ArrayList <Instruction> ();
		local = new HashMap <String, HashMap<String, Double>> ();
		
		// If this is the root block
		if (parent == null) {
			symbol = new HashMap <String, HashMap<String, Double>> ();
			function = new HashMap <String, Block> ();
		}
		
		// Otherwise take the symbol table from the root block
		else { 
			symbol = parent.symbol;
			indentLevel = parent.indentLevel + 1;
		}
	}
	
	/**
	 * Sets the console output view of this block to the given console.
	 * 
	 * @param console - the Console object representing the console output JFrame
	 */
	public void setConsole(Console console) {
		this.console = console;
	}
	
	/**
	 * Add an instruction to this block.
	 * @param instruction - an Instruction object
	 */
	public void add(Instruction instruction) {
		instruction.setBlock(this);
		instructions.add(instruction);
	}
	
	/**
	 * Adds a parameter to this block.
	 */
	public void addParameter(String parameter) {
		if (parameters == null)
			parameters = new ArrayList <String> ();
		parameters.add(parameter);
	}
	
	/**
	 * Sets the parameters of this block to their current values before evaluating.
	 */
	public void setParameters(Block block, HashMap <String, Expression> arguments) {
		if (parameters != null) {
			for (String parameter : parameters) {
				if (arguments.containsKey(parameter))
					assignLocal(parameter, arguments.get(parameter).evaluate(block));
			}
		}
	}
	
	/**
	 * Executes this block with the given Graphics object and root execution block.
	 */
	public void execute(Graphics graphics, Block block) {
		// If this block is not yet complete
		if (! isComplete()) {
			// Check if the current instruction should execute.
			if (currentInstruction().shouldExecute(block)) {
				
				// If it should, run its execute function with the graphics and block object passed to this object.
				currentInstruction().execute(graphics, block);
				
				// If the current instruction does not request to be repeated, go to the next instruction.
				if (! currentInstruction().shouldRepeat())
					nextInstruction();
			}
			// If it shouldn't be executed, skip it.
			else nextInstruction();
		}
	}

	/**
	 * Returns true if the block is complete, and false otherwise.
	 * @return whether the block has executed all instructions
	 */
	public boolean isComplete() {
		return currentInstruction >= instructions.size();
	}
	
	/**
	 * Gets the indentation level of this block.
	 * @return block indent level
	 */
	public int getIndentLevel() {
		return indentLevel;
	}

	/**
	 * Returns the Instruction object that is currently being performed.
	 * @return the current Instruction object.
	 */
	public Instruction currentInstruction() {
		return instructions.get(currentInstruction);
	}
	
	/**
	 * Returns the previous Instruction object.
	 */
	public Instruction previousInstruction() {
		if (currentInstruction == 0) return null;
		return instructions.get(currentInstruction - 1);
	};

	/**
	 * Resets the program counter to the first instruction.
	 */
	public void reset() {
		currentInstruction = 0;
	}
	
	/**
	 * Advances the program counter to the next instruction.
	 */
	public void nextInstruction() {
		currentInstruction++;
	}
	
	/**
	 * Returns the number of instructions in this block.
	 * @return the size of the block in terms of instructions
	 */
	public int length() {
		return instructions.size();
	}
	
	public HashMap<String, HashMap<String, Double>> getSymbolTable() {
		return symbol;
	}

	/**
	 * Returns the String representation of this block by concatenating the String
	 * representations of each instruction.
	 */
	public String toString() {
		// Create a StringBuilder object for assembling the String representation of this block.
		StringBuilder builder = new StringBuilder();
		// Append the open block syntax
		builder.append("{\n");

		// Append every instruction's String representation
		for (Instruction instruction : instructions) {
			
			// First append the number of tabs to create this block's indentation level
			for (int j = 0 ; j < indentLevel + 1 ; j++)
				builder.append("\t");
			
			// Then append the instruction
			builder.append(instruction.toString());
			
			// Then append a new line.
			builder.append("\n");
		}

		// Append the closing block syntax indented to this block's level.
		for (int j = 0 ; j < indentLevel ; j++)
			builder.append("\t");
		builder.append("}\n");
		return builder.toString();
		
	}
	
	/**
	 * Returns true if this object is equal to another instruction in the parse tree.
	 */
	public boolean equals(Instruction instruction, Block outerBlock) {
		if (instruction instanceof Block) {
			Block other = (Block) instruction;
			
			// If the block has a different number of instructions
			if (this.instructions.size() != other.instructions.size())
				return false;
			
			// Check that every instruction is equal to the other
			for (int i = 0 ; i < this.instructions.size() ; i++)
				if (! this.instructions.get(i).equals(other.instructions.get(i)))
					return false;
			
			// Return true if all tests passed
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if there is an assigned value to the given symbol.
	 * @param symbol - the name of the symbol
	 * @return true if the symbol exists, false otherwise
	 */
	public boolean hasSymbol(SymbolTerminal symbol) {
		return this.local.containsKey(symbol.toString()) || this.symbol.containsKey(symbol.toString());
	}
	
	/**
	 * Returns true if there is an assigned value to the given symbol.
	 * @param symbol - the name of the symbol
	 * @return true if the symbol exists, false otherwise
	 */
	public boolean hasSymbol(String symbol) {
		return this.local.containsKey(symbol) || this.symbol.containsKey(symbol);
	}
	
	
	/**
	 * Returns the value assigned to the given symbol name.
	 * @param symbol - the name of the symbol
	 * @return the value assigned to the symbol, or 0 if no value was assigned
	 */
	public double get(SymbolTerminal symbol, Expression exp) {
		int index = 0;
		if(exp != null)
			 index = (int) exp.evaluate(this);
		return get(symbol.toString(), index, getScope(symbol.toString()));
	}

	public double get(String symbol, int index) { return get(symbol, index, getScope(symbol)); }
	
	public double get(String symbol){ return get(symbol, 0, getScope(symbol)); }
	
	/**
	 * Returns an array of the entire list
	 */
	public ArrayList<Double> getList(String symbol){
		ArrayList<Double> values = new ArrayList<Double>();
		if(hasSymbol(symbol)){
			for(int i=0; i < getScope(symbol).get(symbol).get("length");i++){
				values.add(get(symbol, i , getScope(symbol)));
			}
		}
		return values;
	}
	
	/**
	 * Returns the value assigned to either the local or global variables
	 * @param symbol - name of the variable
	 * @param index - index if list
	 * @param values - the HashMap of either the local or global variables
	 * @return
	 */
	public double get(String symbol, int index, HashMap <String, HashMap <String, Double>> values){
		if(values == null)
			return 0;
		String getName = "value";
		if(getScope(symbol).get(symbol.toString()).get("type") == LIST){
			if(index >= getScope(symbol).get(symbol.toString()).get("length") || index < 0)
				index = 0;
			getName = "" + index;
		}
		return values.get(symbol).get(getName);
	}
	
	/**
	 * Various methods that call the assign method below
	 */
	public void assign(String symbol, Expression value){ assign(symbol, value.evaluate(this), null, this.symbol); }
	public void assign(String symbol, double value){ assign(symbol, value, null, this.symbol); }
	public void assign(SymbolTerminal symbol, Expression value, Expression index){ assign(symbol.toString(), value.evaluate(this), index, this.symbol); }
	public void assignLocal(String symbol, double value){ assign(symbol, value, null, local); }
	
	/**
	 * Assigns the given value to the given variable
	 * @param symbol - name of variable
	 * @param value - what the variable equals
	 * @param i - index of what is being set if the variable is a list
	 * @param scope - HashMap of local or global variable
	 */
	public void assign(String symbol, double value, Expression i, HashMap <String, HashMap <String, Double>> scope){
		int index = -1;
		if(i != null)
			index = (int) i.evaluate(this);
		if(isType(scope, symbol, LIST) && index > -1){
			if(index > scope.get(symbol).get("length")){
				index = 0;
			}
			scope.get(symbol).put("" + index, value);
		}
		else{
			HashMap <String, Double> valueMap = new HashMap <String, Double>();
			valueMap.put("type", NUMBER);
			valueMap.put("value", (double)value);
			scope.put(symbol, valueMap);
		}
	}

	public void assign(SymbolTerminal symbol, ArrayList<Expression> value){
		HashMap <String, Double> valueMap = new HashMap <String, Double>();
		if(value.size() == 1){
			valueMap.put("type", NUMBER);
			valueMap.put("value", value.get(0).evaluate(this));
			this.symbol.put(symbol.toString(), valueMap);
		}
		else{
			valueMap.put("type", LIST);
			valueMap.put("length", (double) value.size());
			for(int i = 0; i < value.size(); i++){
				valueMap.put("" + i, value.get(i).evaluate(this));
			}
			this.symbol.put(symbol.toString(), valueMap);
		}
	}
	
	/**
	 * Defines a function with the given name and block.
	 */
	public void define(SymbolTerminal symbol, Block block) {
		this.function.put(symbol.toString(), block);
	}
	
	/**
	 * Defines a function with the given name and block.
	 */
	public void define(String symbol, Block block) {
		this.function.put(symbol, block);
	}
	
	/**
	 * Returns the function definition for the given symbol.
	 */
	public Block getDefinition(String symbol) {
		return this.function.get(symbol);
	}
	
	/**
	 * Prints the given value to the console.
	 */
	public void print(String output) {
		console.print(output);
	}
	
	/**
	 * Prints the given error message to the console.
	 */
	public void error(String message) {
		console.error(message);
	}
	
	/**
	 * Returns the scope of the given variable
	 **/
	private HashMap<String, HashMap<String, Double>> getScope(String symbol){
		if (local.containsKey(symbol))
			return local;
		else if (hasSymbol(symbol)){
			return this.symbol;
		}
		return null;
	}
	
	/**
	 * Returns true if the given variable is of the given type
	 */
	private boolean isType(HashMap< String, HashMap<String, Double>> map, String variable, double type) {
		if(map != null){
			if(map.containsKey(variable))
				return map.get(variable).get("type") == type;
		}
		return false;
	}
	/**
	 * Returns the value that needs to be printed
	 */
	public String printVal(String symbol, int index){
		if(isType(getScope(symbol), symbol, NUMBER))
			return "" + get(symbol);
		else if(isType(getScope(symbol), symbol, LIST)){
			String print = "";
			if(index > 0){
				print = "" + get(symbol,index);
			}
			else{
				for(Double i : getList(symbol)){
					print += i + ", ";
				}
				print = print.substring(0, print.lastIndexOf(","));
			}
			
			return print;
		}
		return "";
	}
}
