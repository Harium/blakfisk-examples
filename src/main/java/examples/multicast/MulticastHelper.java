package examples.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastHelper {

	public static void send(DatagramSocket socket, String host, int port, String msg) throws IOException {
		InetAddress addr = InetAddress.getByName(host);
		send(socket, addr, port, msg);
	}
	
	public static void send(DatagramSocket socket, InetAddress addr, int port, String msg) throws IOException {
		DatagramPacket outPacket = buildPacket(addr, port, msg);
		socket.send(outPacket);
	}
	
	private static DatagramPacket buildPacket(InetAddress address, int port, String msg) {
		byte[] outBuf = msg.getBytes();
		DatagramPacket outPacket = new DatagramPacket(outBuf, outBuf.length, address, port);
		return outPacket;
	}
	
	public static DatagramPacket receivePacket(DatagramSocket socket, byte[] inBuf) throws IOException {
		DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);
		socket.receive(inPacket);
		return inPacket;
	}
	
	public static String receive(DatagramSocket socket, byte[] inBuf) throws IOException {
		DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);
		socket.receive(inPacket);
		
		String msg = new String(inPacket.getData(), 0, inPacket.getLength());
		
		return msg;
	}
}
