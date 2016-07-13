package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mesh.Server;
import mesh.Client;
import expression.RGB;
import expression.Terminal;
import instruction.Block;

/**
 * An OutputPanel is a JPanel that draws the output of an algorithm.
 * 
 * @author  Keshav Saharia
 * 			keshav@techlabeducation.com
 *
 * @license MIT
 */
public class Interpreter extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_PORT = 4965;
	
	// Reference to the parent frame.
	private Pseudocode pseudocode;
	
	// The underlying algorithm being run by this output view.
	private Block block;
	private Console console;
	
	// Flag for resetting the output view.
	private boolean firstReset = true;
	private boolean reset = true;
	private boolean alwaysRepaint = true;
	private boolean printBlock = false;

	// BufferedImages for double buffering output
	private BufferedImage front;
	private BufferedImage back;
	private boolean frontBuffer = true;

	// Static values for storing the mouse and keyboard inputs
	public static Terminal mouseClicked = new Terminal();
	public static Terminal mouseX = new Terminal();
	public static Terminal mouseY = new Terminal();
	
	// Mesh data
	public static boolean meshRunning = false;

	// References to threads for master-slave network.
	private static Server server;
	private static Client client;
	private static boolean running = false;
	
	
	/**
	 * Constructs an OutputPanel object that will be displayed in the given PseudocodeFrame frame.
	 * @param pseudocode - the frame that the output panel would be placed in
	 */
	public Interpreter(Pseudocode pseudocode) {
		this.pseudocode = pseudocode;
		console = new Console(pseudocode);
		
		// Sets the width and height of the panel
		setSize(pseudocode.getWidth() / 2, pseudocode.getHeight());

		// Create a timer that continuously repaints the window
		Timer t = new Timer();
		// Schedule repaints at a fixed rate.
		t.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				repaint();
			}
		}, 0, 20);

		// Add mouse and key listeners
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		// Make the output frame visible
		setVisible(true);
	}

	public void setSize(int width, int height) {
		super.setSize(width, height);
		
		// Initialize the graphics buffers.
		front = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		back =  new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * Resets the buffers and double buffering.
	 */
	public void reset(Graphics g) {
		// Reset the graphics buffers to white
		front.getGraphics().setColor(Color.WHITE);
		front.getGraphics().fillRect(0, 0, getWidth(), getHeight());
		back.getGraphics().setColor(Color.WHITE);
		back.getGraphics().fillRect(0, 0, getWidth(), getHeight());
		
		frontBuffer = true;
		g.drawImage(front, 0, 0, null);
	}

	public void paint(Graphics g) {
		if (firstReset) {
			firstReset = false;
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if (reset) {
			reset = false;
			reset(g);
			//g.drawImage(front, 0, 0, null);
		}
		if (block != null) {
			// Get references to current buffer to draw on, and static buffer with previously drawn frame.
			BufferedImage currentBuffer = (frontBuffer) ? back : front;
			BufferedImage staticBuffer = (frontBuffer) ? front : back;
			
			currentBuffer.getGraphics().drawImage(staticBuffer, 0, 0, null);

			block.execute(currentBuffer.getGraphics(), block);
			g.drawImage(currentBuffer, 0, 0, null);

			frontBuffer = ! frontBuffer;			
		}
	}

	public void interpret(Block block) {
		// Initialize color palette.
		RGB.initialize();
		block.setConsole(console);
		console.reset();

		// Cancel repaint for equivalent program
		if (! alwaysRepaint && this.block != null && this.block.equals(block))
			return;

		// Assign the width and height values to the block.
		block.assign("width", new Terminal(getWidth()));
		block.assign("height", new Terminal(getHeight() - 50));
		block.assign("mousex", mouseX);
		block.assign("mousey", mouseY);
		block.assign("mouseclicked", mouseClicked);

		// Returns true if the block should be print out.
		if (printBlock) {
			print(block);
		}

		// Set the reset flag for the next repaint.
		block.reset();
		this.block = block;
		reset = true;
	}
	
	public void startMesh() {
		if (! meshRunning) {
			meshRunning = true;
//			int option = JOptionPane.showOptionDialog(this, "Use default port?", 
//					"Starting server", JOptionPane.YES_NO_CANCEL_OPTION, 0, null, null, JOptionPane.YES_OPTION);
			server = new Server(this, DEFAULT_PORT);
			server.start();
		}
	}
	
	public void joinMesh() {
		if (meshRunning) {
			if (client != null) {
				client.interrupt();
			}
			if (server != null) {
				server.interrupt();
			}
		}
		meshRunning = true;
		String ip = meshInput("Enter the server IP address.");
		int port = DEFAULT_PORT;
		if (ip.indexOf(':') > 0) {
			try {
				port = Integer.parseInt(ip.substring(ip.indexOf(':') + 1));
				
			} catch (NumberFormatException e) {};
		}
	}
	
	public String meshInput(String message) {
		return JOptionPane.showInputDialog(pseudocode, message);
	}
	
	public void meshAlert(String message) {
		JOptionPane.showMessageDialog(pseudocode, message);
	}
	
	private void print(Block block) {
		System.out.println("public class Pseudocode {");
		for (String symbol : block.getSymbolTable().keySet()) {
			System.out.print("static double ");
			System.out.print(symbol);
			System.out.print(" = ");
			System.out.print(block.get(symbol));
			System.out.print(";\n");
		}
		System.out.print("public static void main(String[] args) ");
		System.out.println(block);
		System.out.println("}");
	}

	public void mouseMoved(MouseEvent e) {
		mouseX.setValue(e.getX());
		mouseY.setValue(e.getY());
		if (block != null) {
			block.assign("mousex", mouseX);
			block.assign("mousey", mouseY);
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		mouseClicked.setValue(1);
		if (block != null)
			block.assign("mouseclicked", mouseClicked);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseClicked.setValue(0);
		if (block != null)
			block.assign("mouseclicked", mouseClicked);
	}

	public void mouseClicked(MouseEvent e) {
		requestFocus();
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (assignKey(e.getKeyCode(), 1)) return;
		if (block != null)
			block.assign(e.getKeyChar() + " pressed", 1);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (assignKey(e.getKeyCode(), 0)) return;
		
		if (block != null)
			block.assign(e.getKeyChar() + " pressed", 0);
	}
	
	private boolean assignKey(int keyEvent, int value) {
		if (keyEvent == KeyEvent.VK_UP)
			block.assign("up pressed", value);
		else if (keyEvent == KeyEvent.VK_DOWN)
			block.assign("down pressed", value);
		else if (keyEvent == KeyEvent.VK_LEFT)
			block.assign("left pressed", value);
		else if (keyEvent == KeyEvent.VK_RIGHT)
			block.assign("right pressed", value);
		else if (keyEvent == KeyEvent.VK_SPACE)
			block.assign("space pressed", value);
		else
			return false;
		
		return true;
	}
}
