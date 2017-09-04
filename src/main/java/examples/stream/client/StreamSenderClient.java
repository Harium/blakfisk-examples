package examples.stream.client;


import com.harium.blakfisk.BlakFiskClient;
import examples.stream.listener.StreamSenderListener;

public class StreamSenderClient extends BlakFiskClient {

    private StreamSenderProtocol protocol;

    public StreamSenderClient(String ip, int tcpPort, StreamSenderListener listener) {
        super(ip, tcpPort);

        protocol = new StreamSenderProtocol(listener, this);
        addProtocol(protocol);

        //tcp.setCodec(new SingleEncoder());

        //tcp.setCodec(new RawEncoder());
        //tcp.setSendBufferSize(64000);

    }

    public StreamSenderProtocol getProtocol() {
        return protocol;
    }

}
