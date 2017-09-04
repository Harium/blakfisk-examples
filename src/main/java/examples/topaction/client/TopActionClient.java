package examples.topaction.client;

import com.harium.blakfisk.BlakFiskClient;
import examples.action.client.ActionClientListener;

public class TopActionClient extends BlakFiskClient {

    private TopActionClientProtocol actionProtocol;

    public TopActionClient(String ip, int tcpPort, ActionClientListener listener) {
        super(ip, tcpPort);

        actionProtocol = new TopActionClientProtocol(listener, this);
        addProtocol(actionProtocol);
    }

    public TopActionClientProtocol getActionProtocol() {
        return actionProtocol;
    }

}
