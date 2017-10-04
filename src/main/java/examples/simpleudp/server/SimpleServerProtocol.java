package examples.simpleudp.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.common.StringServerProtocol;

public class SimpleServerProtocol extends StringServerProtocol {

    public SimpleServerProtocol(String prefix, EtylServer server) {
        super(prefix, server);
    }

    private boolean receivedUdp = false;

    @Override
    public void receiveUDP(Peer peer, String msg) {
        System.out.println(getClass().getSimpleName() + " - Received UDP: " + msg);
        receivedUdp = true;

        sendUDPtoAll("Hallu " + peer.getId());
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {

    }

    public boolean receivedUdp() {
        return receivedUdp;
    }

}
