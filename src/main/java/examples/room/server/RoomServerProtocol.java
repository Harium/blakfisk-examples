package examples.room.server;

import com.harium.etyl.networking.EtylServer;
import com.harium.etyl.networking.model.Peer;
import com.harium.etyl.networking.protocol.ProtocolUtils;
import com.harium.etyl.networking.protocol.common.StringServerProtocol;
import examples.room.client.RoomClientProtocol;
import examples.room.model.Room;

import java.util.*;

/**
 * Single room server protocol
 */
public class RoomServerProtocol extends StringServerProtocol {
		
	private int count = 0;
	private Set<Peer> players = new LinkedHashSet<Peer>();
	
	private Map<String, Room> rooms = new LinkedHashMap<String, Room>();
	private Map<Peer, Room> owners = new LinkedHashMap<Peer, Room>();
	
	public RoomServerProtocol(String prefix, EtylServer server) {
		super(prefix, server);
	}
	
	@Override
	public void addPeer(Peer peer) {
		super.addPeer(peer);
		players.add(peer);
	}
	
	public void removePeer(Peer peer) {
		super.removePeer(peer);
		players.remove(peer);
	}
	
	@Override
	public void receiveUDP(Peer usuario, String msg) {
		// TODO Auto-generated method stub
	}

	@Override
	public void receiveTCP(Peer peer, String msg) {
		System.out.println(getClass().getSimpleName()+" - Received TCP: "+msg);
		
		if (msg.startsWith(RoomClientProtocol.PREFIX_CREATE_ROOM)) {
			if (!owners.containsKey(peer)) {
				Room room = createRoom(peer, msg);
				notifyCreatedRoom(peer, room);	
			} else {
				sendTCP(peer, RoomClientProtocol.PREFIX_CREATE_ROOM_FAIL);
			}
			
		} else if (msg.startsWith(RoomClientProtocol.PREFIX_JOIN_ROOM)) {
			String roomId = ProtocolUtils.nextPrefix(RoomClientProtocol.PREFIX_JOIN_ROOM, msg);
			Room room = rooms.get(roomId);
			if (!room.players.contains(peer)) {
				maybeJoinRoom(peer, room);
				System.out.println("Room "+roomId+" has "+room.players.size()+"/"+room.maxPlayers);
			} else {
				//Already in the room
				sendTCP(peer, RoomClientProtocol.PREFIX_JOIN_ROOM_FAIL);
			}
			
		} else if (msg.startsWith(RoomClientProtocol.PREFIX_EXIT_ROOM)) {
			String roomId = ProtocolUtils.nextPrefix(RoomClientProtocol.PREFIX_EXIT_ROOM, msg);
			Room room = rooms.get(roomId);
			
			leftRoom(peer, room);
		} else if (msg.startsWith(RoomClientProtocol.PREFIX_LIST_ROOM)) {
			listAllRooms(peer);
		} else if (msg.startsWith(RoomClientProtocol.PREFIX_READY)) {
			String roomId = ProtocolUtils.nextPrefix(RoomClientProtocol.PREFIX_READY, msg);
			Room room = rooms.get(roomId);
			
			ready(peer, room);
		}else if (msg.startsWith(RoomClientProtocol.PREFIX_START_ROOM)) {
			String roomId = ProtocolUtils.nextPrefix(RoomClientProtocol.PREFIX_START_ROOM, msg);
			
			Room room = rooms.get(roomId);
			startRoom(peer, room);
		} else {
			sendTCPtoAll(peer.getId()+" "+msg);
		}
	}

	private void listAllRooms(Peer peer) {
		sendTCP(peer, RoomClientProtocol.PREFIX_LIST_ROOM+" "+RoomClientProtocol.DATA_SEPARATOR+rooms.size()+RoomClientProtocol.DATA_SEPARATOR+listRooms());
	}

	private void startRoom(Peer peer, Room room) {
		//If only the creator can start the game 
		if (room.creatorId == peer.getId()) {
			room.state = RoomState.STARTED;
			notifyStartedRoom(room);
		}
	}

	private void ready(Peer peer, Room room) {
		room.ready.add(peer.getId());
		sendTCPtoAll(room.players, RoomClientProtocol.PREFIX_READY+" "+peer.getId());
	}

	public void maybeJoinRoom(Peer peer, Room room) {
				
		//TODO Change the protocol based on the room's game
		//TODO move this responsability to the listener
		/*ActionServerProtocol protocol = new ActionServerProtocol(ActionClientProtocol.PREFIX_ACTION, server);
		peer.addListener(protocol.getPrefix(), protocol);*/
		
		if(room.currentPlayers < room.maxPlayers) {
			addPeerToRoom(room, peer);
			
			sendTCP(peer, RoomClientProtocol.PREFIX_JOIN_NEW_COMMER+" "+room.id+" "+room.currentPlayers+" "+room.maxPlayers+" "+listPlayers(room));
		} else {
			sendTCP(peer, RoomClientProtocol.PREFIX_JOIN_ROOM_FAIL_FULL_ROOM+" "+room.id);
		}
	}
	
	public void leftRoom(Peer peer, Room room) {
		int id = peer.getId();
		
		sendTCPtoAll(room.players, RoomClientProtocol.PREFIX_EXIT_ROOM+" "+id);
	}
	
	public Room createRoom(Peer peer, String msg) {
		Room room = new Room();
		
		String[] part = msg.split(" ");
		int maxPlayers = Integer.parseInt(part[1]);
	
		room.id = generateID();
		room.creatorId = peer.getId();
		room.maxPlayers = maxPlayers;
		room.state = RoomState.WAITING_PLAYERS;
				
		room.currentPlayers = 0; //The creator
		rooms.put(room.id, room);
		owners.put(peer, room);
		
		//Add creator
		addPeerToRoom(room, peer);
				
		return room;
	}
	
	private void addPeerToRoom(Room room, Peer peer) {
		int id = peer.getId();
		
		//Send just to the players in the room
		sendTCPtoAll(room.players, RoomClientProtocol.PREFIX_JOIN_ROOM+" "+room.id+" "+id);
		room.currentPlayers++;
		room.players.add(id);
	}

	private String generateID() {
		count++;
		return Integer.toString(count, 16);
	}

	private void notifyCreatedRoom(Peer peer, Room room) {
		sendTCP(peer, RoomClientProtocol.PREFIX_CREATE_ROOM_SUCCESS+" "+room.toString());
	}
	
	private void notifyStartedRoom(Room room) {
		sendTCPtoAll(room.players, RoomClientProtocol.PREFIX_START_ROOM+" "+room.id);
	}

	public Set<Peer> getPlayers() {
		return players;
	}

	private String listPlayers(Room room) {
		String list = "";
		
		for(Integer peer: room.players) {
			list += peer+RoomClientProtocol.LIST_SEPARATOR;	
		}
		
		return list;
	}

	
	public String listRooms() {
		String list = "";
		
		for(Room room: rooms.values()) {
			list += room.toString()+RoomClientProtocol.LIST_SEPARATOR;	
		}
		
		return list;
	}

	public Collection<Room> getRooms() {
		return rooms.values();
	}
	
}