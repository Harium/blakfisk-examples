package examples.ping.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.common.StringServerProtocol;
import examples.ping.client.PingClientProtocol;

public class PingServerProtocol extends StringServerProtocol {

    public PingServerProtocol(String prefix, EtylServer server) {
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
