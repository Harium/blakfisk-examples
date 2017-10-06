package examples.websocket.server;

import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.websocket.EtylWebSocketServer;

public class EchoWebSocketServer extends EtylWebSocketServer {

    public EchoWebSocketServer(int port) {
        super(port);
    }

    @Override
    public void joinPeer(Peer peer) {
        System.out.println("User " + peer.getId() + " joined.");
    }

    @Override
    public void leftPeer(Peer peer) {
        System.out.println("User " + peer.getId() + " left.");
    }

}
