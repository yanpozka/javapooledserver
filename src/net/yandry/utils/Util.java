package net.yandry.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Class with util methods
 * 
 * @author yandry pozo
 */
public class Util {

	public static boolean DEBUG = false;

	public static void error(String msj, Exception e) {
		System.out.println(msj);
		if (DEBUG && e != null)
			e.printStackTrace();
		System.exit(-1);
	}

	/**
	 * @param integer
	 *            seconds
	 * @return The byte representation of the message to transmit
	 * @throws IOException
	 */
	public static byte[] getMessageBytes(int s, short messageId)
			throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		String response = String.format("\"The server waited for %d seconds\"",
				s);
		// always will be two bytes for the message id
		outputStream.write(getBytes(2 + response.length()));

		outputStream.write(getBytes(messageId));

		outputStream.write(response.getBytes());

		return outputStream.toByteArray();
	}

	/**
	 * @param integer
	 *            value
	 * @return The byte representation of the integer
	 */
	public static byte[] getBytes(int val) {
		return ByteBuffer.allocate(4).putInt(val).array();
	}
}
