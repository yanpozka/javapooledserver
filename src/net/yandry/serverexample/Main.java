package net.yandry.serverexample;

public class Main {

	public static void main(String[] args) {
		Server.DEBUG = true;
		
		Server server = new Server(21005);
		
		server.startComunication();
	}
}