package net.yandry.serverexample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static boolean DEBUG = false;
	private final int PORT = 21205;
	private ServerSocket serverSock;
	
	public Server() {
		try {
			serverSock = new ServerSocket(PORT);
		} catch (IOException e) {
			error(String.format("[-] Error. Could not listen on port: %d", PORT), e);
		}
		System.out.println(String.format("[+] Started the server on port: %d", PORT));
	}
	
	public void startComunication() {
		Socket clientSocket = null;
        try {
            clientSocket = serverSock.accept();
            
        } catch (IOException e) {
            error("Accept failed.", e);
        }
	}
	
	private void error(String msj, Exception e) {
		System.out.println(msj);
		if (DEBUG)
			e.printStackTrace();
		System.exit(-1);
	}

	/**
	 * When the GC comes here it should be nice if the serverSock close
	 */
	@Override
	protected void finalize() throws Throwable {
		if (serverSock != null) {
			serverSock.close();
			System.out.println("DIMEEE");
		}
	}
}
