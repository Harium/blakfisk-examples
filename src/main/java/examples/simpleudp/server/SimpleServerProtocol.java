package examples.simpleudp.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.common.StringServerProtocol;

public class SimpleServerProtocol extends StringServerProtocol {

    public SimpleServerProtocol(String prefix, BlakFiskServer server) {
        super(prefix, server);
    }

    private boolean receivedUdp = false;

    @Override
    public void receiveUDP(Peer peer, String msg) {
        System.out.println(getClass().getSimpleName() + " - Received UDP: " + msg);
        receivedUdp = true;

        sendTCPtoAll("Hallu " + peer.getID());
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {

    }

    public boolean receivedUdp() {
        return receivedUdp;
    }

}
