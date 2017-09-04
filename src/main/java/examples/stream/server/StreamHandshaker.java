package examples.stream.server;


import com.harium.blakfisk.backend.kryo.KryoServer;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.HandshakerProtocol;
import examples.stream.client.StreamSenderProtocol;

public class StreamHandshaker extends HandshakerProtocol {

    public StreamHandshaker(KryoServer server) {
        super(StreamSenderProtocol.PREFIX_STREAM_ACTION, server);
    }

    @Override
    public String handshakeText(Peer peer) {
        return StreamSenderProtocol.PREFIX_CONNECT + " " + peer.getID() + " ";
    }

}
