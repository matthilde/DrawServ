package fr.univlorraine.sae.packets;

import fr.univlorraine.sae.Packet;
import fr.univlorraine.sae.ServeurThread;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Update extends Packet {
	public Update(ServeurThread st) {
		super(st);
	}
	
	@Override
	protected boolean handleScanner(Scanner msg) throws InputMismatchException {
		String command = msg.next().trim();
		if (!command.equalsIgnoreCase("UPDATE")) {
			return false;
		}
		
		sThread.strat().show();
		
		return true;
	}
}
