package fr.univlorraine.sae.responses;

import fr.univlorraine.sae.Response;

public class UnknownCommand implements Response {
    @Override
    public String send() {
        return "UNKNOWN_COMMAND";
    }
}
