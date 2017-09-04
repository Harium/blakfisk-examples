package examples.stream.server;

import com.harium.blakfisk.codec.image.ImageEncoder;
import com.harium.blakfisk.codec.image.awt.BufferedImageDirectEncoder;
import com.harium.blakfisk.codec.image.awt.BufferedImageEncoder;
import com.harium.blakfisk.codec.image.awt.BufferedImageYUVDirectEncoder;
import com.harium.blakfisk.compression.Compressor;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.common.ProtocolImpl;
import com.harium.blakfisk.util.Base64;
import com.harium.blakfisk.util.ByteMessageUtils;
import examples.stream.application.StreamClientApplication;
import examples.stream.client.StreamSenderProtocol;
import examples.stream.listener.StreamReceiverListener;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Stream data server protocol
 */
public class StreamReceiverProtocol extends ProtocolImpl {

    private StreamReceiverListener listener;
    private Compressor<byte[]> compressor = StreamSenderProtocol.buildCompressor();

    protected Map<Integer, Peer> peers = new HashMap<Integer, Peer>();

    public StreamReceiverProtocol(String prefix) {
        super(prefix);
    }

    @Override
    public void addPeer(Peer peer) {
        peers.put(peer.getID(), peer);
        listener.addStream(peer.getID());
    }

    public void removePeer(Peer peer) {
        peers.remove(peer.getID());
        listener.removeStream(peer.getID());
    }

    public Map<Integer, Peer> getPeers() {
        return peers;
    }

    @Override
    public void receiveUDP(Peer peer, byte[] msg) {
        String prefix = new String(ByteMessageUtils.getPrefix(msg));
        byte[] crop = ByteMessageUtils.wipePrefix(prefix.getBytes(), msg);

        System.out.println("Recv: " + prefix);

        if (prefix.startsWith(StreamSenderProtocol.PREFIX_SEND_RAW_DATA)) {
            receiveRawData(peer, crop);
        }
    }

    @Override
    public void receiveTCP(Peer peer, byte[] msg) {

        String prefix = new String(ByteMessageUtils.getPrefix(msg));
        byte[] crop = ByteMessageUtils.wipePrefix(prefix.getBytes(), msg);

        if (prefix.startsWith(StreamSenderProtocol.PREFIX_SEND_DATA)) {
            receiveData(peer, crop);
        } else if (prefix.startsWith(StreamSenderProtocol.PREFIX_SEND_RAW_DATA)) {
            receiveRawData(peer, crop);
        } else if (prefix.startsWith(StreamSenderProtocol.PREFIX_SEND_YUV_DATA)) {
            receiveYUVData(peer, crop);
        } else if (prefix.startsWith(StreamSenderProtocol.PREFIX_PONG)) {

        }
    }

    private void receiveData(Peer peer, byte[] crop) {

        byte[] lb = ByteMessageUtils.getPrefix(crop);
        int len = Integer.parseInt(new String(lb));

        byte[] data = ByteMessageUtils.wipePrefix(lb, crop);

        try {
            data = Base64.decode(new String(data));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ImageEncoder<BufferedImage> encoder = new BufferedImageEncoder();
        BufferedImage image = encoder.decode(data);

        listener.onReceiveImage(peer.getID(), image);
    }

    private void receiveRawData(Peer peer, byte[] crop) {

        byte[] lb = ByteMessageUtils.getPrefix(crop);
        int len = Integer.parseInt(new String(lb));

        byte[] data = ByteMessageUtils.wipePrefix(lb, crop);

        //listener.onReceiveData(peer.getSessionID(), data);

        try {
            byte[] decompressedData = compressor.decompress(len, data);
            ImageEncoder<BufferedImage> encoder = new BufferedImageDirectEncoder(StreamClientApplication.WIDTH, StreamClientApplication.HEIGHT);
            BufferedImage image = encoder.decode(decompressedData);

            listener.onReceiveImage(peer.getID(), image);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void receiveYUVData(Peer peer, byte[] crop) {

        byte[] lb = ByteMessageUtils.getPrefix(crop);
        int len = Integer.parseInt(new String(lb));

        byte[] data = ByteMessageUtils.wipePrefix(lb, crop);

        try {
            byte[] decompressedData = compressor.decompress(len, data);

            ImageEncoder<BufferedImage> encoder = new BufferedImageYUVDirectEncoder(StreamClientApplication.WIDTH, StreamClientApplication.HEIGHT);
            BufferedImage image = encoder.decode(decompressedData);

            listener.onReceiveImage(peer.getID(), image);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public StreamReceiverListener getListener() {
        return listener;
    }

    public void setListener(StreamReceiverListener listener) {
        this.listener = listener;
    }

}