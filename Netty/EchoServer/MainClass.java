import java.util.Scanner;
import client.Client;
import server.DiscardServer;


public class MainClass {
	final static int port = 9090;
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("1 for server and 2 for client");
		int sw = sc.nextInt();
		
		switch(sw) {
			case 1:
				new DiscardServer(port);
				break;
			case 2:
				new Client(port);
				break;
			default:
				break;
		}
		
		
		
		sc.close();
	}
}
