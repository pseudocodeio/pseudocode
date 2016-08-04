package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import expression.Constant;
import expression.RGB;
import parser.Lexer;

/**
 * 
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 *
 * @license MIT
 */
public class Editor extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	
	// References to the text area and parent frame.
	private JTextPane area;
	private Pseudocode pseudocode;
	
	// For formatting
	public static final String FONT = "Menlo";
	public static final int FONT_SIZE = 18;
	
	private static final Color background = RGB.fromHex("#ECEFF1");
	private static final Color foreground = RGB.fromHex("#263238");
	private static final Color keyword = RGB.fromHex("#3F51B5");
	private static final Color attribute = RGB.fromHex("#2196F3");
	private static final Color number = RGB.fromHex("#673AB7");	
	private static final Color operator = RGB.fromHex("#F44336");
	private static final Color string = RGB.fromHex("#FFC107");
	private static final Color comment = RGB.fromHex("#26A65B");
	
	private static Lexer lexer;
	
	//The undo text storage array list
	private static ArrayList<String> undoText = new ArrayList<String>();
	
	/**
	 * Constructs the editor.
	 * @param pseudocode
	 */
	public Editor(Pseudocode pseudocode) {
		this.pseudocode = pseudocode;
		
		// Set the size and layout manager for this panel.
		setSize(pseudocode.getWidth() / 2, pseudocode.getHeight());
		setLayout(new BorderLayout());
		
		// Create a text area in a scroll pane 
		area = new JTextPane();
		area.setFont(new Font(FONT, 0, FONT_SIZE));
		area.setBackground(background);
		initializeFormatting();
		
		JPanel areaPanel = new JPanel( new BorderLayout() );
		areaPanel.add(area);
		JScrollPane pane = new JScrollPane(areaPanel);
		area.addKeyListener(this);
		add(pane);
	}
	
	public void updateText(String text) {
		area.setText(text);
		format();
	}
	
	public String getText() {
		return area.getText();
	}
	
	public void format() {
		StyledDocument document = area.getStyledDocument();
		document.setCharacterAttributes(0, area.getText().length(), area.getStyle("base"), true);
		
		// Position in the formatted document
		int position = 0;
		// Get each line of text in the textarea
		String[] lines = area.getText().split("\n");
		
		// Go to every line
		for (String line : lines) {
			// Split the line into tokens
			String[] tokens = lexer.lex(line, true, false);
			boolean firstToken = true;
			boolean comment = false;
			
			// Go to each token
			for (int i = 0 ; i < tokens.length ; i++) {
				// Tabs get ignored
				if (tokens[i].equals("\t")) {
					position++;
					continue;
				}
				
				String token = tokens[i];
				String style = null;
				
				// Conditions to determine a special syntax highlighting style
				// TODO: add more styles (future)
				if(!comment){
					if (firstToken && Constant.keyword.contains(token))
						style = "keyword";
					else if (token.matches("/")){
						i++;
						if(i < tokens.length && tokens[i].equals("/")){
							comment = true;
							style = "comment";
						}
						else
							style = "operator";
						i--;
					}
					else if (Constant.operator.contains(token)) 
						style = "operator";
					else if (Constant.attribute.contains(token))
						style = "attribute";
					else if (token.matches("\\d+(|\\.\\d*)"))
						style = "number";
					else if (token.startsWith("\"") && token.endsWith("\""))
						style = "string";
				}
				else{
					if(token.matches("\n"))
						comment = false;
					style = "comment";
				}
				
	
				// If a style was found, then apply it to this section
				if (style != null) {
					document.setCharacterAttributes(position, token.length(), area.getStyle(style), true);
				}
				
				// Advanced the position to the next token
				position += token.length();
				firstToken = false;
			}
			// Account for the newline character
			position++;
		}
	}
	
	/**
	 * Initializes the styles used by the editor to format text.
	 * @see NXTalkFormatter
	 */
	private void initializeFormatting() {
		lexer = new Lexer();
		
		addStyle("base", foreground, false, false);
		addStyle("keyword", keyword, true, false);
		addStyle("attribute", attribute, false, false);
		addStyle("number", number, false, false);
		addStyle("string", string, false, false);
		addStyle("operator", operator, false, false);
		addStyle("comment", comment, false, false);
		//addStyle("operator", Color.RED, false, false);
	}
	
	private void addStyle(String name, Color color, boolean bold, boolean italic) {
		Style style = area.addStyle(name, null);
		StyleConstants.setForeground(style, color);
		StyleConstants.setFontSize(style, FONT_SIZE);
		StyleConstants.setFontFamily(style, FONT);
		StyleConstants.setBold(style, bold);
		StyleConstants.setItalic(style, italic);
	}

	
