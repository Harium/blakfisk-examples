package examples.ping.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.common.StringServerProtocol;
import examples.ping.client.PingClientProtocol;

public class PingServerProtocol extends StringServerProtocol {

    public PingServerProtocol(String prefix, BlakFiskServer server) {
        super(prefix, server);
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        String prefix = msg.split(" ")[0];
        if (prefix.equals(PingClientProtocol.PREFIX_PING)) {
            sendUDP(peer, PingClientProtocol.PREFIX_PONG + " " + System.currentTimeMillis());
        } else {
            sendUDP(peer, ":P " + System.currentTimeMillis());
        }
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {

    }

}
