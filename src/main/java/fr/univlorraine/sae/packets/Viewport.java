package fr.univlorraine.sae.packets;

import fr.univlorraine.sae.*;
import fr.univlorraine.sae.responses.Err;
import fr.univlorraine.sae.responses.Ok;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Change les coordonnees monde-ecran
 *
 * Usage cote client:
 * 		VIEWPORT x1 y1 x2 y2
 * Exemple:
 * 		VIEWPORT -1.0 -0.95 0.5 0.2
 */
public class Viewport extends Packet {
    public Viewport(ServeurThread st) {
        super(st);
    }

    @Override
    protected Response handleScanner(Scanner msg) throws InputMismatchException {
        String command = msg.next().trim();
        if (!command.equalsIgnoreCase("VIEWPORT")) {
            return null;
        }

        // Lit les doubles
        final double x1, y1, x2, y2;
        x1 = msg.nextDouble();
        y1 = msg.nextDouble();
        x2 = msg.nextDouble();
        y2 = msg.nextDouble();

        try {
            sThread.viewport().setBounds(new Vec2(x1, y1), new Vec2(x2, y2));
            return new Ok();
        } catch (DrawServException e) {
            return new Err(e.getMessage());
        }
    }
}
