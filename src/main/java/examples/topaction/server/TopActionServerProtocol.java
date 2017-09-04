package examples.topaction.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.common.StringServerProtocol;
import examples.topaction.client.TopActionClientProtocol;
import examples.topaction.model.PlayerState;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TopActionServerProtocol extends StringServerProtocol {

    private Set<Integer> players = new LinkedHashSet<Integer>();
    private Map<Integer, PlayerState> playerState = new HashMap<Integer, PlayerState>();

    public TopActionServerProtocol(String prefix, BlakFiskServer server) {
        super(prefix, server);
    }

    @Override
    public void addPeer(Peer peer) {
        super.addPeer(peer);
        players.add(peer.getID());
        playerState.put(peer.getID(), new PlayerState(32, 32));
        sendTCPtoAll(TopActionClientProtocol.PREFIX_JOIN + " " + peer.getID());
    }

    public void removePeer(Peer peer) {
        super.removePeer(peer);
        players.remove(peer.getID());
        playerState.remove(peer.getID());
        sendTCPtoAll(TopActionClientProtocol.PREFIX_EXIT + " " + peer.getID());
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {
        System.out.println(getClass().getSimpleName() + " - Received TCP: " + msg);

        if (msg.startsWith(TopActionClientProtocol.PREFIX_ANGLE)) {
            PlayerState state = playerState.get(peer.getID());
            double angle = Double.parseDouble(msg.split(" ")[1]);
            state.angle = angle;
        } else if (msg.startsWith(TopActionClientProtocol.PREFIX_STATE)) {
            PlayerState s = playerState.get(peer.getID());
            int state = Integer.parseInt(msg.split(" ")[1]);
            s.state = state;
        } else if (msg.startsWith(TopActionClientProtocol.PREFIX_CHANGE_WEAPON)) {
            PlayerState state = playerState.get(peer.getID());
            int weapon = Integer.parseInt(msg.split(" ")[1]);
            state.weapon = weapon;
        }

        sendTCPtoAll(peer.getID() + " " + msg);
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        // TODO Auto-generated method stub
    }

    public Set<Integer> getPlayers() {
        return players;
    }

}
