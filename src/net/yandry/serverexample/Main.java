package net.yandry.serverexample;

public class Main {

	public static void main(String[] args) {
		Server server = new Server();
		Server.DEBUG = true;
		server.startComunication();
	}
}