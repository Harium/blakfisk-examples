package examples.room.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import examples.room.client.RoomClientProtocol;

public class RoomServer extends EtylServer {

	private RoomServerProtocol protocol;

	public RoomServer(int port) {
		super(port);

		name = "Room Server";
				
		protocol = new RoomServerProtocol(RoomClientProtocol.PREFIX_ROOM_ACTION, this);
		setHandshaker(new RoomHandshaker(protocol, this));

		addProtocol(RoomClientProtocol.PREFIX_ROOM_ACTION, protocol);
	}
	
	@Override
	public void start() {
		super.start();
	}
	
	@Override
	public void joinPeer(Peer peer) {
		System.out.println("ActionPeer "+peer.getId()+" connected.");
		
		protocol.addPeer(peer);
	}
	
	@Override
	public void leftPeer(Peer peer) {
		System.out.println("Player "+peer.getId()+" disconnected.");
		
		protocol.removePeer(peer);
	}
	
}
