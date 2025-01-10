package fr.univlorraine.sae;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/**
 * Classe du programme principal
 */
public class Serveur {
	// Port du serveur a bind
	public final int port;
	// Compteur client par requete. Chaque nouvelle connexion incremente ce compteur
	private int compteur_client = 0;

	/**
	 * Fonction princpiale du programme, execute le serveur indefiniment.
	 *
	 * @param args Arguments, le premier argument doit etre le port.
	 * @throws IOException Si le serveur n'a pas pu etre cree ou qu'une erreur est survenue, il renverra une IOException.
	 */
	public static void main(String[] args) throws IOException {
		Serveur serv = new Serveur(Integer.parseInt(args[0]));
		
		System.out.println("DrawServ");
		System.out.println("Now listening...");
		serv.run();
	}

	/**
	 * Constructeur du Serveur
	 *
	 * @param port Port a bind
	 */
	public Serveur(int port) {
		this.port = port;
	}

	/**
	 * Accepte les connexions clients en parallele indefiniment et les traitent adequatement.
	 */
	public void run() throws IOException {
		ServerSocket sock = new ServerSocket(port);
		
		while (true) {
			try {
				Socket conn = sock.accept();
				
				ServeurThread thread = new ServeurThread(compteur_client++, conn);
				thread.start();
			} catch (IOException e) {
				System.out.println("Erreur: " + e.getMessage());
			}
		}
	}
}
