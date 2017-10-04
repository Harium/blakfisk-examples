package examples.orientation.client;

import com.harium.etyl.networking.EtylClient;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.common.StringClientProtocol;
import examples.orientation.listener.OrientationListener;
import examples.orientation.model.PlayerOrientation;

public class OrientationClientProtocol extends StringClientProtocol {
    private OrientationListener listener;

    public static final String PREFIX_ACTION = "/a";

    public static final String PREFIX_INIT = "i";
    public static final String PREFIX_JOIN = "j";
    public static final String PREFIX_EXIT = "q";

    public static final String ACTION_ROTATION = "r";
    public static final String ACTION_CLICK = "clk";
    public static final String ACTION_START_LONG_CLICK = "slc";
    public static final String ACTION_STOP_LONG_CLICK = "plc";

    public OrientationClientProtocol(OrientationListener listener, EtylClient client) {
        super(PREFIX_ACTION, client);
        this.listener = listener;
    }

    public void sendClick() {
        sendTCP(ACTION_CLICK);
    }

    public void sendStartLongClick() {
        sendTCP(ACTION_START_LONG_CLICK);
    }

    public void sendStopLongClick() {
        sendTCP(ACTION_STOP_LONG_CLICK);
    }

    public void sendRotation(double azimuth, double pitch, double roll) {
        String message = ACTION_ROTATION
                + " " + Double.toString(azimuth)
                + " " + Double.toString(pitch)
                + " " + Double.toString(roll);

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
            String id = msg.split(" ")[1];
            listener.joinedClient(id);
        } else if (msg.startsWith(PREFIX_EXIT)) {
            String id = msg.split(" ")[1];
            listener.exitClient(id);
        } else if (msg.startsWith(ACTION_ROTATION)) {
            String id = msg.split(" ")[1];

            double yaw = Float.parseFloat(msg.split(" ")[2]);
            double pitch = Float.parseFloat(msg.split(" ")[3]);
            double roll = Float.parseFloat(msg.split(" ")[4]);

            PlayerOrientation orientation = new PlayerOrientation(yaw, pitch, roll);
            listener.rotate(id, orientation);
        } else {
            String id = msg.split(" ")[0];
            String message = msg.split(" ")[1];
            listener.receiveMessage(id, message);
        }
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        // TODO Auto-generated method stub

    }

}
