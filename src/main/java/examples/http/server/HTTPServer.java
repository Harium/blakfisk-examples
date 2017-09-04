package examples.http.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import examples.http.Http;

import java.io.IOException;
import java.nio.channels.Channel;


public class HTTPServer extends BlakFiskServer {

    private HTTPServerProtocol listener;

    public HTTPServer(int port) {
        super(port);
        name = "HttpServer";
        //tcp.setCodec(new SingleEncoder());

        //handshaker = new HTTPHandshaker();

        listener = new HTTPServerProtocol(Http.METHOD_GET, this);

        addProtocol(" ", listener);
        addProtocol(Http.METHOD_GET, listener);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void joinPeer(Peer peer) {
        System.out.println("HandShakePeer " + peer.getID() + " connected.");
        listener.addPeer(peer);
    }

    public void afterHandshake(Channel channel) {
        try {
            channel.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void leftPeer(Peer peer) {
        // TODO Auto-generated method stub

    }

}
