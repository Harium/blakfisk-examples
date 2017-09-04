package examples.stream.application;

import com.harium.etyl.commons.context.Application;
import com.harium.etyl.core.graphics.Graphics;
import examples.stream.listener.StreamReceiverListener;
import examples.stream.server.StreamReceiverServer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

public class StreamServerApplication extends Application implements StreamReceiverListener {

    private Map<Integer, BufferedImage> streams = new LinkedHashMap<Integer, BufferedImage>();
    private StreamReceiverServer server;

    public StreamServerApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {
        server = new StreamReceiverServer(StreamClientApplication.PORT);
        server.setListener(this);
        server.start();

        loading = 100;
    }

    @Override
    public void draw(Graphics g) {
        if (server == null) {
            return;
        }
        if (!server.getProtocol().getPeers().isEmpty()) {
            g.setColor(Color.BLACK);
            int count = 0;
            for (BufferedImage image : streams.values()) {
                if (image != null) {
                    int x = StreamClientApplication.WIDTH * count;
                    int y = 0;

                    g.drawImage(image, x, y);
                    g.drawRect(x, y, StreamClientApplication.WIDTH, StreamClientApplication.HEIGHT);
                }
                count++;
            }

        } else {
            g.setColor(Color.BLACK);
            g.drawString("Empty!", this);
        }

    }

    @Override
    public void onReceiveImage(int id, BufferedImage image) {
        if (image == null) {
            return;
        }

        streams.put(id, image);
    }

    @Override
    public void onReceiveData(int id, byte[] data) {

    }

    @Override
    public void addStream(int id) {
        streams.put(id, null);
    }

    @Override
    public void removeStream(int id) {
        streams.remove(id);
    }

    @Override
    public void init(int id) {
        // TODO Auto-generated method stub

    }

}
