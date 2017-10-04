package examples.topaction.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.common.StringServerProtocol;
import examples.topaction.client.TopActionClientProtocol;
import examples.topaction.model.PlayerState;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TopActionServerProtocol extends StringServerProtocol {

    private Set<Integer> players = new LinkedHashSet<Integer>();
    private Map<Integer, PlayerState> playerState = new HashMap<Integer, PlayerState>();

    public TopActionServerProtocol(String prefix, EtylServer server) {
        super(prefix, server);
    }

    @Override
    public void addPeer(Peer peer) {
        super.addPeer(peer);
        players.add(peer.getId());
        playerState.put(peer.getId(), new PlayerState(32, 32));
        sendTCPtoAll(TopActionClientProtocol.PREFIX_JOIN + " " + peer.getId());
    }

    public void removePeer(Peer peer) {
        super.removePeer(peer);
        players.remove(peer.getId());
        playerState.remove(peer.getId());
        sendTCPtoAll(TopActionClientProtocol.PREFIX_EXIT + " " + peer.getId());
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {
        System.out.println(getClass().getSimpleName() + " - Received TCP: " + msg);

        if (msg.startsWith(TopActionClientProtocol.PREFIX_ANGLE)) {
            PlayerState state = playerState.get(peer.getId());
            double angle = Double.parseDouble(msg.split(" ")[1]);
            state.angle = angle;
        } else if (msg.startsWith(TopActionClientProtocol.PREFIX_STATE)) {
            PlayerState s = playerState.get(peer.getId());
            int state = Integer.parseInt(msg.split(" ")[1]);
            s.state = state;
        } else if (msg.startsWith(TopActionClientProtocol.PREFIX_CHANGE_WEAPON)) {
            PlayerState state = playerState.get(peer.getId());
            int weapon = Integer.parseInt(msg.split(" ")[1]);
            state.weapon = weapon;
        }

        sendTCPtoAll(peer.getId() + " " + msg);
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        // TODO Auto-generated method stub
    }

    public Set<Integer> getPlayers() {
        return players;
    }

}
