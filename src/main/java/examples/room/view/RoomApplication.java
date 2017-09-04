package examples.room.view;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.core.graphics.Graphics;
import examples.action.client.ActionClientListener;
import examples.action.client.ActionClientProtocol;
import examples.room.client.RoomClient;
import examples.room.client.RoomClientListener;
import examples.room.model.Room;

import java.util.Collection;

public class RoomApplication extends CommonClientApplication implements RoomClientListener, ActionClientListener {

    private Room room;
    private ActionClientProtocol protocol;

    public RoomApplication(int w, int h, Room room) {
        super(w, h);
        this.room = room;
    }

    @Override
    public void load() {
        client = (RoomClient) session.get(RoomClientApplication.PARAM_CLIENT);
        client.getRoomProtocol().setListener(this);

        protocol = new ActionClientProtocol(this, client);

        client.addProtocol(protocol);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("Room: " + room.id, this);

        g.drawString("" + room.currentPlayers + "/" + room.maxPlayers, 20, 60);

        int count = 0;
        for (Integer peer : room.players) {
            g.drawString("Player " + (count + 1) + ": " + peer, 20, 100 + 20 * count);
            count++;
        }
    }

    @Override
    public void createdRoom(Room room) {
        // TODO Auto-generated method stub

    }

    @Override
    public void joinedRoom(Room room) {
        // TODO Auto-generated method stub

    }

    @Override
    public void listRooms(Collection<Room> rooms) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(String[] ids) {
        // TODO Auto-generated method stub

    }

}
