package examples.simpleudp;

import examples.simpleudp.client.HandShakeUDPClient;

public class SimpleClientUDPExample {
	
	public static final int PORT = 9907;
	private static final String LOCAL_IP = "127.0.0.1";
	
	public static void main(String[] args) {
		HandShakeUDPClient client = new HandShakeUDPClient(LOCAL_IP, PORT);
		client.connect();
		
		//Send messages every 1000ms
		client.sendMessages(1000);
	}

}
