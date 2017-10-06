package examples.websocket.client;

import com.harium.etyl.networking.websocket.EtylWebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class EchoWebSocketClient extends EtylWebSocketClient {

    public EchoWebSocketClient(String serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to server: " + serverUri);
    }

}
