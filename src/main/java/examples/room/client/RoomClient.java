package examples.room.client;

import com.harium.etyl.networking.EtylClient;

public class RoomClient extends EtylClient {

    private RoomClientProtocol roomProtocol;

    public RoomClient(String ip, int tcpPort, RoomClientListener listener) {
        super(ip, tcpPort);

        roomProtocol = new RoomClientProtocol(listener, this);
        addProtocol(roomProtocol);
    }

    public RoomClientProtocol getRoomProtocol() {
        return roomProtocol;
    }

}
