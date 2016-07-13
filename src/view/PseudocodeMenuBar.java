package view;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * 
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 *
 * @license MIT
 */
@SuppressWarnings("serial")
public class PseudocodeMenuBar extends JMenuBar implements ActionListener {

	// Refers to the pseudocode frame that is containing this menu bar.
	private Pseudocode pseudocode;

	private static HashMap <String, String> example;
	private static String[][] exampleCategory = {
			{ "Basics", "Draw Shapes", "Mouse Coloring" },
			{ "Physics", "Bouncing Ball" }, 
			{ "Games", "Flappy Bird", "Paddle Bounce", "Etch A Sketch" }
	};

	// The menu item that shows the current mesh
	private static JMenuItem mesh;

	// The FileFilter object that is used to filter non-pseudocode files from being opened.
	private FileFilter pseudocodeFilter = new FileFilter() {
		
		/**
		 * Returns true if the given File object represents a valid pseudocode file.
		 */
		public boolean accept(File pathname) {
			return pathname.isDirectory() ||
					pathname.getAbsolutePath().endsWith(".pseudo") ||
					pathname.getAbsolutePath().endsWith(".txt");
		}

		/**
		 * Returns the description of pseudocode files for file choosers.
		 */
		public String getDescription() {
			return "Pseudocode (.pseudo, .txt)";
		}
	};

	// The current file being read/written to
	private File currentFile;

	private static Font font;

	public PseudocodeMenuBar(Pseudocode pseudocode) {
		super();

		this.pseudocode = pseudocode;
		font = new Font(Editor.FONT, 0, Editor.FONT_SIZE);
		this.setFont(font);
		initialize();
	}

	private void initialize() {
		JMenu fileMenu = createMenu("File");
		fileMenu.add(createMenuItem("New", 'N'));
		fileMenu.add(createMenuItem("Open", 'O'));
		fileMenu.add(createMenuItem("Save", 'S'));
		fileMenu.add(createMenuItem("Save As"));
		fileMenu.add(createMenuItem("Quit", 'Q'));
		add(fileMenu);

		JMenu meshMenu = createMenu("Mesh");
		mesh = createMenuItem("Start Mesh", 'M');
		meshMenu.add(mesh);
		meshMenu.add(createMenuItem("Join Mesh", 'J'));
		add(meshMenu);

		// Maps examples to their string representation
		example = new HashMap <String, String> ();

		// Create a menu for each example category
		for (String[] category : exampleCategory) {
			JMenu exampleMenu = createMenu(category[0]);

			// For each category
			for (int i = 1 ; i < category.length ; i++) {
				// Add a menu item for each example
				exampleMenu.add(createMenuItem(category[i]));
				example.put(category[i], readExample(category[i]));
			}

			add(exampleMenu);
		}
	}

	/**
	 * Reads the given File object and returns a String representing its contents.
	 * @param file - a reference to the File
	 * @return the file's contents as a String
	 */
	private String readFile(File file) {
		try {
			// Create a Scanner and StringBuilder object to read from this file.
			Scanner scanner = new Scanner(file);
			StringBuilder builder = new StringBuilder();

			// Read each line from the file
			while (scanner.hasNextLine()) {
				builder.append(scanner.nextLine());

				// Append new line characters between lines
				if (scanner.hasNextLine())
					builder.append("\n");
			}

			// Clean up resources and return the resulting String.
			scanner.close();
			return builder.toString();

		}
		// If the file wasn't found, return the empty string.
		catch (FileNotFoundException e) {
			return "";
		}
	}

	/**
	 * Reads the example file with the given name.
	 * @param name - name of the example
	 * @return string content of the example file
	 */
	private String readExample(String name) {
		return readFile(this.getClass().getResourceAsStream("/example/" + name.replace(' ', '_') + ".pseudo"));
	}

	/**
	 * Reads the given File object and returns a String representing its contents.
	 * @param file - a reference to the File
	 * @return the file's contents as a String
	 */
	private String readFile(InputStream file) {
		if (file != null) {
			// Create a Scanner and StringBuilder object to read from this file.
			Scanner scanner = new Scanner(file);
			StringBuilder builder = new StringBuilder();

			// Read each line from the file
			while (scanner.hasNextLine()) {
				builder.append(scanner.nextLine());

				// Append new line characters between lines
				if (scanner.hasNextLine())
					builder.append("\n");
			}

			// Clean up resources and return the resulting String.
			scanner.close();
			return builder.toString();

		}
		return "";
	}

	/**
	 * Creates a JMenu with the given name.
	 * @param name - the name of the menu
	 * @return a JMenu object
	 */
	private JMenu createMenu(String name) {
		JMenu menu = new JMenu(name);
		menu.setFont(font);
		return menu;
	}

	/**
	 * Returns a JMenuItem with the given name and a default action of the lowercase version 
	 * of the name.
	 * @param name - the name of the item
	 * @return the JMenuItem object representing this item
	 */
	private JMenuItem createMenuItem(String name) {
		return createMenuItem(name, name, (char) 0);
	}

