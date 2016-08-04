package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import expression.RGB;

public class Find extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4306715677158269289L;

	private static final int HEIGHT = 200;
	private static final int WIDTH = 400;

	// For formatting
	public static final String FONT = "Menlo";
	public static final int FONT_SIZE = 16;
	public static final int LABEL_FONT_SIZE = 16;
	public static final int BUTTON_FONT_SIZE = 20;
	
	boolean findPressed=false;


	private static final Color BACKGROUND = RGB.fromHex("#ECEFF1");
	private static final Color BORDERCOLOR = Color.GRAY;
	
	JPanel container = new JPanel(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
			
	static JFrame frame;
	
	//Create the buttons and text panes
	JButton findButton = new JButton();
	JTextPane findArea = new JTextPane();
	JTextPane replaceArea = new JTextPane();


	public Find(int x, int y, int width){
		super ("Find");
		
		initialize();
		
        setLocation(x + width, y);

		setVisible(true);
	}

	public void initialize(){

		setSize(WIDTH,HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}


		//Create the find label and text pane
		findArea.setFont(new Font(FONT, 0, FONT_SIZE));
		findArea.setBorder(BorderFactory.createLineBorder(BORDERCOLOR));

		JLabel findLabel = new JLabel("Find:");
		findLabel.setFont(new Font(FONT, 0, LABEL_FONT_SIZE));
		findLabel.setBackground(BACKGROUND);

		//Create the replace label and text pane
		replaceArea.setFont(new Font(FONT, 0, FONT_SIZE));
		replaceArea.setBorder(BorderFactory.createLineBorder(BORDERCOLOR));

		JLabel replaceLabel = new JLabel("Replace With:");
		replaceLabel.setFont(new Font(FONT, 0, LABEL_FONT_SIZE));
		replaceLabel.setBackground(BACKGROUND);
		
		//Create the find button
		findButton.setFont(new Font("Menlo", 0, 20));
		findButton.setBackground(RGB.fromHex("#ECEFF1"));
		findButton.setText("Find");
		

		JPanel filler1 = new JPanel();
		JPanel filler2 = new JPanel();


		//Set the location of the find label and text pane
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridy=0;
		c.gridx=1;
		c.gridwidth=3;
		c.weightx=1;
		container.add(findArea,c);

		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridy=0;
		c.gridx=0;
		c.gridwidth=1;
		c.weightx=0;
		container.add(findLabel,c);

		//Add a filler panel to add spacing between the find label and text pane and
		//the replace label and text pane
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=1;
		container.add(filler1, c);

		//Set the location of the replace label and text pane
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridy=2;
		c.gridx=1;
		c.gridwidth=3;
		c.weightx=1;
		container.add(replaceArea,c);

		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridy=2;
		c.gridx=0;
		c.gridwidth=1;
		c.weightx=0;
		container.add(replaceLabel,c);
		
		//Add a filler panel to add external padding
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=3;
		c.ipady=50;
		container.add(filler2, c);
		
		//Set the location of the find button
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridy=4;
		c.gridx=0;
		c.gridwidth=4;
		c.ipady=10;
		c.weightx=1;
		container.add(findButton, c);

		
		
		//Create a clear border for easier reading of the text
		container.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

		add(container);
		
	}
	
	
	
	
}
