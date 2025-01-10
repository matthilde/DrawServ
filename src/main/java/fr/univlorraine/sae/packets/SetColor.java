package fr.univlorraine.sae.packets;

import fr.univlorraine.sae.Packet;
import fr.univlorraine.sae.Response;
import fr.univlorraine.sae.ServeurThread;
import fr.univlorraine.sae.responses.Ok;

import java.awt.Color;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Selectionne la couleur de dessin. Les couleurs sont comprises entre 0 et 255 chacune.
 *
 * Usage cote client:
 * 		COLOR rouge vert bleu
 * Exemple d'utilisation:
 * 		COLOR 128 255 34
 */
public class SetColor extends Packet {
	public SetColor(ServeurThread st) {
		super(st);
	}
	
	@Override
	protected Response handleScanner(Scanner msg) throws InputMismatchException {
		String command = msg.next().trim();
		if (!command.equalsIgnoreCase("COLOR")) {
			return null;
		}

		final int r, g, b;
		r = msg.nextInt() & 255;
		g = msg.nextInt() & 255;
		b = msg.nextInt() & 255;
		
		sThread.graphics().setColor(new Color(r, g, b));

		return new Ok();
	}
}
