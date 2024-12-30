package fr.univlorraine.sae;

import fr.univlorraine.sae.packets.Default;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServeurThread extends Thread {
	public final int num_client;
	private final Socket sock;
	private final Frame frame;
	private final Graphics graphics;
	private BufferStrategy strat;
	private Viewport viewport;
	
	private Packet packets;
	
	public void log(String msg) {
		System.out.printf("[CLIENT %d] %s\n", num_client, msg);
	}
	
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
			viewport = new Viewport(800, 600);
		} catch (DrawServException e) {
			viewport = null;
		} // ne devrait pas arriver
		
		packets = Default.defaultPacketChain(this);
	}

	public Frame frame() { return this.frame; }
	public Graphics graphics() { return this.graphics; }
	public BufferStrategy strat() { return this.strat; }
	public Viewport viewport() { return this.viewport; }
	
	private boolean interpretPacket(String command) {
		command = command.trim();
		Packet currentPacket = packets;
		boolean response = false;
		
		while (!response && currentPacket != null) {
			response = currentPacket.handle(command);
			if (!response) {
				currentPacket = currentPacket.getNext();
			}
		}
		
		return response;
	}
	
	private void unexist() {
		frame.setVisible(false);
	}
	
	public void run() {
		try {
			PrintStream outp   = new PrintStream(sock.getOutputStream());
			BufferedReader inp = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			while (true) {
				String line = inp.readLine();
				if (line == null) {
					log("Connection terminated.");
					break;
				} else {
					log("Received " + line);
				}
				
				try {
					if (interpretPacket(line)) {
						outp.println("SUCCESS");
					} else {
						outp.println("UNKNOWN_COMMAND");
					}
				} catch (Exception e) {
					outp.println("ERROR " + e.getMessage());
				}
				
				outp.flush();
			}
			
			sock.close();
		} catch (IOException e) {
			log("ERROR! Shutting down thread. Stacktrace below.");
			e.printStackTrace();
		}
		
		unexist();
	}
}
