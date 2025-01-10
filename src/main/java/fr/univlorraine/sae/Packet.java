package fr.univlorraine.sae;

import java.util.Scanner;

/**
 * Classe abstraite permettant d'implementer un paquet dans la chaine de responsabilite.
 *
 * Le developpeur pourra se charger tout simplemenet de definir la methode .handleScanner().
 */
public abstract class Packet {
	protected Packet next = null;
	protected final ServeurThread sThread;

	/**
	 * @param st ServeurThread qui gere le client. Cela permettera au paquet d'obtenir les objets AWT pour dessiner.
	 */
	public Packet(ServeurThread st) {
		sThread = st;
	}

	/**
	 * @return prochain paquet de la chaine de responsabilite.
	 */
	public Packet getNext() { return next; }

	/**
	 * Definit le prochain paquet de la chaine. Il renvoie le parametre next afin de pouvoir construire la chaine en
	 * une seule ligne. Voir la classe packets.Default pour un exemple pratique.
	 *
	 * @param next Prochain paquet
	 * @return le parametre next
	 */
	public Packet setNext(Packet next) {
		this.next = next;
		return next;
	}

	/**
	 * Traite la requete client a partir d'une chaine de caractere.
	 *
	 * @param msg Ligne de requete
	 * @return Reponse du serveur pour la requete client
	 */
	public Response handle(String msg) {
		Scanner sc = new Scanner(msg).useDelimiter(" ");
		return handleScanner(sc);
	}

	/**
	 * Methode abstraite qui s'occupe de gerer la requete client. Elle prend un Scanner pour faciliter le travail de
	 * parsing. Elle est appelee par la methode handle(), ce qui explique pourquoi c'est une methode protegee.
	 *
	 * @param msg Scanner de la ligne de requete
	 * @return Reponse du serveur pour la requete client
	 */
	protected abstract Response handleScanner(Scanner msg);
}
