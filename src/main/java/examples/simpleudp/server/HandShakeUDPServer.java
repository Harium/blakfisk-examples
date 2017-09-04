package examples.simpleudp.server;


import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import examples.simpletcp.SimpleClientExample;
import examples.simpletcp.client.SimpleClientProtocol;
import examples.simpletcp.server.SimpleHandshaker;

public class HandShakeUDPServer extends BlakFiskServer {

    private SimpleServerProtocol listener;

    public HandShakeUDPServer(int udpPort) {
        super(SimpleClientExample.PORT, udpPort);

        name = "HandShake Server";

        setHandshaker(new SimpleHandshaker(this));
        listener = new SimpleServerProtocol(SimpleClientProtocol.DEFAULT_PREFIX, this);
        addProtocol(SimpleClientProtocol.DEFAULT_PREFIX, listener);
    }

    @Override
    public void joinPeer(Peer peer) {
        System.out.println("HandShakePeer " + peer.getID() + " connected.");
        listener.addPeer(peer);
    }

    @Override
    public void leftPeer(Peer peer) {
        System.out.println("Left: " + peer.getID());
        listener.removePeer(peer);
    }

}
