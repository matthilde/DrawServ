package fr.univlorraine.sae.packets;

import fr.univlorraine.sae.Packet;
import fr.univlorraine.sae.Response;
import fr.univlorraine.sae.ServeurThread;
import fr.univlorraine.sae.Vec2;
import fr.univlorraine.sae.responses.Ok;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Ecrit du texte a l'ecran.
 *
 * Usage cote client:
 * 		TEXT x y text
 * Exemple:
 *      TEXT -1.0 0.34 Hello, World!
 */
public class Text extends Packet {
    public Text(ServeurThread st) {
        super(st);
    }

    @Override
    protected Response handleScanner(Scanner msg) throws InputMismatchException {
        String command = msg.next().trim();
        if (!command.equalsIgnoreCase("TEXT")) {
            return null;
        }

        final double x, y;
        x = msg.nextDouble();
        y = msg.nextDouble();

        Vec2 coords = sThread.viewport().translate(new Vec2(x, y));

        msg.useDelimiter("\\A");
        String text = msg.next();

        sThread.graphics().drawString(text, (int)coords.x, (int)coords.y);

        return new Ok();
    }
}
