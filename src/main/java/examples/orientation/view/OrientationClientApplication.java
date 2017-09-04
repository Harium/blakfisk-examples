package examples.orientation.view;

import com.harium.etyl.commons.context.Application;
import com.harium.etyl.commons.context.UpdateIntervalListener;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.etyl.commons.event.MouseEvent;
import com.harium.etyl.commons.event.PointerEvent;
import com.harium.etyl.core.graphics.Graphics;
import examples.orientation.client.OrientationClient;
import examples.orientation.client.OrientationClientProtocol;
import examples.orientation.listener.OrientationListener;
import examples.orientation.model.PlayerOrientation;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class OrientationClientApplication extends Application implements OrientationListener, UpdateIntervalListener {
    private String me = "";

    private OrientationClient client;
    private OrientationClientProtocol protocol;

    public static final int PORT = 16777;
    private static final String LOCAL_IP = "127.0.0.1";

    private Map<String, String> messages = new LinkedHashMap<String, String>();

    //Player State
    private double azimuth = 1;
    private double pitch = 2;
    private double roll = 3;
    private boolean isLongClick = false;

    public OrientationClientApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {
        client = new OrientationClient(LOCAL_IP, PORT, this);
        client.connect();

        protocol = client.getActionProtocol();

        updateAtFixedRate(50, this);

        loading = 100;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        //Write msgs
        int i = 0;
        for (Entry<String, String> entry : messages.entrySet()) {
            String id = entry.getKey();
            String message = entry.getValue();

            int x = 60;
            int y = 120 + 20 * i;

            g.drawString(id + ": " + message, x, y);

            if (id.equals(me)) {
                g.fillRect(x - 20, y - 10, 14, 14);
            }
            i++;
        }
    }

    @Override
    public void updateKeyboard(KeyEvent event) {
        if (event.isKeyDown(KeyEvent.VK_RIGHT)) {
            azimuth += 10;
            azimuth %= 360;
        } else if (event.isKeyDown(KeyEvent.VK_LEFT)) {
            azimuth += 350;
            azimuth %= 360;
        } else if (event.isKeyDown(KeyEvent.VK_UP)) {
            roll += 10;
            roll %= 360;
        } else if (event.isKeyDown(KeyEvent.VK_DOWN)) {
            roll += 350;
            roll %= 360;
        }
    }

    @Override
    public void updateMouse(PointerEvent event) {
        //protocol.sendPointerEvent(event);
        if (event.isButtonDown(MouseEvent.MOUSE_BUTTON_LEFT)) {
            protocol.sendClick();
        } else if (event.isButtonDown(MouseEvent.MOUSE_BUTTON_RIGHT)) {

            if (!isLongClick) {
                protocol.sendStartLongClick();
            } else {
                protocol.sendStopLongClick();
            }
            isLongClick = !isLongClick;
        }

    }

    @Override
    public void joinedClient(String id) {
        //clients.add(id);
        messages.put(id, "...");
    }

    @Override
    public void receiveMessage(String id, String message) {
        messages.put(id, message);
    }

    @Override
    public void init(String[] ids) {
        me = ids[0];

        for (int i = 1; i < ids.length; i++) {
            String id = ids[i];
            messages.put(id, "...");
        }
    }

    @Override
    public void exitClient(String id) {
        messages.remove(id);
    }

    @Override
    public void timeUpdate(long now) {
        protocol.sendRotation(azimuth, pitch, roll);
    }

    @Override
    public void rotate(String id, PlayerOrientation orientation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void click(String id, int strength) {
        // TODO Auto-generated method stub

    }
}
