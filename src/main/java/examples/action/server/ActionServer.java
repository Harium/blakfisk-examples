package examples.action.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import examples.action.client.ActionClientProtocol;

public class ActionServer extends BlakFiskServer {

    private ActionServerProtocol listener;

    public ActionServer(int port) {
        super(port);

        name = "Action Server";

        listener = new ActionServerProtocol(ActionClientProtocol.PREFIX_ACTION, this);
        addProtocol(ActionClientProtocol.PREFIX_ACTION, listener);
        setHandshaker(new ActionHandshaker(this, listener.getPlayers()));
    }

    @Override
    public void joinPeer(Peer peer) {
        System.out.println("ActionPeer " + peer.getID() + " connected.");
        listener.addPeer(peer);
        System.out.println("Peers " + this.getConnections().length);
    }

    @Override
    public void leftPeer(Peer peer) {
        System.out.println("ActionPeer " + peer.getID() + " disconnected.");
        listener.removePeer(peer);
        System.out.println("Peers " + this.getConnections().length);
    }
}
