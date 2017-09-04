package examples.action.client;

public interface ActionClientListener {
	public void exitClient(int id);
	public void joinedClient(int id);	
	public void receiveMessage(int id, String message);
	public void init(String[] ids);
}
