package examples.action.client;


import com.harium.etyl.networking.EtylClient;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.common.StringClientProtocol;
import com.harium.etyl.networking.util.ByteMessageUtils;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.etyl.commons.event.PointerEvent;

public class ActionClientProtocol extends StringClientProtocol {

    private ActionClientListener listener;

    public static final String PREFIX_ACTION = "/a";

    public static final String PREFIX_INIT = "i";
    public static final String PREFIX_JOIN = "j";
    public static final String PREFIX_EXIT = "q";

    public ActionClientProtocol(ActionClientListener listener, EtylClient client) {
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
            String prefix = msg.split(" ")[0];
            int id = Integer.parseInt(prefix);
            String message = new String(ByteMessageUtils.wipePrefix(prefix.getBytes(), msg.getBytes()));
            listener.receiveMessage(id, message);
        }
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        // TODO Auto-generated method stub

    }

}
