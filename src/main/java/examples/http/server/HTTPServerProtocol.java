package examples.http.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.common.StringServerProtocol;
import examples.http.header.HTTPHeader;

import java.util.HashMap;
import java.util.Map;

public class HTTPServerProtocol extends StringServerProtocol {

    public HTTPServerProtocol(String prefix, EtylServer server) {
        super(prefix, server);
    }

    @Override
    public void receiveUDP(Peer peer, String msg) {
        // TODO Auto-generated method stub
    }

    @Override
    public void receiveTCP(Peer peer, String msg) {

        String prefix = msg.substring(0, msg.indexOf(" "));

        int dividerIndex = prefix.lastIndexOf("/") + 1;

        String path = prefix.substring(0, dividerIndex);
        String paramsText = prefix.substring(dividerIndex);

        Map<String, String> params = getParams(paramsText);
        handlePath(peer, path, params);
    }

    protected void handlePath(Peer peer, String path, Map<String, String> params) {
        sendTCPNoPrefix(peer, HTTPHeader.fakeResponse("<strong>Hello my friend " + peer.getId() + "</strong>"));
    }

    private Map<String, String> getParams(String paramsText) {
        if (paramsText.startsWith("?")) {
            paramsText = paramsText.substring(1);
        }

        Map<String, String> params = new HashMap<String, String>();

        if (paramsText.isEmpty() || !paramsText.contains("=")) {
            return params;
        }

        String[] paramsSplit = paramsText.split("&");

        for (String param : paramsSplit) {
            String[] paramParts = param.split("=");
            params.put(paramParts[0], paramParts[1]);
        }

        return params;
    }

}
