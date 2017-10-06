package examples.websocket;

import examples.simpletcp.server.SimpleServerProtocol;
import examples.websocket.server.EchoWebSocketServer;

public class WebsocketServerExample {

    public static final int PORT = 10999;

    public static void main(String[] args) {
        EchoWebSocketServer server = new EchoWebSocketServer(PORT);
        server.addProtocol("echo", new SimpleServerProtocol("echo", server));
        server.start();

        //Disable Firewalls
        //sudo service iptables stop
        //sudo service firewalld stop
    }

}
