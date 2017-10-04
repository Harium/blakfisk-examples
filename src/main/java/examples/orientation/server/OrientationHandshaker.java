package examples.orientation.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.HandshakerProtocol;
import examples.action.client.ActionClientProtocol;

import java.util.Set;

public class OrientationHandshaker extends HandshakerProtocol {

    private Set<Integer> players;

    public OrientationHandshaker(EtylServer server, Set<Integer> players) {
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
