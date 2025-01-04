package fr.univlorraine.sae.responses;

import fr.univlorraine.sae.Response;

public class Err implements Response {
    private String message;

    public Err(String message) {
        this.message = message;
    }

    public String send() {
        return String.format("ERR %s", message);
    }
}
