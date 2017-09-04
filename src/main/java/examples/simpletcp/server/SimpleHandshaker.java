package examples.simpletcp.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.HandshakerProtocol;
import examples.simpletcp.client.SimpleClientProtocol;

public class SimpleHandshaker extends HandshakerProtocol {

    public SimpleHandshaker(BlakFiskServer server) {
        super(SimpleClientProtocol.DEFAULT_PREFIX, server);
    }

    @Override
    public String handshakeText(Peer peer) {
        return " Hello, player " + peer.getID() + "!";
    }

}
