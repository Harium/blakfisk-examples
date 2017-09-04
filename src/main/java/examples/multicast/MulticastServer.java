package examples.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
/**
 *
 * @author lycog
 */
public class MulticastServer {

	public static final String MULTICAST_GROUP = "224.2.2.4";
	public static final int PORT = 12309;
	
	private static MulticastSocket socket = null;
	private static InetAddress address;

	public static void main(String[] args) {
		
		System.out.println("Start multicast receiver");
		
		DatagramPacket inPacket = null;
		byte[] inBuf = new byte[256];
		try {
			//Prepare to join multicast group
			socket = new MulticastSocket(PORT);
			address = InetAddress.getByName(MULTICAST_GROUP);
			socket.joinGroup(address);

			while (true) {
				inPacket = MulticastHelper.receivePacket(socket, inBuf);
				String msg = new String(inPacket.getData(), 0, inPacket.getLength());
				
				//Listen to this
				if(msg.startsWith("This")) {
					System.out.println("From " + inPacket.getAddress() + ":"+inPacket.getPort()+" Msg : " + msg);
					send("Hello, my name is Serv?");
				} else {
					System.err.println("Server Ignore: " + inPacket.getAddress() + " Msg : " + msg);
				}
			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
	
	private static void send(String msg) throws IOException {
		MulticastHelper.send(socket, address, PORT, msg);
	}
}
