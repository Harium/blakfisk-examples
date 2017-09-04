package examples.room.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.HandshakerProtocol;
import examples.room.client.RoomClientProtocol;

public class RoomHandshaker extends HandshakerProtocol {

    private RoomServerProtocol protocol;

    public RoomHandshaker(RoomServerProtocol protocol, BlakFiskServer server) {
        super(RoomClientProtocol.PREFIX_ROOM_ACTION, server);
        this.protocol = protocol;
    }

    @Override
    public String handshakeText(Peer peer) {
        String message = RoomClientProtocol.PREFIX_CONNECT + " " + peer.getID() + " ";
        message += RoomClientProtocol.DATA_SEPARATOR;
        message += protocol.getRooms().size();
        message += RoomClientProtocol.DATA_SEPARATOR;
        message += protocol.listRooms();

        return message;
    }

}
