package net.yandry.tcpclient;

/**
 * Class client for consume the "server"
 * 
 * @author yandry pozo
 */
public class Client {

	public Client(int port) {
		for (int i = 1; i <= 10; i++) {
			new Thread(new Worker(port, i)).start();
		}
	}

}
