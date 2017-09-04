package examples.topaction.client;

public interface TopActionClientListener {
	public void exitClient(String id);
	public void joinedClient(String id);
	public void receiveMessage(String id, String message);
	public void init(String[] ids);
}
