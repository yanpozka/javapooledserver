package net.yandry.tcpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import net.yandry.utils.Util;

/**
 * Class client for consume the "server"
 * 
 * @author yandry pozo
 */
public class Client {

	private int port;

	public Client(int port) {
		this.port = port;
	}

	public void send10Requests() {
		int count = 10;

		// while (count-- > 0) {
		Socket socket = null;
		try {
			socket = new Socket(InetAddress.getByAddress(null), port);
		} catch (IOException e) {
			Util.error(
					"[-Client-] Impossible to connect with localhost on port "
							+ port, e);
		}

		try {
			BufferedReader bin = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			
			System.out.println( "*** llego esto del servidor ***" );
			System.out.println(bin.readLine());
		} catch (IOException e) {
			Util.error("[-Client-] Error tring to get the server message", e);
		}

		// }

	}

}
