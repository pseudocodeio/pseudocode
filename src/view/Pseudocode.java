package view;

import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import instruction.Block;
import parser.Parser;

/**
 * 
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 *
 * @license MIT
 */
public class Pseudocode extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Create an instance of this JFrame on execution.
	 */
	public static void main(String[] args) { new Pseudocode(); };
	
	// Display constants
	public static final int SIZE = 600;
	public static int windowCount = 0;
	
	// Editor and output panel reference.
	Editor editor;
	Interpreter interpreter;
	
	// Parser
	Parser parser;
	Block parsed;
	
	/**
	 * Constructs this pseudocode editor.
	 */
	public Pseudocode() {
		super("Pseudocode");
		setSize(SIZE * 2, SIZE + 50);
		
		windowCount++;
		setDefaultCloseOperation((windowCount > 1) ? JFrame.DISPOSE_ON_CLOSE : JFrame.EXIT_ON_CLOSE);
		setJMenuBar(new PseudocodeMenuBar(this));
		setLocationByPlatform(true);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		// Creates a JPanel to contain the editor and output panel.
		JPanel container = new JPanel();
		editor = new Editor(this);
		interpreter = new Interpreter(this);
		parser = new Parser();
		
		this.addComponentListener(new ComponentListener() {

			public void componentResized(ComponentEvent e) {
				interpreter.setSize(getWidth() / 2, getHeight());
				if (parsed != null) {
					interpreter.interpret(parsed);
				}
			}
			
			public void componentMoved(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
			
		});
		
		// Add the panels to the container, and add the container to the JFrame.
		container.setLayout(new GridLayout(1, 2));
		container.add(editor);
		container.add(interpreter);
		add(container);
		
		//setResizable(false);
		setVisible(true);
	}

	/**
	 * Called whenever the text in the editor is updated.
	 * @param text  the text to update the editor with
	 */
	public void update(String text) {
		parsed = parser.parse(text);
		interpreter.interpret(parsed);
	}
	
	/**
	 * Called whenever the text in the editor needs to be updated
	 * @param text the text to update the editor with
	 */
	public void updateText(String text) {
		editor.updateText(text);
		update(text);
	}
	
	/**
	 * Called whenever the text in the editor needs to be accessed
	 */
	public String getText() {
		return editor.getText();
	}	
	
	/**
	 * Called whenever the array of undoable edits needs to be accessed
	 * @return the array of undoable edits
	 */
	public ArrayList<String> returnUndoText(){
		return editor.returnUndoText();
	}
	
	/**
	 * Used to set the array of undoable edits to a new array
	 * @param input the new array of undoable edits
	 */
	public void setUndoText(ArrayList<String> input){
		editor.setUndoText(input);
	}
	
	/**
	 * Used to add a single instance to the array of undoable edits
	 * @param input the string which is added to the array of undoable edits
	 */
	public void addUndoText(String input){
		editor.addUndoText(input);
	}
	
}