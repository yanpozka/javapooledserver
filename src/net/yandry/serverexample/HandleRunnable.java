package net.yandry.serverexample;

import java.net.Socket;

public class HandleRunnable implements Runnable {

	private Socket clientSocket;

	public HandleRunnable(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {

	}

}
