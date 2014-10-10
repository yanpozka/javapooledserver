package net.yandry.tcpserver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.Random;

import net.yandry.utils.Util;

/**
 * Class to handle the communication when a new client comes
 * 
 * @author yandry pozo
 */
public class HandleRunnable implements Runnable {

	private Socket clientSocket;
	private Random random;

	public HandleRunnable(Socket clientSocket) {
		this.clientSocket = clientSocket;
		random = new Random();
	}

	@Override
	public void run() {
		int seconds = random.nextInt(5) + 1;
		try {
			Thread.sleep(seconds * 1000);
			InputStream input = clientSocket.getInputStream();
			short message_id = 0;

			BufferedInputStream br = new BufferedInputStream(input);
			byte[] request = new byte[12]; // we are sure that message length  is less than 12
			
			int total = br.read(request);
			if (total <= 0) {
				Util.error("[-Server-] Impossible to get message from client",
						null);
			}

			byte[] bytes_id = new byte[] { request[5], request[6] };
			ShortBuffer sb = ByteBuffer.wrap(bytes_id)
					.order(ByteOrder.BIG_ENDIAN).asShortBuffer();
			message_id = sb.get();

			OutputStream output = clientSocket.getOutputStream();
			output.write(Util.getMessageBytes(seconds, message_id));
		} catch (IOException | InterruptedException e) {
			Util.error("[-] Error tring to get and/or put client data", e);
		}
	}

}
