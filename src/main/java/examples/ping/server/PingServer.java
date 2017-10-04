package examples.ping.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import examples.ping.client.PingClientProtocol;

public class PingServer extends EtylServer {

    private PingServerProtocol listener;

    public PingServer(int port) {
        super(port);
        name = "Ping Server";

        setHandshaker(new PingHandshaker(this));
        listener = new PingServerProtocol(PingClientProtocol.PREFIX_PING, this);
        addProtocol(PingClientProtocol.PREFIX_PING, listener);
    }

    @Override
    public void joinPeer(Peer peer) {
        System.out.println("PingPeer " + peer.getId() + " connected.");
        listener.addPeer(peer);
    }

    @Override
    public void leftPeer(Peer peer) {
        System.out.println("PingPeer " + peer.getId() + " disconnected.");
        listener.removePeer(peer);
    }

}
