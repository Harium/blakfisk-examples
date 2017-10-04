package examples.room.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.HandshakerProtocol;
import examples.room.client.RoomClientProtocol;

public class RoomHandshaker extends HandshakerProtocol {

    private RoomServerProtocol protocol;

    public RoomHandshaker(RoomServerProtocol protocol, EtylServer server) {
        super(RoomClientProtocol.PREFIX_ROOM_ACTION, server);
        this.protocol = protocol;
    }

    @Override
    public String buildHandshake(Peer peer) {
        String message = RoomClientProtocol.PREFIX_CONNECT + " " + peer.getId() + " ";
        message += RoomClientProtocol.DATA_SEPARATOR;
        message += protocol.getRooms().size();
        message += RoomClientProtocol.DATA_SEPARATOR;
        message += protocol.listRooms();

        return message;
    }

}
