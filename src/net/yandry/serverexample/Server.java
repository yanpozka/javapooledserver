package net.yandry.serverexample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static boolean DEBUG = false;
	private int port;
	private ServerSocket serverSocket;
	protected ExecutorService threadPool = Executors.newFixedThreadPool(10);
	private boolean isStopped;

	public Server(int port) {
		this.port = port;
		this.isStopped = false;
	}

	public void startComunication() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			error(String
					.format("[-] Error. Could not listen on port: %d", port),
					e);
		}
		System.out.println(String.format("[+] Started the server on port: %d",
				port));

		while (!isStopped()) {
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("[-] Server Stopped.");
					this.threadPool.shutdown();
					return;
				}
				throw new RuntimeException("[-] Error accepting client connection", e);
			}
			this.threadPool.execute(new HandleRunnable(clientSocket));
		}
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	private void error(String msj, Exception e) {
		System.out.println(msj);
		if (DEBUG)
			e.printStackTrace();
		System.exit(-1);
	}
}
