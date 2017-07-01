package echoServer;

import java.util.ArrayList;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Server {
	private static ArrayList<Connection> connections = 
	new ArrayList<Connection>();

	public Server() {
		int port=45831;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server is on");
			while (true) {
				Socket socket = serverSocket.accept(); // метод ждёт подключения
				Connection user = new Connection(socket);
				connections.add(user);
				user.start();
			}
			
		} catch (IOException ioexc) {
			System.out.println(ioexc);
			System.exit(-1);
		}
	}



	private class Connection extends Thread {
		private Socket socket;
		private BufferedReader inStream;
		private PrintWriter outStream;

		Connection(Socket socket) {
			this.socket=socket;
			// Создаём потоки для принятого соединения
			try {
				inStream = new BufferedReader( new InputStreamReader (
				socket.getInputStream() ));

				outStream = new PrintWriter (
					new OutputStreamWriter (
						socket.getOutputStream() ),
					true
				);

			} catch (IOException ioexc) {
				System.out.println(ioexc);
			}
			
		} // end of Connection constructor

		public void run() {
			System.out.println("User is connected");
			String message;
			try {
				while ( (message=inStream.readLine() )!=null ) {

					System.out.println(message);

					for (Connection c : connections) {
						c.outStream.println(message);
					}

				}
			} catch (IOException ioexc) {
				System.out.println(ioexc);
			}

		} // end of run method

	} // end of Connection class

}