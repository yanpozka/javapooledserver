package net.yandry.tcpclient;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import net.yandry.utils.Util;

public class Worker implements Runnable {

	private int port, id;

	public Worker(int port, int id) {
		super();
		this.port = port;
		this.id = id;
	}

	@Override
	public void run() {
		Socket socket = null;
		try {
			socket = new Socket("localhost", port);
		} catch (IOException e) {
			Util.error(
					"[-Client-] Impossible to connect with localhost on port "
							+ port, e);
		}

		try {
			OutputStream output = socket.getOutputStream();
			byte[] message = this.getMessage(id);

			output.write(message);

			InputStream input = socket.getInputStream();
			BufferedInputStream br = new BufferedInputStream(input);
			byte[] buffer = new byte[255];

			int total = 0;
			while ((total = br.read(buffer)) < 1);

			System.out.println(new String(buffer, 6, total));

			output.close();
			br.close();
		} catch (IOException e) {
			Util.error("[-Client-] Error tring to get the server message", e);
		}
	}

	private byte[] getMessage(int id) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		String response = String.format("Thread-%d", id);
		// always will be two bytes for the message id
		outputStream.write(Util.getBytes(2 + response.length()));

		outputStream.write(Util.getBytes((short) id));

		outputStream.write(response.getBytes());

		return outputStream.toByteArray();
	}

}
