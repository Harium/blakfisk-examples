package examples.ping.client;

import com.harium.blakfisk.BlakFiskClient;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.common.StringClientProtocol;

public class PingClientProtocol extends StringClientProtocol {

    public static final String PREFIX_PING = "ping";
    public static final String PREFIX_PONG = "PONG";

    public PingClientProtocol(BlakFiskClient client) {
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
