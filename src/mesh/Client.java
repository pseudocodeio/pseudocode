package mesh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import view.Interpreter;

public class Client extends Thread {
	Interpreter interpreter;
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	String ip;
	boolean connected = false;
	public ConcurrentHashMap <String, ConcurrentHashMap <String, Double>> cache;

	public Client(Interpreter interpreter, String ip, int port) {
		try {
			interpreter.print("Connecting to " + ip);
			this.socket = new Socket(ip, port);
			this.ip = ip;
			
			cache = new ConcurrentHashMap <String, ConcurrentHashMap <String, Double>> ();
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			connected = true;
		} catch (IOException e) {}
	}

	public void run() {
		if (! connected) {
			interpreter.print("Could not connect to " + ip + ".");
			return;
		}
		try {
			while (true) {
				String line = input.readLine();
				if (line == null) {
					interpreter.print("Connection to " + ip + " closed.");
					break;
				}
				if (line.length() > 0) {
					// Get delimiters in the update string.
					int at = line.indexOf('@'), equals = line.indexOf('=');
					
					String name = line.substring(0, at);
					String key = line.substring(at + 1, equals);
					String value = line.substring(equals + 1);
					
					if (! cache.containsKey(name))
						cache.put(name, new ConcurrentHashMap <String, Double> ());
					
					
					cache.get(name).put(key, Double.parseDouble(value));
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
			//cache.put(key, value);
			//output.println(key + '=' + value);
		}
	}
	
	public static void parse(String line, ConcurrentHashMap <String, ConcurrentHashMap <String, Double>> cache) {
		if (line.length() > 0) {
			// Get delimiters in the update string.
			int at = line.indexOf('@'), equals = line.indexOf('=');
			if (at < 0 || equals < 0) return;
			
			// Get the name, key, and value for the output.
			String name = line.substring(0, at);
			String key = line.substring(at + 1, equals);
			String value = line.substring(equals + 1);
			
			if (! cache.containsKey(name))
				cache.put(name, new ConcurrentHashMap <String, Double> ());
			
			
			cache.get(name).put(key, Double.parseDouble(value));
		}
	}
}