package net.yandry.apptest;

import net.yandry.tcpclient.Client;
import net.yandry.tcpserver.Server;
import net.yandry.utils.Util;

/**
 * Start the server and the client
 * 
 * @author yandry pozo
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		Util.DEBUG = true;
		int port = 1989;
		
		Server server = new Server(port);

		new Thread(server).start();

		new Client(port);
	}
}