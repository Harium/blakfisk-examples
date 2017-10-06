package examples.topaction.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.HandshakerProtocol;
import examples.action.client.ActionClientProtocol;

import java.util.Set;

public class TopActionHandshaker extends HandshakerProtocol {

    private Set<Integer> players;

    public TopActionHandshaker(Set<Integer> players, EtylServer server) {
        super(ActionClientProtocol.PREFIX_ACTION, server);
        this.players = players;
    }

    @Override
    public String buildHandshake(Peer peer) {
        String message = ActionClientProtocol.PREFIX_INIT + " " + peer.getId() + " ";

        for (Integer player : players) {
            message += player + " ";
        }

        return message;
    }

}
