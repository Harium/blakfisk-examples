package examples.ping.client;

import com.harium.blakfisk.BlakFiskClient;

public class PingClient extends BlakFiskClient {

    private PingClientProtocol pingProtocol;

    public PingClient(String ip, int tcpPort) {
        super(ip, tcpPort);

        pingProtocol = new PingClientProtocol(this);
        addProtocol(pingProtocol);
    }

    public PingClientProtocol getActionProtocol() {
        return pingProtocol;
    }

    @Override
    public void run() {
        super.run();

        pingProtocol.sendPing();
    }

}
