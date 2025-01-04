package fr.univlorraine.sae.packets;

import fr.univlorraine.sae.Packet;
import fr.univlorraine.sae.Response;
import fr.univlorraine.sae.ServeurThread;
import fr.univlorraine.sae.Vec2;
import fr.univlorraine.sae.responses.Ok;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Rectangle extends Packet {
	public Rectangle(ServeurThread st) {
		super(st);
	}
	
	@Override
	protected Response handleScanner(Scanner msg) throws InputMismatchException {
		String command = msg.next().trim();
		if (!command.equalsIgnoreCase("RECTANGLE")) {
			return null;
		}
		
		final double x1, y1, x2, y2;
		x1 = msg.nextDouble();
		y1 = msg.nextDouble();
		x2 = msg.nextDouble();
		y2 = msg.nextDouble();
		
		
		final Vec2 v1, v2;
		v1 = sThread.viewport().translate(new Vec2(x1, y1));
		v2 = sThread.viewport().translate(new Vec2(x2, y2));
		
		sThread.graphics().fillRect((int)v1.x, (int)v1.y, (int)(v2.x - v1.x), (int)(v2.y - v1.y));
		
		return new Ok();
	}
}
