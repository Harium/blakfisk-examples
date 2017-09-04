package examples.room.client;

import com.harium.blakfisk.BlakFiskClient;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.ProtocolUtils;
import com.harium.blakfisk.protocol.common.StringClientProtocol;
import examples.room.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomClientProtocol extends StringClientProtocol {

    private int id;

    private Room room;
    private RoomClientListener listener;

    public static final String PREFIX_ROOM_ACTION = "/r";

    public static final String DATA_SEPARATOR = ":";
    public static final String LIST_SEPARATOR = ",";

    /**
     * Wait action
     */
    public static final String PREFIX_CONNECT = "h";

    public static final String PREFIX_CREATE_ROOM = "c";
    public static final String PREFIX_CREATE_ROOM_SUCCESS = "s";
    public static final String PREFIX_CREATE_ROOM_FAIL = "f";
    public static final String PREFIX_START_ROOM = "t";

    public static final String PREFIX_INIT_ROOM = "i";
    public static final String PREFIX_JOIN_ROOM = "j";
    public static final String PREFIX_EXIT_ROOM = "q";
    public static final String PREFIX_LIST_ROOM = "l";
    public static final String PREFIX_JOIN_ROOM_FAIL = "k";
    public static final String PREFIX_JOIN_ROOM_FAIL_FULL_ROOM = "F";
    public static final String PREFIX_JOIN_NEW_COMMER = "n";

    public static final String PREFIX_READY = "d";

    public RoomClientProtocol(RoomClientListener listener, BlakFiskClient client) {
        super(PREFIX_ROOM_ACTION, client);
        this.listener = listener;
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {

        if (msg.startsWith(PREFIX_CONNECT)) {
            //HandShake message
            int id = Integer.parseInt(ProtocolUtils.nextPrefix(PREFIX_CONNECT, msg));
            this.id = id;
            listener.init(id);

            String parts[] = msg.split(DATA_SEPARATOR);

            int roomCount = Integer.parseInt(parts[1]);

            if (roomCount > 0) {
                System.out.println("CONNECT: " + parts[2]);
                List<Room> rooms = parseRooms(roomCount, parts[2]);

                listener.listRooms(rooms);
            }

        } else if (msg.startsWith(PREFIX_CREATE_ROOM_SUCCESS)) {
            String[] parts = msg.split(" ");

            String roomId = parts[1];
            int currentPlayers = Integer.parseInt(parts[2]);
            int maxPlayers = Integer.parseInt(parts[3]);

            //Starts a room
            room = new Room();
            room.creatorId = id;
            room.id = roomId;
            room.players.add(id);
            room.currentPlayers = currentPlayers;
            room.maxPlayers = maxPlayers;

            listener.createdRoom(room);
        } else if (msg.startsWith(PREFIX_JOIN_ROOM)) {
            String[] parts = msg.split(" ");
            String roomId = parts[1]; //Useful with multiple rooms
            int id = Integer.parseInt(parts[2]);

            room.join(id);
            listener.joinedClient(id);
        } else if (msg.startsWith(PREFIX_JOIN_NEW_COMMER)) {
            String[] parts = msg.split(" ");
            String roomId = parts[1];
            int currentPlayers = Integer.parseInt(parts[2]);
            int maxPlayers = Integer.parseInt(parts[3]);
            String listIds = parts[4];

            room = new Room();
            room.id = roomId;
            room.currentPlayers = currentPlayers;
            room.maxPlayers = maxPlayers;

            for (String sid : listIds.split(LIST_SEPARATOR)) {
                int id = Integer.parseInt(sid);
                room.join(id);
            }
            listener.joinedRoom(room);
        } else if (msg.startsWith(PREFIX_JOIN_ROOM_FAIL_FULL_ROOM)) {
            System.out.println("Room is full");
        } else if (msg.startsWith(PREFIX_EXIT_ROOM)) {
            int id = Integer.parseInt(msg.split(" ")[1]);
            room.left(id);
            listener.exitClient(id);
        } else if (msg.startsWith(PREFIX_LIST_ROOM)) {

            String parts[] = msg.split(DATA_SEPARATOR);

            int roomCount = Integer.parseInt(parts[1]);

            if (roomCount > 0) {
                List<Room> rooms = parseRooms(roomCount, parts[2]);
                listener.listRooms(rooms);
            }
        } else if (msg.startsWith(PREFIX_READY)) {
            int id = Integer.parseInt(msg.split(" ")[1]);
            room.ready.add(id);
            listener.joinedClient(id);
        }
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        // TODO Auto-generated method stub

    }

    public void createRoom(int maxPlayers) {
        sendTCP(PREFIX_CREATE_ROOM + " " + Integer.toString(maxPlayers));
    }

    public void joinRoom(String roomId) {
        sendTCP(PREFIX_JOIN_ROOM + " " + roomId);
    }

    public void leftRoom(String roomId) {
        sendTCP(PREFIX_EXIT_ROOM + " " + roomId);
    }

    public void listRooms() {
        sendTCP(PREFIX_LIST_ROOM);
    }

    public void ready(String roomId) {
        sendTCP(PREFIX_READY + " " + roomId);
    }

    public List<Room> parseRooms(int roomCount, String text) {
        String[] roomsText = text.split(LIST_SEPARATOR);
        List<Room> list = new ArrayList<Room>(roomCount);

        for (String roomText : roomsText) {
            Room room = Room.fromString(roomText);
            list.add(room);
        }
        return list;
    }

    public RoomClientListener getListener() {
        return listener;
    }

    public void setListener(RoomClientListener listener) {
        this.listener = listener;
    }

}