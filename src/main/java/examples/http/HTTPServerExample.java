package examples.http;

import examples.http.server.HTTPServer;

public class HTTPServerExample {
	
	public static void main(String[] args) {
		HTTPServer server = new HTTPServer(8080);
		server.start();
		
		//Disable Firewalls
		//sudo service iptables stop
		//sudo service firewalld stop
		//Usage: curl -XGET localhost:8080
	}

}
