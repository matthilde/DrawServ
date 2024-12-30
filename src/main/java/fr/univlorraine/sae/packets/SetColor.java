package fr.univlorraine.sae.packets;

import fr.univlorraine.sae.Packet;
import fr.univlorraine.sae.ServeurThread;

import java.awt.Color;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SetColor extends Packet {
	public SetColor(ServeurThread st) {
		super(st);
	}
	
	@Override
	protected boolean handleScanner(Scanner msg) throws InputMismatchException {
		String command = msg.next().trim();
		if (!command.equalsIgnoreCase("COLOR")) {
			return false;
		}

		final int r, g, b;
		r = msg.nextInt() & 255;
		g = msg.nextInt() & 255;
		b = msg.nextInt() & 255;
		
		sThread.graphics().setColor(new Color(r, g, b));

		return true;
	}
}
