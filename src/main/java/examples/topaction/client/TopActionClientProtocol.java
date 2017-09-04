package examples.topaction.client;

import com.harium.blakfisk.BlakFiskClient;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.common.StringClientProtocol;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.etyl.commons.event.PointerEvent;
import examples.action.client.ActionClientListener;

public class TopActionClientProtocol extends StringClientProtocol {

    private ActionClientListener listener;

    public static final String PREFIX_ACTION = "/a";

    public static final String PREFIX_INIT = "i";
    public static final String PREFIX_JOIN = "j";
    public static final String PREFIX_EXIT = "q";

    public static final String PREFIX_ANGLE = "n";
    public static final String PREFIX_STATE = "s";
    public static final String PREFIX_CHANGE_WEAPON = "w";

    public TopActionClientProtocol(ActionClientListener listener, BlakFiskClient client) {
        super(PREFIX_ACTION, client);
        this.listener = listener;
    }

    public void sendKeyEvent(KeyEvent event) {
        String message = event.getState().toString()
                + " " + Integer.toString(event.getKey());

        sendTCP(message);
    }

    public void sendPointerEvent(PointerEvent event) {
        String message = event.getState().toString()
                + " " + Integer.toString(event.getX())
                + " " + Integer.toString(event.getY());

        sendTCP(message);
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {

        if (msg.startsWith(PREFIX_INIT)) {
            //HandShake message
            String crop = msg.substring((PREFIX_INIT + " ").length());
            String[] ids = crop.split(" ");
            listener.init(ids);
        } else if (msg.startsWith(PREFIX_JOIN)) {
            int id = Integer.parseInt(msg.split(" ")[1]);
            listener.joinedClient(id);
        } else if (msg.startsWith(PREFIX_EXIT)) {
            int id = Integer.parseInt(msg.split(" ")[1]);
            listener.exitClient(id);
        } else {
            int id = Integer.parseInt(msg.split(" ")[0]);
            String message = msg.split(" ")[1];
            listener.receiveMessage(id, message);
        }
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        // TODO Auto-generated method stub

    }

    public void sendState(int state) {
        sendTCP(PREFIX_STATE + " " + state);
    }

    public void sendAngle(double angle) {
        sendTCP(PREFIX_ANGLE + " " + angle);
    }

    public void sendWeapon(int weapon) {
        sendTCP(PREFIX_CHANGE_WEAPON + " " + weapon);
    }

}
