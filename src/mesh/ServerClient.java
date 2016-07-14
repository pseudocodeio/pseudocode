package mesh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ServerClient extends Thread {
	private Server server;
	private BufferedReader input;
	private PrintWriter output;
	private String ip;
	
	// Whether this connection is currently active.
	private boolean connected = false;
	
	String name;
	private boolean hasName = false;

	public ServerClient(Socket socket, Server server) {
		try {
			this.server = server;
			ip = socket.getInetAddress().getHostAddress();
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			connected = true;
		}
		catch (IOException e) {}
	}

	public void run() {
		if (! connected) return;

		// Copy all current data to client
		StringBuilder initialUpdate = new StringBuilder();
		for (String name : server.cache.keySet()) {
			for (String key : server.cache.get(name).keySet())
				initialUpdate
					.append(name).append('@')
					.append(key).append('=')
					.append(server.cache.get(name).get(key)).append('\n');
		}

		output.println(initialUpdate);

		// Keep reading updates from the client until disconnect, and update all other clients with updates
		try {
			while (connected) {
				String line = input.readLine();
				if (line == null) 
					break;
				else if (! hasName) {
					name = line;
					server.cache.put(name, new ConcurrentHashMap <String, Double> ());
				}
				else {
					// Get delimiters in the update string.
					int at = line.indexOf('@'), equals = line.indexOf('=');
					
					String name = line.substring(0, at);
					String key = line.substring(at + 1, equals);
					String value = line.substring(equals + 1);
					
					if (! server.cache.containsKey(name))
						server.cache.put(name, new ConcurrentHashMap <String, Double> ());
					
					
					server.cache.get(name).put(key, Double.parseDouble(value));
				}
			}

			input.close();
			output.close();

		} catch (IOException e) {}
	}

	public void put(String name, String key, double value) {
		if (connected) {
			output.println(name + '@' + key + '=' + value);
		}
	}
}