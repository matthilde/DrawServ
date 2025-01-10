package fr.univlorraine.sae.packets;

import fr.univlorraine.sae.Packet;
import fr.univlorraine.sae.Response;
import fr.univlorraine.sae.responses.*;
import fr.univlorraine.sae.ServeurThread;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Reinitialise la fenetre de dessin.
 *
 * Utilisation cote client:
 * 		CLEAR
 */
public class Clear extends Packet {
	public Clear(ServeurThread st) {
		super(st);
	}
	
	@Override
	protected Response handleScanner(Scanner msg) throws InputMismatchException {
		String command = msg.next().trim();
		if (!command.equalsIgnoreCase("CLEAR")) {
			return null;
		}
		
		final int width = sThread.frame().getWidth();
		final int height = sThread.frame().getHeight();
		
		sThread.graphics().clearRect(0, 0, width, height);
		
		return new Ok();
	}
}
