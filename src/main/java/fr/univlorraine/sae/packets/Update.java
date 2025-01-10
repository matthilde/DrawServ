package fr.univlorraine.sae.packets;

import fr.univlorraine.sae.Packet;
import fr.univlorraine.sae.Response;
import fr.univlorraine.sae.ServeurThread;
import fr.univlorraine.sae.responses.Ok;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Met a jour l'ecran
 *
 * Usage cote client:
 * 		UPDATE
 */
public class Update extends Packet {
	public Update(ServeurThread st) {
		super(st);
	}
	
	@Override
	protected Response handleScanner(Scanner msg) throws InputMismatchException {
		String command = msg.next().trim();
		if (!command.equalsIgnoreCase("UPDATE")) {
			return null;
		}
		
		sThread.strat().show();
		
		return new Ok();
	}
}
