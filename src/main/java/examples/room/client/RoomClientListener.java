package examples.room.client;

import java.util.Collection;

import examples.room.model.Room;

public interface RoomClientListener {
	void init(int id);
	void createdRoom(Room room);
	void joinedRoom(Room room);
	void joinedClient(int id);
	void exitClient(int id);
	void receiveMessage(int id, String message);
	void listRooms(Collection<Room> rooms);
	void onStart();
}
