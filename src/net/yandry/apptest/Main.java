package net.yandry.apptest;

import net.yandry.tcpserver.Server;
import net.yandry.utils.Util;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Util.DEBUG = true;
		Server server = new Server(21005);
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				server.startComunication();
			}
		}).start();
		
		
		Thread.sleep(10000);
		
		server.stop();
	}
}