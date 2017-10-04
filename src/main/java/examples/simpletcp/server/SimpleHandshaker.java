package examples.simpletcp.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.HandshakerProtocol;
import examples.simpletcp.client.SimpleClientProtocol;

public class SimpleHandshaker extends HandshakerProtocol {

    public SimpleHandshaker(EtylServer server) {
        super(SimpleClientProtocol.DEFAULT_PREFIX, server);
    }

    @Override
    public String buildHandshake(Peer peer) {
        return "Hello, player " + peer.getId() + "!";
    }

}
