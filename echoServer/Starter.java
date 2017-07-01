package echoServer;

public class Starter {
	public static void main(String[] args) {
		String trigger = args[0];

		switch(trigger) {
			case "0":
				new Server();
				break;
			case "1":
				new Client();
				break;
			default:
				break;
		}


	}
}