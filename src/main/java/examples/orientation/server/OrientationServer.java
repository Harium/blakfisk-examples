package examples.orientation.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import examples.orientation.client.OrientationClientProtocol;

public class OrientationServer extends BlakFiskServer {

    private OrientationServerProtocol listener;

    public OrientationServer(int port) {
        super(port);

        name = "Orientation Server";
        listener = new OrientationServerProtocol(OrientationClientProtocol.PREFIX_ACTION, this);
        setHandshaker(new OrientationHandshaker(this, listener.getPlayers()));

        addProtocol(OrientationClientProtocol.PREFIX_ACTION, listener);
    }

    @Override
    public void joinPeer(Peer peer) {
        System.out.println("OrientationPeer " + peer.getID() + " connected.");

        listener.addPeer(peer);
    }

    @Override
    public void leftPeer(Peer peer) {
        System.out.println("Player " + peer.getID() + " disconnected.");

        listener.removePeer(peer);
    }

}
