package examples.simpleudp.client;

import com.harium.etyl.networking.EtylClient;
import examples.simpletcp.SimpleClientExample;
import examples.simpletcp.client.SimpleClientProtocol;

public class HandShakeUDPClient extends EtylClient {

    private boolean running = true;
    private SimpleClientProtocol simpleProtocol;

    public HandShakeUDPClient(String ip, int udpPort) {
        super(ip, SimpleClientExample.PORT, udpPort);

        simpleProtocol = new SimpleClientProtocol(this);
        addProtocol(simpleProtocol);
    }

    public SimpleClientProtocol getSimpleProtocol() {
        return simpleProtocol;
    }

    public void sendMessages(final int delay) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    System.out.println("Sending message...");
                    simpleProtocol.sendUDPHandShake();
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }

}
