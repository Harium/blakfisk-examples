package examples.topaction.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.HandshakerProtocol;
import examples.action.client.ActionClientProtocol;

import java.util.Set;

public class TopActionHandshaker extends HandshakerProtocol {

    private Set<Integer> players;

    public TopActionHandshaker(Set<Integer> players, BlakFiskServer server) {
        super(ActionClientProtocol.PREFIX_ACTION, server);
        this.players = players;
    }

    @Override
    public String handshakeText(Peer peer) {
        String message = ActionClientProtocol.PREFIX_INIT + " " + peer.getID() + " ";

        for (Integer player : players) {
            message += player + " ";
        }

        return message;
    }

}
