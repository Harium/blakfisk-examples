package examples.topaction.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import examples.action.client.ActionClientProtocol;

public class TopActionServer extends EtylServer {

	private TopActionServerProtocol listener;

	public TopActionServer(int port) {
		super(port);

		name = "Action Server";
				
		listener = new TopActionServerProtocol(ActionClientProtocol.PREFIX_ACTION, this);
		
		setHandshaker(new TopActionHandshaker(listener.getPlayers(), this));

		addProtocol(ActionClientProtocol.PREFIX_ACTION, listener);
	}
		
	@Override
	public void joinPeer(Peer peer) {
		System.out.println("ActionPeer "+peer.getId()+" connected.");
		
		listener.addPeer(peer);
	}
	
	@Override
	public void leftPeer(Peer peer) {
		System.out.println("Player "+peer.getId()+" disconnected.");
		
		listener.removePeer(peer);
	}
	
}
