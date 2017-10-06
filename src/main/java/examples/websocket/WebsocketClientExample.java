package examples.websocket;

import examples.websocket.client.EchoWebSocketClient;

public class WebsocketClientExample {

    public static void main(String[] args) {
        EchoWebSocketClient client = new EchoWebSocketClient("ws://localhost:" + WebsocketServerExample.PORT);
        client.connect();
    }

}
