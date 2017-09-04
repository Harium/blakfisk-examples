package examples.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
/**
 * @author lycog
 */
public class MulticastClient {

	private static int port = MulticastServer.PORT;
	private static InetAddress address;
	private static DatagramSocket sendSocket = null;

	public static void main(String[] args) {

		byte[] inBuf = new byte[256];
		byte[] outBuf = new byte[256];
				
		try {
			sendSocket = new DatagramSocket();
			address = InetAddress.getByName(MulticastServer.MULTICAST_GROUP);
			DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length, address, port);
			
			MulticastSocket recvSocket = new MulticastSocket(port); // must bind receive side
			recvSocket.joinGroup(InetAddress.getByName(MulticastServer.MULTICAST_GROUP));
			
			long counter = 0;
			String msg;

			while (true) {
				msg = "This is multicast! " + counter;
				counter++;

				send(msg);
				System.out.println("Sent: " + msg);

				try {
					Thread.sleep(800);
				} catch (InterruptedException ie) {
				}
				
				recvSocket.receive(inPacket);
				
				String rec = new String(inPacket.getData(), 0, inPacket.getLength());
				
				if(rec.startsWith("This")) {
					//Ignore
					System.err.println("Client Ignore: "+rec);
				} else {
					String fromServer = "Recv from " + inPacket.getAddress() + ", " + inPacket.getPort() + ": "
							+ new String(inPacket.getData(), 0, inPacket.getLength());
					
					System.out.println(fromServer);
				}
			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	private static void send(String msg) throws IOException {
		MulticastHelper.send(sendSocket, address, port, msg);
	}

}