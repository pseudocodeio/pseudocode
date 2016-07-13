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
	private Interpreter interpreter;
	private int port;
	
	private ServerSocket master;
	private ArrayList <ServerClient> clients;
	private static ConcurrentHashMap <String, Double> cache;

	/**
	 * Initialize this thread to listen on the given port.
	 * @param port
	 */
	public Server(Interpreter interpreter, int port) {
		this.interpreter = interpreter;
		this.port = port;
	}

	/**
	 * Starts listening on the given port.
	 */
	public void run() {
		try {
			master = new ServerSocket(port);
			clients = new ArrayList <ServerClient> ();
			System.out.println("Starting mesh at " + InetAddress.getLocalHost().getHostAddress() + ", port " + port);

			// Keep listening for new clients
			while (true) {
				Socket newClient = master.accept();
				//Window.mesh.message("connection from " + newClient.getInetAddress().getHostAddress());

				ServerClient client = new ServerClient(newClient, this);
				synchronized(clients) {
					clients.add(client);
					client.start();
				}
			}
		}
		catch (IOException e) {
			//error("Could not create server at port " + port);
		}
	}
	
	/**
	 * 
	 * @param client - the client that is sending the new value, or null if it is originating from the server.
	 * @param key - unique ID of the data
	 * @param value - the integer value
	 */
	public void put(ServerClient client, String key, double value) {
		cache.put(key, value);
		synchronized(clients) {
			for (ServerClient c : clients) {
				if (c != client) {
					c.put(key, value);
				}
			}
		}
	}
}