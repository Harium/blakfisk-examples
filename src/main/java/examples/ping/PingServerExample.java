package examples.ping;

import examples.ping.server.PingServer;

public class PingServerExample {
	
	public static final int PORT = 10991;
	
	public static void main(String[] args) {
		PingServer server = new PingServer(PORT);
		server.start();
		
		//Disable Firewalls
		//sudo service iptables stop
		//sudo service firewalld stop
	}

}
