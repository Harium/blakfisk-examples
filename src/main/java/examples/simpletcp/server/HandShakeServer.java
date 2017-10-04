package examples.simpletcp.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import examples.simpletcp.client.SimpleClientProtocol;

public class HandShakeServer extends EtylServer {

	private SimpleServerProtocol listener;

	public HandShakeServer(int port, int udpPort) {
		this(port);
		this.udpPort = udpPort;
	}
	
	public HandShakeServer(int port) {
		super(port);
		
		name = "HandShake TCP Server";
		
		setHandshaker(new SimpleHandshaker(this));
		listener = new SimpleServerProtocol(SimpleClientProtocol.DEFAULT_PREFIX, this);
		addProtocol(SimpleClientProtocol.DEFAULT_PREFIX, listener);
	}
		
	@Override
	public void joinPeer(Peer peer) {
		System.out.println("Joined: "+peer.getId());
		listener.addPeer(peer);
	}

	@Override
	public void leftPeer(Peer peer) {
		System.out.println("Left: "+peer.getId());
		listener.removePeer(peer);
	}
	
}
