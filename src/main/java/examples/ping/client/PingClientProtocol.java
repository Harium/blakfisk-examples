package examples.ping.client;

import com.harium.etyl.networking.EtylClient;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.common.StringClientProtocol;

public class PingClientProtocol extends StringClientProtocol {

    public static final String PREFIX_PING = "ping";
    public static final String PREFIX_PONG = "PONG";

    public PingClientProtocol(EtylClient client) {
        super(PREFIX_PING, client);
    }

    public void sendPing() {
        sendUDP(PREFIX_PING + " Hello");
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {
        // TODO Auto-generated method stub
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        System.out.println("Received UDP: " + msg);
    }

}
