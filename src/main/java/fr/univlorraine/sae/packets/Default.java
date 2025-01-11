package fr.univlorraine.sae.packets;

import fr.univlorraine.sae.Packet;
import fr.univlorraine.sae.ServeurThread;

public class Default {
	/**
	 * @param st Serveur Thread
	 * @return
	 */
	public static Packet defaultPacketChain(ServeurThread st) {
		Packet p = new Clear(st);
		
		p	.setNext(new Rectangle(st))
			.setNext(new SetColor(st))
			.setNext(new Update(st))
			.setNext(new Line(st))
			.setNext(new Ellipse(st))
			.setNext(new Viewport(st))
			.setNext(new Text(st));

		return p;
	}
}
