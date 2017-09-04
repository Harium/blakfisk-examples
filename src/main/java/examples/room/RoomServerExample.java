package examples.room;

import examples.room.server.RoomServer;
import examples.room.view.RoomClientApplication;

public class RoomServerExample {
	
	//TODO This example is not working
	//All peers are receiving the same events
	//The correct should be separated by rooms
	
	public static void main(String[] args) {
		RoomServer server = new RoomServer(RoomClientApplication.PORT);
		server.start();
		
		//Disable Firewalls
		//sudo service iptables stop
		//sudo service firewalld stop
	}

}
