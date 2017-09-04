package examples.orientation;

import examples.orientation.server.OrientationServer;
import examples.orientation.view.OrientationClientApplication;

public class RotationVectorServerExample {
	
	public static void main(String[] args) {
		OrientationServer server = new OrientationServer(OrientationClientApplication.PORT);
		server.start();
		
		//Disable Firewalls
		//sudo service iptables stop
		//sudo service firewalld stop
	}

}
