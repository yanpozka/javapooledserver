package net.yandry.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
	 * 
	 * @return The byte representation of the integer
	 */
	public static byte[] getBytes(int val) {
		return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(val).array();
	}
	
	/**
	 * @param integer
	 * 
	 * @return The byte representation of the integer
	 */
	public static byte[] getBytes(short val) {
		return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(val).array();
	}
}
