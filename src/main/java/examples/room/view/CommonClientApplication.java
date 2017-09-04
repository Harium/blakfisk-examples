package examples.room.view;

import com.harium.etyl.commons.context.Application;
import com.harium.etyl.core.graphics.Graphics;
import examples.room.client.RoomClient;
import examples.room.client.RoomClientListener;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class CommonClientApplication extends Application implements RoomClientListener {

    private int me = -1;
    protected RoomClient client;
    private Map<Integer, String> messages = new LinkedHashMap<Integer, String>();

    public CommonClientApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void init(int id) {
        me = id;
    }

    @Override
    public void joinedClient(int id) {
        messages.put(id, "...");
    }

    @Override
    public void receiveMessage(int id, String message) {
        messages.put(id, message);
    }

    @Override
    public void exitClient(int id) {
        messages.remove(id);
    }

    public void drawMessages(Graphics g) {
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
    public void onStart() {
        // TODO Auto-generated method stub

    }
}
