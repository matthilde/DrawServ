package fr.univlorraine.sae.responses;

import fr.univlorraine.sae.Response;

public class Ok implements Response {
    @Override
    public String send() {
        return "OK";
    }
}
