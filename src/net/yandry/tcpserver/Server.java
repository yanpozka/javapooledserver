package net.yandry.tcpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.yandry.utils.Util;

/**
 * Class tcp/ip server to listen on one given port and start the communication
 * 
 * @author yandry pozo
 */
public class Server {

	private int port;
	private ServerSocket serverSocket;
	private ExecutorService threadPool;
	private boolean isStopped;

	public Server(int port) {
		threadPool = Executors.newFixedThreadPool(7); // lucky number
		this.port = port;
		this.isStopped = false;
	}

	public void startComunication() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			Util.error(String.format(
					"[-Server-] Error. Could not listen on port: %d", port), e);
		}
		System.out.format("[+Server+] Started the server on port: %d\n", port);

		while (!isStopped()) {
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					this.threadPool.shutdown();
					return;
				}
				Util.error("[-Server-] Error accepting client connection", e);
			}
			this.threadPool.execute(new HandleRunnable(clientSocket));
			
			System.out.format("[+Server+] A new client is processed\n", port);
		}
	}

	public synchronized void stop() {
		this.isStopped = true;

	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}
}