	/**
	 * Returns a JMenuItem with the given name and action command.
	 * @param name - the name of the item
	 * @param action - the action command send to this ActionListener event
	 * @return the JMenuItem object representing this item
	 */
	private JMenuItem createMenuItem(String name, String action) {
		return createMenuItem(name, action, (char) 0);
	}

	/**
	 * Returns a JMenuItem with the given name and keyboard shortcut.
	 * @param name - the name of the item
	 * @param shortcut - the keyboard shortcut for triggering this item
	 * @return the JMenuItem object representing this item
	 */
	private JMenuItem createMenuItem(String name, char shortcut) {
		return createMenuItem(name, name, shortcut);
	}

	/**
	 * Returns a JMenuItem with the given name, action command, and keyboard shortcut.
	 * @param name - the name of the item
	 * @param action - the action command send to this ActionListener event
	 * @param shortcut - the keyboard shortcut for triggering this item
	 * @return the JMenuItem object representing this item
	 */
	private JMenuItem createMenuItem(String name, String action, char shortcut) {
		JMenuItem item = new JMenuItem(name);

		// Set up action listener
		item.addActionListener(this);
		item.setActionCommand(action);

		// Set the menu font as the font of this object
		item.setFont(font);

		// Set the keyboard shortcut for this item if there is one.
		if (shortcut > 0)
			item.setAccelerator(KeyStroke.getKeyStroke(shortcut, 
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// Return the menu item.
		return item;
	}

	/**
	 * Called when a menu item is clicked.
	 * @param event - the ActionEvent object representing the item that was clicked.
	 */
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		// Standard file commands
		if (command.equals("New")) newFile();
		else if (command.equals("Open")) openFile();
		else if (command.equals("Save")) saveFile();
		else if (command.equals("Save As")) saveFileAs();
		else if (command.equals("Quit")) quit();

		// Mesh commands
		else if (command.equals("Start Mesh")) {
			pseudocode.interpreter.startMesh();
			try {
				mesh.setText(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {}
		}
		else if (command.equals("Join Mesh")) pseudocode.interpreter.joinMesh();

		// Command to open an example
		else if (example.containsKey(command)) {
			openExample(command);
		}
	}

	/**
	 * Opens a new instance of the Pseudocode editor.
	 */
	private void newFile() {
		new Pseudocode();
	}

	/**
	 * Quits the current pseudocode editor.
	 */
	private void quit() {
		// TODO: check if work is unsaved
		pseudocode.dispatchEvent(new WindowEvent(pseudocode, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Use a JFileChooser object to select the file that should be opened.
	 */
	private void openFile() {
		// Create a JFileChooser with the file filter defined above.
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(pseudocodeFilter);

		// Get the result of an open dialog.
		int choice = chooser.showOpenDialog(pseudocode);

		// If the user selected a file to open, get the File object
		if (choice == JFileChooser.APPROVE_OPTION) {
			currentFile = chooser.getSelectedFile();

			// Check if this is a valid file, and if it is, update the pseudocode editor
			// with the String contents of the file.
			if (currentFile.exists()) {
				pseudocode.updateText(readFile(currentFile));
			}
		}
	}

	/**
	 * Saves the current file.
	 */
	private void saveFile() {
		if (currentFile != null) {
			if (! currentFile.exists()) {
				try {
					currentFile.createNewFile();
				} catch (IOException e) {}
			}
			
			// Write to the file.
			try {
				FileWriter fw = new FileWriter(currentFile.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(pseudocode.getText());
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else saveFileAs();
	}

	/**
	 * Saves the current file by asking the user to select a new file target.
	 */
	private void saveFileAs() {
		// Create a JFileChooser with the file filter defined above.
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(pseudocodeFilter);

		// Get the result of an open dialog.
		int choice = chooser.showSaveDialog(pseudocode);
		
		// If the user selected a file to open, get the File object
		if (choice == JFileChooser.APPROVE_OPTION) {
			currentFile = chooser.getSelectedFile();
			
			// Appends a .pseudo extension to the file
			if (! currentFile.getAbsolutePath().endsWith(".pseudo") &&
				! currentFile.getAbsolutePath().endsWith(".txt")) {
				currentFile = new File(currentFile.getAbsolutePath() + ".pseudo");
			}

			// Check if this is a valid file, and if it is, update the pseudocode editor
			// with the String contents of the file.
			if (! currentFile.exists()) {
				try {
					currentFile.createNewFile();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, "The file could not be created.");
					return;
				}
			}
			
			// Write the file to the given File object.
			try {
				FileWriter fw = new FileWriter(currentFile.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(pseudocode.getText());
				bw.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "The file could not be saved.");
			}
		}
	}

	/**
	 * Opens an example file.
	 * @param exampleName
	 */
	private void openExample(String exampleName) {
		// TODO: check for unsaved changes
		System.out.println(example.get(exampleName));
		pseudocode.updateText(example.get(exampleName));
	}
}
