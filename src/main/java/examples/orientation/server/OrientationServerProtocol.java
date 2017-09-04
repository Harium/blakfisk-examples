package examples.orientation.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.common.StringServerProtocol;
import examples.orientation.client.OrientationClientProtocol;
import examples.orientation.model.PlayerOrientation;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class OrientationServerProtocol extends StringServerProtocol {

    private Set<Integer> players = new LinkedHashSet<Integer>();
    private Map<Integer, PlayerOrientation> states = new HashMap<Integer, PlayerOrientation>();

    public OrientationServerProtocol(String prefix, BlakFiskServer server) {
        super(prefix, server);
    }

    @Override
    public void addPeer(Peer peer) {
        super.addPeer(peer);
        int uniqueId = peer.getID();

        players.add(uniqueId);
        states.put(uniqueId, new PlayerOrientation());
        //sendTCPtoAll(ActionClientProtocol.PREFIX_JOIN+" "+peer.getSessionID());
        sendTCPtoAllExcept(peer, OrientationClientProtocol.PREFIX_JOIN + " " + peer.getID());
    }

    public void removePeer(Peer peer) {
        super.removePeer(peer);
        players.remove(peer.getID());
        sendTCPtoAll(OrientationClientProtocol.PREFIX_EXIT + " " + peer.getID());
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        String action = msg;

        if (OrientationClientProtocol.ACTION_ROTATION.equals(action)) {
            String[] parts = action.split(" ");

            Double azimuth = Double.parseDouble(parts[1]);
            Double pitch = Double.parseDouble(parts[2]);
            Double roll = Double.parseDouble(parts[3]);

            PlayerOrientation state = states.get(peer.getID());
            state.yaw = azimuth;
            state.pitch = pitch;
            state.roll = roll;
        }
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {
        System.out.println(getClass().getSimpleName() + " - Received TCP: " + msg);

        String action = msg;

        if (OrientationClientProtocol.ACTION_CLICK.equals(action)) {
            //PlayerOrientation state = states.get(peer.getSessionID());
            //notifyListener about the click
            System.out.println(peer.getID() + " clicked");
        } else if (OrientationClientProtocol.ACTION_START_LONG_CLICK.equals(action)) {
            PlayerOrientation state = states.get(peer.getID());
            state.isLongClick = true;
            System.out.println(peer.getID() + " startedLongClick");
        } else if (OrientationClientProtocol.ACTION_STOP_LONG_CLICK.equals(action)) {
            PlayerOrientation state = states.get(peer.getID());
            state.isLongClick = false;
            System.out.println(peer.getID() + " startedLongClick");
        }

        //sendTCPtoAll(peer.getSessionID()+" "+msg);
    }

    public Set<Integer> getPlayers() {
        return players;
    }

}
