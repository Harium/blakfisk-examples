package examples.ping.client;

import com.harium.etyl.networking.EtylClient;

public class PingClient extends EtylClient {

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
