package examples.topaction;

import examples.action.server.ActionServer;
import examples.action.view.ActionClientApplication;

public class TopActionServerExample {
	
	public static void main(String[] args) {
		ActionServer server = new ActionServer(ActionClientApplication.PORT);
		server.start();
		
		//Disable Firewalls
		//sudo service iptables stop
		//sudo service firewalld stop
	}

}
