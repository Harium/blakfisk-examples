package examples.benchmark;

import com.harium.blakfisk.BlakFiskClient;
import com.harium.blakfisk.backend.kryo.KryoEndpoint;
import examples.simpletcp.server.HandShakeServer;
import examples.simpletcp.server.SimpleServerProtocol;

public class BenchmarkFastDisconnect {

    private static final String IP = KryoEndpoint.LOCAL_HOST;
    private static final int PORT = 10101;

    private static final String LISTENER_PREFIX = "/s";

    public static void main(String[] args) {
        HandShakeServer server = new HandShakeServer(PORT);

        SimpleServerProtocol listener = new SimpleServerProtocol(LISTENER_PREFIX, server);

        server.addProtocol(listener.getPrefix(), listener);
        server.start();

        //Create multiple
        for (int i = 0; i < 1300; i++) {
            BlakFiskClient client = new BlakFiskClient(IP, PORT);
            client.connect();
            client.close();
        }

        int i = 200;
        while (i > 0) {
            i--;
            int connections = server.getConnections().length;
            System.out.println("Active connections: " + connections);

            if (connections == 0) {
                break;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}

