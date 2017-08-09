import java.util.Scanner;

import client.SimpleNettyClient;
import server.SimpleNettyServer;

public class Main {
	final static int PORT=12432;
	final static String HOST="localhost";
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("1 for server and 2 for chat");
		int answer = sc.nextInt();
		
		switch(answer) {
			case 1: 
				new SimpleNettyServer(PORT);
				break;
			case 2: 
				new SimpleNettyClient(HOST,PORT);
				break;
		}
		
	}
}
