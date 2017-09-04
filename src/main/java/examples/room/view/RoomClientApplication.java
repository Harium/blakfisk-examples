package examples.room.view;

import com.harium.etyl.commons.event.Action;
import com.harium.etyl.commons.event.GUIEvent;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.etyl.ui.Button;
import com.harium.etyl.ui.UI;
import com.harium.etyl.ui.label.TextLabel;
import examples.room.client.RoomClient;
import examples.room.client.RoomClientListener;
import examples.room.client.RoomClientProtocol;
import examples.room.model.Room;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RoomClientApplication extends CommonClientApplication implements RoomClientListener {

    private RoomClientProtocol protocol;

    private int maxPlayers = 3;

    public static final int PORT = 9906;
    private static final String LOCAL_IP = "127.0.0.1";

    public static final String PARAM_CLIENT = "client";

    private Map<String, Room> rooms = new LinkedHashMap<String, Room>();

    private Button createButton;
    private Button refreshButton;

    private List<Button> roomButtons = new ArrayList<Button>();

    private int skin = 0;

    public RoomClientApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {
        createButton = generateButton(60, 100);
        createButton.setLabel(new TextLabel("Create Room"));
        createButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "createRoomAction"));
        UI.add(createButton);

        refreshButton = generateButton(60, 190);
        refreshButton.setLabel(new TextLabel("List Rooms"));
        refreshButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "listRoomAction"));
        UI.add(refreshButton);

        client = new RoomClient(LOCAL_IP, PORT, this);
        client.connect();

        protocol = client.getRoomProtocol();

        session.put(PARAM_CLIENT, client);

        loading = 100;
    }

    private Button generateButton(int x, int y) {
        return new Button(x, y, 200, 60);
    }

    @Override
    public void updateKeyboard(KeyEvent event) {
        if (event.isKeyDown(KeyEvent.VK_A)) {
            skin = 0;
        } else if (event.isKeyDown(KeyEvent.VK_B)) {
            skin = 1;
        } else if (event.isKeyDown(KeyEvent.VK_C)) {
            skin = 2;
        }
    }

    public void createRoomAction() {
        protocol.createRoom(maxPlayers);
    }

    public void listRoomAction() {
        protocol.listRooms();
    }

    public void joinRoomAction(String roomId) {
        protocol.joinRoom(roomId);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        //Write msgs
        drawMessages(g);

        g.drawString("Rooms:", 300, 60);
        g.drawRect(298, 64, 204, 400);

        //Draw Skins
        for (int i = 0; i < 3; i++) {
            int cx = 26 + 42 * i;
            int cy = 400;
            int radius = 20;
            g.setColor(Color.BLACK);
            g.fillCircle(cx, cy, radius);

            g.setColor(Color.YELLOW);
            char label = (char) ('a' + i);
            g.drawString("" + label, cx - radius, cy - radius, radius * 2, radius * 2);

            if (skin == i) {
                g.setLineWidth(2);
                g.drawCircle(cx, cy, radius);
            }
        }
    }

    @Override
    public void listRooms(Collection<Room> rooms) {
        UI.clear();
        UI.add(createButton);
        UI.add(refreshButton);

        int count = 0;
        for (Room room : rooms) {
            this.rooms.put(room.id, room);

            Button roomButton = generateButton(300, 70 + 85 * count);
            roomButton.setLabel(new TextLabel(room.id));
            roomButton.addAction(GUIEvent.MOUSE_LEFT_BUTTON_UP, new Action(this, "joinRoomAction", room.id));
            roomButtons.add(roomButton);
            UI.add(roomButton);
            count++;
        }
    }

    @Override
    public void createdRoom(Room room) {
        nextApplication = new RoomApplication(w, h, room);
    }

    @Override
    public void joinedRoom(Room room) {
        nextApplication = new RoomApplication(w, h, room);
    }

}
