package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Console extends JFrame {

	private Pseudocode pseudocode;
	private JTextPane area;
	
	private static Color background = new Color(20, 20, 20);
	private static Color foreground = new Color(250, 250, 250);

	public Console(Pseudocode pseudocode) {
		super("Console");
		this.pseudocode = pseudocode;
		setSize(Pseudocode.SIZE, Pseudocode.SIZE / 2);
		setLocation((int) pseudocode.getLocation().getX(), (int) (pseudocode.getLocation().getY() + Pseudocode.SIZE + 50));
		
		// Create a text area in a scroll pane 
		area = new JTextPane();
		area.setFont(new Font(Editor.FONT, 0, Editor.FONT_SIZE));
		area.setBackground(background);
		area.setForeground(foreground);
		area.setEditable(false);

		JPanel areaPanel = new JPanel( new BorderLayout() );
		areaPanel.add(area);
		JScrollPane pane = new JScrollPane(areaPanel);
		add(pane);
	}

	public void reset() {
		area.setText("");
	}
	
	public void print(String text) {
		if (area.getText().equals(""))
			area.setText("  " + text);
		else
			area.setText(area.getText() + "\n  " + text);
		
		if (! isVisible())
			setVisible(true);
	}
	
	public void error(String message) {
		// TODO: special highlighting color
		print(message);
	}
}
