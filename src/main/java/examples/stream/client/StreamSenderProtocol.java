package examples.stream.client;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.harium.blakfisk.BlakFiskClient;
import com.harium.blakfisk.codec.image.ImageEncoder;
import com.harium.blakfisk.codec.image.awt.BufferedImageDirectEncoder;
import com.harium.blakfisk.codec.image.awt.BufferedImageEncoder;
import com.harium.blakfisk.codec.image.awt.BufferedImageYUVDirectEncoder;
import com.harium.blakfisk.compression.Compressor;
import com.harium.blakfisk.compression.GZipCompressor;
import com.harium.blakfisk.model.Peer;
import com.harium.blakfisk.protocol.common.RawClientProtocol;
import com.harium.blakfisk.util.Base64;
import com.harium.blakfisk.util.ByteMessageUtils;
import examples.stream.listener.StreamSenderListener;

public class StreamSenderProtocol extends RawClientProtocol {
	
	private String id;
	private StreamSenderListener listener;

	private Compressor<byte[]> compressor = buildCompressor();
	
	public static final String PREFIX_STREAM_ACTION = "st";
		
	/**
	 * Wait action
	 */
	public static final String PREFIX_CONNECT = "h";
	
	public static final String PREFIX_SEND_DATA = "d";
	public static final String PREFIX_SEND_RAW_DATA = "r";
	public static final String PREFIX_SEND_YUV_DATA = "y";
	public static final String PREFIX_PING = "p";
	public static final String PREFIX_PONG = "o";
	
	public StreamSenderProtocol(StreamSenderListener listener, BlakFiskClient client) {
		super(PREFIX_STREAM_ACTION, client);
		this.listener = listener;
	}
	
	@Override
	public void receiveTCP(Peer peer, byte[] msg) {
		String prefix = new String(ByteMessageUtils.getPrefix(msg));
		byte[] crop = ByteMessageUtils.wipePrefix(prefix.getBytes(), msg);
		
		if(prefix.startsWith(PREFIX_CONNECT)) {
			//HandShake message
			String cropMsg = new String(crop);
			
			String[] ids = cropMsg.split(" ");
			this.id = ids[0];
			listener.init(id);
			
		} else if(prefix.startsWith(PREFIX_PING)) {
			sendTCP(PREFIX_PONG.getBytes());
		}
	}

	@Override
	public void receiveUDP(Peer peer, byte[] msg) {
		// TODO Auto-generated method stub
	}
	
	public void sendData(BufferedImage image) {
		ImageEncoder<BufferedImage> encoder = new BufferedImageEncoder();
		byte[] data = encoder.encode(image);
		
		String msg = PREFIX_SEND_DATA+" "+data.length+" "+ Base64.encodeBytes(data);
		sendTCP(msg.getBytes());
	}
	
	public void sendRawData(BufferedImage image) throws IOException {
		int width = image.getWidth();
		int height = image.getHeight();
		
		ImageEncoder<BufferedImage> encoder = new BufferedImageDirectEncoder(width, height);
		byte[] data = encoder.encode(image);
		
		int decompressedLength = data.length;
		
		byte[] compressed = compressor.compress(data);
		
		System.out.println("decompressed data: "+decompressedLength);
		System.out.println("compressed data: "+compressed.length);
		
		String head = PREFIX_SEND_RAW_DATA+" "+decompressedLength+" ";
		byte[] s = ByteMessageUtils.concatenate(head.getBytes(), compressed);
		sendTCP(s);
		//sendUDP(s);
	}
	
	public void sendYUVData(BufferedImage image) throws IOException {
		int width = image.getWidth();
		int height = image.getHeight();
		
		ImageEncoder<BufferedImage> encoder = new BufferedImageYUVDirectEncoder(width, height);
		byte[] data = encoder.encode(image);
		
		int decompressedLength = data.length;
		
		byte[] compressed = compressor.compress(data);
		
		System.out.println("decompressed data: "+decompressedLength);
		System.out.println("compressed data: "+compressed.length);
		
		String head = PREFIX_SEND_YUV_DATA+" "+decompressedLength+" ";
		byte[] s = ByteMessageUtils.concatenate(head.getBytes(), compressed);
		sendTCP(s);
		//sendUDP(s);
	}
	
	public static Compressor<byte[]> buildCompressor() {
		//return new LZCompressor();
		return new GZipCompressor();
	}
	
}