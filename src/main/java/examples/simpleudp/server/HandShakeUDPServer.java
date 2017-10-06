package examples.simpleudp.server;


import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import examples.simpletcp.SimpleClientExample;
import examples.simpletcp.client.SimpleClientProtocol;
import examples.simpletcp.server.SimpleHandshaker;

public class HandShakeUDPServer extends EtylServer {

    private SimpleServerProtocol listener;

    public HandShakeUDPServer(int udpPort) {
        super(SimpleClientExample.PORT, udpPort);

        name = "HandShake UDP Server";

        setHandshaker(new SimpleHandshaker(this));
        listener = new SimpleServerProtocol(SimpleClientProtocol.DEFAULT_PREFIX, this);
        addProtocol(SimpleClientProtocol.DEFAULT_PREFIX, listener);
    }

    @Override
    public void joinPeer(Peer peer) {
        System.out.println("HandShakePeer " + peer.getId() + " connected.");
        listener.addPeer(peer);
    }

    @Override
    public void leftPeer(Peer peer) {
        System.out.println("Left: " + peer.getId());
        listener.removePeer(peer);
    }

}
