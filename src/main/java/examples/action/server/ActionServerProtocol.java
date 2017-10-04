package examples.action.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.common.StringServerProtocol;
import examples.action.client.ActionClientProtocol;

import java.util.LinkedHashSet;
import java.util.Set;

public class ActionServerProtocol extends StringServerProtocol {

    private Set<Integer> players = new LinkedHashSet<Integer>();

    public ActionServerProtocol(String prefix, EtylServer server) {
        super(prefix, server);
    }

    private boolean receivedTcp = false;

    @Override
    public void addPeer(Peer peer) {
        super.addPeer(peer);
        players.add(peer.getId());
        sendTCPtoAll(ActionClientProtocol.PREFIX_JOIN + " " + peer.getId());
    }

    public void removePeer(Peer peer) {
        super.removePeer(peer);
        players.remove(peer.getId());
        sendTCPtoAll(ActionClientProtocol.PREFIX_EXIT + " " + peer.getId());
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        // TODO Auto-generated method stub
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {
        System.out.println(getClass().getSimpleName() + " - Received TCP: " + msg);
        receivedTcp = true;

        sendTCPtoAll(peer.getId() + " " + msg);
    }

    public boolean receivedTcp() {
        return receivedTcp;
    }

    public Set<Integer> getPlayers() {
        return players;
    }

}
