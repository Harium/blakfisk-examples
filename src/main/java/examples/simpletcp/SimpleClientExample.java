package examples.simpletcp;

import examples.simpletcp.client.HandShakeClient;

public class SimpleClientExample {

	public static final int PORT = 9906;

	private static final String LOCAL_IP = "127.0.0.1";
	private static final String INTERNAL_NETWORK_IP = "10.42.0.1";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HandShakeClient client = new HandShakeClient(LOCAL_IP, PORT);
		client.connect();

		//Send messages every 1000ms
		client.sendMessages(1000);
	}

}
