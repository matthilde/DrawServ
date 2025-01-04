package fr.univlorraine.sae;

import java.util.Scanner;

public abstract class Packet {
	protected Packet next = null;
	protected final ServeurThread sThread;
	
	public Packet(ServeurThread st) {
		sThread = st;
	}
	
	public Packet getNext() { return next; }
	public Packet setNext(Packet next) {
		this.next = next;
		return next;
	}	
	
	public Response handle(String msg) {
		Scanner sc = new Scanner(msg).useDelimiter(" ");
		return handleScanner(sc);
	}
	
	protected abstract Response handleScanner(Scanner msg);
}
