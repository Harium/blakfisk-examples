package examples.stream.server;


import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.HandshakerProtocol;
import examples.stream.client.StreamSenderProtocol;

public class StreamHandshaker extends HandshakerProtocol {

    public StreamHandshaker(EtylServer server) {
        super(StreamSenderProtocol.PREFIX_STREAM_ACTION, server);
    }

    @Override
    public String buildHandshake(Peer peer) {
        return StreamSenderProtocol.PREFIX_CONNECT + " " + peer.getId() + " ";
    }

}
