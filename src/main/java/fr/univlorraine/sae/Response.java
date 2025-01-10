package fr.univlorraine.sae;

/**
 * Interface pour definir une reponse.
 */
public interface Response {
    /**
     * @return Reponse du serveur a envoyer au client.
     */
    public String send();
}
