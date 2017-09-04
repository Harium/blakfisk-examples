package examples.simpletcp;

import examples.simpletcp.server.HandShakeServer;

public class SimpleServerExample {
	
	public static void main(String[] args) {
		HandShakeServer server = new HandShakeServer(SimpleClientExample.PORT);
		server.start();
		
		//Disable Firewalls
		//sudo service iptables stop
		//sudo service firewalld stop
	}

}
