package mesh;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import view.Interpreter;

/**
 * Thread for a server listening on the given port.
 */
public class Server extends Thread {
	Interpreter interpreter;
	int port;
	
	private ServerSocket master;
	private ArrayList <ServerClient> clients;
	public ConcurrentHashMap <String, ConcurrentHashMap <String, Double>> cache;

	/**
	 * Initialize this thread to listen on the given port.
	 * @param port
	 */
	public Server(Interpreter interpreter, int port) {
		this.interpreter = interpreter;
		this.port = port;
		this.cache = new ConcurrentHashMap <String, ConcurrentHashMap <String, Double>> ();
	}

	/**
	 * Starts listening on the given port.
	 */
	public void run() {
		try {
			master = new ServerSocket(port);
			clients = new ArrayList <ServerClient> ();
			interpreter.print("Starting mesh at " + InetAddress.getLocalHost().getHostAddress());

			// Keep listening for new clients
			while (true) {
				Socket newClient = master.accept();
				interpreter.print("User at " + newClient.getInetAddress().getHostAddress() + " joined the mesh.");
				
				ServerClient client = new ServerClient(newClient, this);
				synchronized(clients) {
					clients.add(client);
					client.start();
				}
			}
		}
		catch (IOException e) {
			interpreter.print("Could not start mesh server.");
		}
	}
}