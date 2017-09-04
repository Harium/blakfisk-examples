package examples.http.server;

import com.harium.blakfisk.BlakFiskServer;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.HandshakerProtocol;
import examples.http.header.HTTPHeader;

public class HTTPHandshaker extends HandshakerProtocol {

    public HTTPHandshaker(String prefix, BlakFiskServer server) {
        super(prefix, server);
    }

    @Override
    public String handshakeText(Peer peer) {
        return HTTPHeader.RESPONSE_OK + "\r\n" + "Connection: Close";
    }

}
