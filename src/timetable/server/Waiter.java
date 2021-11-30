package timetable.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Waiter {
	int port;
	ServerSocket server;
	
	public Waiter(int portNumber) {
		port = portNumber;
	}
	
	public void start() throws IOException {
		server = new ServerSocket(port);
	}
	
	public Socket waitForConnection() throws IOException {
		return server.accept();
	}
}
