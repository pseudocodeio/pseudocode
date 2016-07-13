package mesh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import view.Interpreter;

public class Client extends Thread {
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	String ip;
	boolean connected = false;
	private ConcurrentHashMap <String, Double> cache;

	public Client(Interpreter interpreter, String ip, int port) {
		try {
			this.socket = new Socket(ip, port);
			this.ip = ip;
			
			cache = new ConcurrentHashMap <String, Double> ();
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			connected = true;
		} catch (IOException e) {}
	}

	public void run() {
		if (! connected) {
			System.err.println("Could not connect to " + ip + ".");
			return;
		}
		try {
			while (true) {
				String line = input.readLine();
				if (line == null) {
					System.err.println("Connection to " + ip + " closed.");
					break;
				}
				if (line.length() > 0) {
					int equals = line.indexOf('=');
					String key = line.substring(0, equals);
					cache.put(key, Double.parseDouble(line.substring(equals + 1)));
				}
			}
			connected = false;
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {}
	}

	public void put(String key, double value) {
		if (connected && key != null) {
			cache.put(key, value);
			output.println(key + '=' + value);
		}
	}
}