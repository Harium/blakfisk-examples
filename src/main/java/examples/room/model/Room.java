package examples.room.model;

import java.util.LinkedHashSet;
import java.util.Set;

import examples.room.server.RoomState;

public class Room {

	public String id;
	public int creatorId;	
	public int maxPlayers;
	public int currentPlayers = 0;
	
	public RoomState state = RoomState.WAITING_PLAYERS;
	
	public Set<Integer> players = new LinkedHashSet<Integer>();
	public Set<Integer> ready = new LinkedHashSet<Integer>();
	
	public void join(int peerId) {
		players.add(peerId);
	}
	
	public void left(int peerId) {
		players.remove(peerId);
	}
	
	public void ready(int peerId) {
		ready.add(peerId);
	}
	
	public String toString() {
		return id+" "+currentPlayers+" "+maxPlayers;
	}
	
	public static Room fromString(String text) {
		Room room = new Room();
		String[] parts = text.split(" ");
		
		String id = parts[0];
		int currentPlayers = Integer.parseInt(parts[1]);
		int maxPlayers = Integer.parseInt(parts[2]);
		
		room.id = id;
		room.currentPlayers = currentPlayers;
		room.maxPlayers = maxPlayers;
		
		return room;
	}
	
}
