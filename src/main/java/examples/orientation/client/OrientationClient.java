package examples.orientation.client;

import com.harium.blakfisk.BlakFiskClient;
import examples.orientation.listener.OrientationListener;

public class OrientationClient extends BlakFiskClient {

	private OrientationClientProtocol actionProtocol;
	
	public OrientationClient(String ip, int tcpPort, OrientationListener listener) {
		super(ip, tcpPort);
		actionProtocol = new OrientationClientProtocol(listener, this);
		addProtocol(actionProtocol);
	}

	public OrientationClientProtocol getActionProtocol() {
		return actionProtocol;
	}
	
}
