package examples.action.view;

import com.harium.etyl.commons.context.Application;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.etyl.commons.event.PointerEvent;
import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.etyl.networking.protocol.ProtocolHandler;
import examples.action.client.ActionClient;
import examples.action.client.ActionClientListener;
import examples.action.client.ActionClientProtocol;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ActionClientApplication extends Application implements ActionClientListener {

    private int me = -1;

    private ActionClient client;

    private ActionClientProtocol protocol;

    public static final int PORT = 9906;

    private static final int CLIENT_DELAY = 100;

    private Map<Integer, String> messages = new LinkedHashMap<Integer, String>();

    public ActionClientApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {

        //client = new ActionClient("192.168.0.20", PORT, this);
        client = new ActionClient(ProtocolHandler.LOCAL_HOST, PORT, this);
        client.connect();

        protocol = client.getActionProtocol();

        loading = 100;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        //Write msgs
        int i = 0;
        for (Entry<Integer, String> entry : messages.entrySet()) {
            int id = entry.getKey();
            String message = entry.getValue();

            int x = 60;
            int y = 120 + 20 * i;

            g.drawString(id + ": " + message, x, y);

            if (id == me) {
                g.fillRect(x - 20, y - 10, 14, 14);
            }
            i++;
        }
    }

    @Override
    public void updateKeyboard(KeyEvent event) {
        protocol.sendKeyEvent(event);
    }

    @Override
    public void updateMouse(PointerEvent event) {
        protocol.sendPointerEvent(event);
    }

    @Override
    public void joinedClient(int id) {
        //clients.add(id);
        messages.put(id, "...");
    }

    @Override
    public void receiveMessage(int id, String message) {
        messages.put(id, message);
    }

    @Override
    public void init(String[] ids) {
        me = Integer.parseInt(ids[0]);

        for (int i = 1; i < ids.length; i++) {
            int id = Integer.parseInt(ids[i]);
            messages.put(id, "...");
        }
    }

    @Override
    public void exitClient(int id) {
        messages.remove(id);
    }
}
