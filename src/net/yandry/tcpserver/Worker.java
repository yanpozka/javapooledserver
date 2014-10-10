package net.yandry.tcpserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
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
public class Worker implements Runnable {

	private Socket clientSocket;
	private Random random;

	public Worker(Socket clientSocket) {
		this.clientSocket = clientSocket;
		random = new Random();
	}

	@Override
	public void run() {
		// System.out.format("[+Server+] A new client is processed\n");

		int seconds = random.nextInt(5) + 1;
		try {
			Thread.sleep(seconds * 1000);
			InputStream input = clientSocket.getInputStream();
			short message_id = 0;

			BufferedInputStream br = new BufferedInputStream(input);
			// we are sure that message length is less than 12
			byte[] request = new byte[12];

			int total = br.read(request);
			if (total < 6) {
				Util.error(
						"[-Server-] Impossible to get the right message from client",
						null);
			}

			// bytes fifth and sixth give the id
			byte[] bytes_id = new byte[] { request[4], request[5] };
			ShortBuffer sb = ByteBuffer.wrap(bytes_id)
					.order(ByteOrder.BIG_ENDIAN).asShortBuffer();
			message_id = sb.get();

			OutputStream output = clientSocket.getOutputStream();
			output.write(this.getMessageBytes(seconds, message_id));

			output.close();
			input.close();

		} catch (IOException | InterruptedException e) {
			Util.error("[-] Error tring to get and/or put client data", e);
		}
	}

	/**
	 * @param seconds
	 * 
	 * @return The byte representation of the message to transmit
	 * @throws IOException
	 */
	private byte[] getMessageBytes(int seconds, short id) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		String response = String.format("The server waited for %d seconds",
				seconds);
		// always will be two bytes for the message id
		outputStream.write(Util.getBytes(2 + response.length()));

		outputStream.write(Util.getBytes(id));

		outputStream.write(response.getBytes());

		return outputStream.toByteArray();
	}

}
