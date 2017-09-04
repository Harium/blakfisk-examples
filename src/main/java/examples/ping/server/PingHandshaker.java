package examples.ping.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.HandshakerProtocol;
import examples.ping.client.PingClientProtocol;

public class PingHandshaker extends HandshakerProtocol {

    public PingHandshaker(BlakFiskServer server) {
        super(PingClientProtocol.PREFIX_PING, server);
    }

    @Override
    public String handshakeText(Peer peer) {
        return peer.getID() + " " + System.currentTimeMillis();
    }

}
