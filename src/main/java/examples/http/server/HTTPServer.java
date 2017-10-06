package examples.http.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.model.data.ConnectionData;
import com.harium.etyl.networking.model.data.RawData;
import examples.http.Http;

import java.io.IOException;
import java.nio.channels.Channel;


public class HTTPServer extends EtylServer {

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
        System.out.println("HandShakePeer " + peer.getId() + " connected.");
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
