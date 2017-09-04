package examples.stream.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import examples.stream.client.StreamSenderProtocol;
import examples.stream.listener.StreamReceiverListener;

public class StreamReceiverServer extends BlakFiskServer {

	private StreamReceiverProtocol protocol;

	public StreamReceiverServer(int port) {
		super(port);
		
		//tcp.setCodec(new SingleEncoder());
		
		//tcp.setCodec(new RawEncoder());
		//tcp.setRecvBufferSize(64000);
		
		name = "Stream Server";
		protocol = new StreamReceiverProtocol(StreamSenderProtocol.PREFIX_STREAM_ACTION);
		setHandshaker(new StreamHandshaker(this));

		addProtocol(StreamSenderProtocol.PREFIX_STREAM_ACTION, protocol);
	}
		
	@Override
	public void joinPeer(Peer peer) {
		System.out.println("ActionPeer "+peer.getID()+" connected.");
		
		protocol.addPeer(peer);
	}
	
	@Override
	public void leftPeer(Peer peer) {
		System.out.println("Player "+peer.getID()+" disconnected.");
		
		protocol.removePeer(peer);
	}

	public StreamReceiverListener getListener() {
		return  protocol.getListener();
	}

	public void setListener(StreamReceiverListener listener) {
		protocol.setListener(listener);
	}

	public StreamReceiverProtocol getProtocol() {
		return protocol;
	}
	
}
