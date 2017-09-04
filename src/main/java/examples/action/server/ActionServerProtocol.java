package examples.action.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.common.StringServerProtocol;
import examples.action.client.ActionClientProtocol;

import java.util.LinkedHashSet;
import java.util.Set;

public class ActionServerProtocol extends StringServerProtocol {

    private Set<Integer> players = new LinkedHashSet<Integer>();

    public ActionServerProtocol(String prefix, BlakFiskServer server) {
        super(prefix, server);
    }

    private boolean receivedTcp = false;

    @Override
    public void addPeer(Peer peer) {
        super.addPeer(peer);
        players.add(peer.getID());
        sendTCPtoAll(ActionClientProtocol.PREFIX_JOIN + " " + peer.getID());
    }

    public void removePeer(Peer peer) {
        super.removePeer(peer);
        players.remove(peer.getID());
        sendTCPtoAll(ActionClientProtocol.PREFIX_EXIT + " " + peer.getID());
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        // TODO Auto-generated method stub
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {
        System.out.println(getClass().getSimpleName() + " - Received TCP: " + msg);
        receivedTcp = true;

        sendTCPtoAll(peer.getID() + " " + msg);
    }

    public boolean receivedTcp() {
        return receivedTcp;
    }

    public Set<Integer> getPlayers() {
        return players;
    }

}
