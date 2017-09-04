package examples.ping;

import examples.ping.client.PingClient;

public class PingClientExample {
		
	private static final String LOCAL_IP = "127.0.0.1";
	private static final String INTERNAL_NETWORK_IP = "10.42.0.1";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PingClient client = new PingClient(LOCAL_IP, PingServerExample.PORT);
		client.connect();
	}

}
