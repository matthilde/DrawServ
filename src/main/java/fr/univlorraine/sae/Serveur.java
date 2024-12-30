package fr.univlorraine.sae;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Serveur {
	public final int port;
	private int compteur_client = 0;
	
	public static void main(String[] args) throws IOException {
		Serveur serv = new Serveur(9090);
		
		System.out.println("DrawServ");
		System.out.println("Now listening...");
		serv.run();
	}
	
	public Serveur(int port) {
		this.port = port;
	}
	
	public void run() throws IOException {
		ServerSocket sock = new ServerSocket(port);
		
		while (true) {
			try {
				Socket conn = sock.accept();
				
				ServeurThread thread = new ServeurThread(compteur_client++, conn);
				thread.start();
			} catch (IOException e) {
				System.out.println("erreur: " + e.getMessage());
			}
		}
	}
}