//		
//		Style style;
//		for (int syntax = 0 ; syntax <= NXTalk.SyntaxTypes ; syntax++) {
//			style = editor.addStyle("" + syntax, base);
//			if (NXTalk.isSyntaxBold(syntax))
//				StyleConstants.setBold(style, true);
//			if (NXTalk.isSyntaxItalic(syntax))
//				StyleConstants.setItalic(style, true);
//			StyleConstants.setForeground(style, NXTalk.getSyntaxColor(syntax));
//		}
	
//	public synchronized void refreshFormatting() {
//		model.synchronize(editor);
//		int caret = editor.getCaretPosition();
//		NXTalkFormatterOutput output = model.formatText(editor.getCaretPosition());
//		editor.setText(output.getFormattedText());
//		editor.setCaretPosition(caret);
//		StyledDocument doc = editor.getStyledDocument();
//		while (output.hasNextFormatType())
//			doc.setCharacterAttributes(
//					output.getNextPosition(), 
//					output.getNextLength(),
//					editor.getStyle(output.getNextFormatType()), true);
//	}

	/**
	 * When a key is released, the pseudocode frame is updated.
	 */
	public void keyReleased(KeyEvent e) {
		// Auto-indentation
		if (e.getKeyCode() == 10) {
			// Get the current line and caret position
			int caret = area.getCaretPosition();
			String text = area.getText();
			String[] lines = text.substring(0, caret).split("\n");
			int line = lines.length;
			
			if (line > 0) {
				String indent = "";
				while (indent.length() < lines[line - 1].length() && 
					   lines[line - 1].charAt(indent.length()) == '\t')
					indent += "\t";
				
				if (indent.length() < lines[line - 1].length()) {
					String last = lines[line - 1].substring(indent.length());
					if (last.startsWith("if") || last.startsWith("forever") || last.startsWith("otherwise")) {
						indent += "\t";
					}
				}
				
				if (indent.length() > 0) {
					area.setText(text.substring(0, caret) + indent + text.substring(caret));
					area.setCaretPosition(caret + indent.length());
				}
			}
		}
		else {
			pseudocode.update(area.getText());
		}
		format();
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE||e.getKeyCode()==KeyEvent.VK_ENTER){
			//log all the text in the editor to a new instance in the array list undoText
			undoText.add(area.getText());
		}
		
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	
	/**
	 * Used to retrieve the array of undoable edits
	 * @return the array of undoable edits, undoText
	 */
	public ArrayList<String> returnUndoText(){
		return undoText;
	}
	
	/**
	 * Used to set the array of undoable edits to a new array
	 * @param input the new array of undoable edits
	 */
	public void setUndoText(ArrayList<String> input){
		undoText=input;	
	}
	
	/**
	 * Used to add a single instance to the array of undoable edits, undoText
	 * @param input the String which is to be added to the array of undoable edits, undoText
	 */
	public void addUndoText(String input){
		undoText.add(input);
	}
	
	public Highlighter getHighlighter(){
		return area.getHighlighter();
	}
		
	
}
