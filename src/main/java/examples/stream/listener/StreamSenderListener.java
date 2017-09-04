package examples.stream.listener;

public interface StreamSenderListener {
	void init(String id);
	void addStream(String id);
	void removeStream(String id);
}
