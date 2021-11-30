package spark;

import java.io.IOException;
import java.net.Socket;

import timetable.server.Controller;
import timetable.server.Waiter;
import timetable.server.Worker;

public class Server{
	
	public static final int port = 8000;
	
	public static void main(String[] args) {
		System.out.println("this is server");
		Waiter TimeTableSortingServer = new Waiter(port);
		try {
			TimeTableSortingServer.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true) {
			try {
				//get socket
				Socket client = TimeTableSortingServer.waitForConnection();
				//thread
				Controller childServer = new Controller();
				childServer.socket = client;
				Thread t = new Thread(childServer);
				t.start();
				System.out.println("thread started");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
