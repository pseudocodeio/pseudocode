package mesh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ServerClient extends Thread {
	private Server master;
	private BufferedReader input;
	private PrintWriter output;
	private String ip;
	private boolean connected = false;
	private static ConcurrentHashMap <String, Double> cache;

	public ServerClient(Socket socket, Server master) {
		try {
			this.master = master;
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
		for (String key : cache.keySet())
			initialUpdate.append(key).append('=').append(cache.get(key)).append('\n');

		output.println(initialUpdate);
		//Window.mesh.message("updated " + ip);

		// Keep reading updates from the client until disconnect, and update all other clients with updates
		try {
			while (true) {
				String line = input.readLine();
				if (line == null) 
					break;
				
				int equals = line.indexOf('=');
				String key = line.substring(0, equals);
				master.put(this, key, Double.parseDouble(line.substring(equals + 1)));
			}

			input.close();
			output.close();

		} catch (IOException e) {}
	}

	public void put(String key, int value) {
		if (connected) {
			output.println('#' + key + '=' + value);
		}
	}

	public void put(String key, double value) {
		if (connected) {
			output.println('%' + key + '=' + value);
		}
	}

	public void put(String key, String value) {
		if (connected) {
			output.println('$' + key + '=' + value);
		}
	}
}