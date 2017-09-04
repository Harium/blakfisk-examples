package examples.orientation.listener;

import examples.orientation.model.PlayerOrientation;

public interface OrientationListener {
	public void exitClient(String id);
	public void joinedClient(String id);
	public void receiveMessage(String id, String message);
	public void rotate(String id, PlayerOrientation orientation);
	public void click(String id, int strength);
	public void init(String[] ids);
}
