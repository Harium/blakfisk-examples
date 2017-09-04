package examples.stream.application;

import com.harium.etyl.commons.context.Application;
import com.harium.etyl.core.graphics.Graphics;
import examples.stream.client.StreamSenderClient;
import examples.stream.listener.StreamSenderListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StreamClientApplication extends Application implements StreamSenderListener {

    public static int WIDTH = 48;
    public static int HEIGHT = 100;

    Robot robot;

    Rectangle screenRect;

    private StreamSenderClient client;

    public static final int PORT = 11011;
    private static final int CLIENT_DELAY = 100;
    private static final int CAPTURE_DELAY = 300;

    long lastUpdate = 0;

    BufferedImage bufferedImage;

    public StreamClientApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        //screenRect = new Rectangle(screenSize);
        screenRect = new Rectangle(0, 0, WIDTH, HEIGHT);

        try {
            robot = new Robot();

            client = new StreamSenderClient("127.0.0.1", PORT, this);
            client.connect();

        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        loading = 100;
    }

    @Override
    public void update(long now) {
        //capture();
    }

    public void capture() {
        long now = System.currentTimeMillis();
        if (lastUpdate + CAPTURE_DELAY < now) {

            System.out.println("Update");

            bufferedImage = robot.createScreenCapture(screenRect);

            System.out.println("Encode");

            try {
                //client.getProtocol().sendRawData(bufferedImage);
                client.getProtocol().sendYUVData(bufferedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            lastUpdate = now;
        }
    }

    @Override
    public void draw(Graphics g) {

        capture();

        if (bufferedImage != null) {
            g.drawImage(bufferedImage, 0, 0);
        }

		/*if(data != null) {
			
			if(needDecode) {
				toDraw = encoder.decode(data);
				needDecode = false;
			}
			
			if(toDraw != null) {
				g.drawImage(toDraw, 0, 0);	
			}
		}*/
    }

    @Override
    public void init(String id) {
        int offset = Integer.parseInt(id) - 1;
        screenRect = new Rectangle(offset * WIDTH, 0, WIDTH, HEIGHT);
    }

    @Override
    public void addStream(String id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeStream(String id) {
        // TODO Auto-generated method stub

    }
	
	/*public static ImageEncoder<BufferedImage> buildEncoder() {
		//return new BufferedImageEncoder();
		//return new BufferedImageDirectEncoder(WIDTH, HEIGHT);
		return new BufferedImageYUVDirectEncoder(WIDTH, HEIGHT);
	}*/

}
