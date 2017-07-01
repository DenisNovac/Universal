package echoServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	private int port = 45831;
	private Socket socket;
	private BufferedReader inStream;
	private PrintWriter outStream;

	public Client() {
		try {
			socket = new Socket("localhost", port);
			System.out.println("Client is on. Knocking on "+port+" port");

			inStream = new BufferedReader ( new InputStreamReader (
				socket.getInputStream() )
			);
			System.out.println(".");

			outStream = new PrintWriter (
				new OutputStreamWriter (
					socket.getOutputStream() ),
				true	
			);

			ConnectionReader con = new ConnectionReader();
			con.start();
			System.out.println("Success");

			Scanner sc = new Scanner (System.in);
			String message;

			while (true) {
				message=sc.nextLine();
				if (message!="") {
					outStream.println(message);
				}
			}

		} catch (IOException ioexc) {
			System.out.println(ioexc);
		}

	} // end of Client constructor

	private class ConnectionReader extends Thread {

		public void run() {
			System.out.println("Connection to server is on");
			String message;
			try {
				while (( message=inStream.readLine() )!=null) {
					System.out.println(message);
				}
			} catch (IOException ioexc) {
				System.out.println(ioexc);
			}

		} // end of run


	} // end of ConnectionReader class

} // end of Client class