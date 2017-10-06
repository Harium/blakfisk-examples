package examples.http.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.HandshakerProtocol;
import examples.http.header.HTTPHeader;

public class HTTPHandshaker extends HandshakerProtocol {

    public HTTPHandshaker(String prefix, EtylServer server) {
        super(prefix, server);
    }

    @Override
    public String buildHandshake(Peer peer) {
        return HTTPHeader.RESPONSE_OK + "\r\n" + "Connection: Close";
    }

}
