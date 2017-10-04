package examples.ping.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.HandshakerProtocol;
import examples.ping.client.PingClientProtocol;

public class PingHandshaker extends HandshakerProtocol {

    public PingHandshaker(EtylServer server) {
        super(PingClientProtocol.PREFIX_PING, server);
    }

    @Override
    public String buildHandshake(Peer peer) {
        return peer.getId() + " " + System.currentTimeMillis();
    }

}
