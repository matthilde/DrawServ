package fr.univlorraine.sae;

import fr.univlorraine.sae.packets.Default;
import fr.univlorraine.sae.responses.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Thread serveur, gere la fenetre pour un client et traite ses requetes.
 */
public class ServeurThread extends Thread {
	// Numero client attribue par Serveur.run()
	public final int num_client;
	// Connexion avec le client
	private final Socket sock;

	// Objets AWT
	private final Frame frame;
	private final Graphics graphics;
	private BufferStrategy strat;

	// Viewport
	private Viewport viewport;

	// Chaine de responsabilite des packets
	private Packet packets;

	/**
	 * Ecrit une ligne de log en affichant le numero client.
	 *
	 * @param msg Message a afficher dans les logs.
	 */
	public void log(String msg) {
		System.out.printf("[CLIENT %d] %s\n", num_client, msg);
	}

	/**
	 * Cree une instance de ServeurThread a partir du numero client et de la socket de connexion.
	 * Il va egalement creer une fenetre de dessin.
	 *
	 * @param num_client
	 * @param sock
	 */
	public ServeurThread(int num_client, Socket sock) {
		this.num_client = num_client;
		this.sock = sock;
		
		frame = new Frame("Window " + num_client);
		frame.setBounds(200, 200, 800, 600);
		
		frame.setVisible(true);
		frame.setIgnoreRepaint(false);
		
		final int numBuffers = 2;
		frame.createBufferStrategy(numBuffers);
		
		frame.setBackground(Color.WHITE);
		
		do {
			strat = frame.getBufferStrategy();
		} while (strat == null);
		
		graphics = strat.getDrawGraphics();
		
		try {
			viewport = new Viewport(800, 600, new Vec2(-1.0, -1.0), new Vec2(1.0, 1.0));
		} catch (DrawServException e) {
			viewport = null;
		}
		
		packets = Default.defaultPacketChain(this);
	}

	// Getters des objets AWT pour utilisation dans les classes Packet
	public Frame frame() { return this.frame; }
	public Graphics graphics() { return this.graphics; }
	public BufferStrategy strat() { return this.strat; }
	public Viewport viewport() { return this.viewport; }

	/**
	 * Interprete les paquets a travers la chaine de responsabilite. Renvoie la reponse UnknownCommand si aucun element
	 * de la chaine a pu traiter la requete donnee.
	 *
	 * @param command Requete a traiter
	 * @return Reponse du serveur en fonction de la requete.
	 */
	private Response interpretPacket(String command) {
		command = command.trim();
		Packet currentPacket = packets;
		Response response = null;
		
		while (response == null && currentPacket != null) {
			response = currentPacket.handle(command);
			if (response == null) {
				currentPacket = currentPacket.getNext();
			}
		}

		if (response == null) response = new UnknownCommand();
		return response;
	}

	/**
	 * Detruit la fenetre.
	 */
	private void unexist() {
		frame.setVisible(false);
	}

	/**
	 * Fonction principale du thread pour traiter les requetes client. Tourne indefiniment jusqu'a qu'une erreur
	 * intervienne ou que le client se deconnecte.
	 */
	public void run() {
		try {
			// Streams entrantes et sortantes du Socket
			PrintStream outp   = new PrintStream(sock.getOutputStream());
			BufferedReader inp = new BufferedReader(new InputStreamReader(sock.getInputStream()));

			// Traitement indefinit.
			while (true) {
				String line = inp.readLine();
				if (line == null) {
					log("Connection terminated.");
					break;
				} else {
					log("Received " + line);
				}
				
				try {
					outp.println(interpretPacket(line).send());
				} catch (Exception e) {
					outp.println(new Err(e.getMessage()));
				}
				
				outp.flush();
			}
			
			sock.close();
		} catch (IOException e) {
			// Ecrit dans les logs l'erreur en cas de crash.
			log("ERROR! Shutting down thread. Stacktrace below.");
			e.printStackTrace();
		}
		
		unexist();
	}
}
