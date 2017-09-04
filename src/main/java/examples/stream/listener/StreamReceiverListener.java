package examples.stream.listener;

import java.awt.image.BufferedImage;

public interface StreamReceiverListener {
	void init(int id);
	void addStream(int id);
	void removeStream(int id);
	void onReceiveData(int id, byte[] data);
	void onReceiveImage(int id, BufferedImage data);
}
