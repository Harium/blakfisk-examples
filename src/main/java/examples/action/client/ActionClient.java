package examples.action.client;

import com.harium.blakfisk.BlakFiskClient;

public class ActionClient extends BlakFiskClient {

    private ActionClientProtocol actionProtocol;

    public ActionClient(String ip, int tcpPort, ActionClientListener listener) {
        super(ip, tcpPort);

        actionProtocol = new ActionClientProtocol(listener, this);
        addProtocol(actionProtocol);
    }

    public ActionClientProtocol getActionProtocol() {
        return actionProtocol;
    }

}
