package fr.univlorraine.sae;

/**
 * Viewport = le port de coordonnées abstraites à une résolution en pixels. Comme on l'a vu avec le tuteur
 */
public class Viewport {
	private Vec2 lowerBound, higherBound, divider;
	private int width, height;
	
	/**
	 * Initialisation des bornes par défaut avec les deux vecteurs(précisé dans setBounds) et résolution donnée en param
	 *
	 * @param width  La largeur de la résolution en pixels.
	 * @param height La hauteur de la résolution en pixels.
	 * @param higher Partie
	 * @throws DrawServException Si la résolution est invalide (largeur ou hauteur ≤ 0).
	 */
	public Viewport(int width, int height, Vec2 lower, Vec2 higher) throws DrawServException {
		setResolution(width, height);
		setBounds(new Vec2(-1.0, -1.0), new Vec2(1.0, 1.0));
	}

	/**
	 * Définit les bornes du viewport dans l'espace abstrait.
	 *
	 * @param lower Les coordonnées du coin inférieur gauche (borne inférieure).
	 * @param higher Les coordonnées du coin supérieur droit (borne supérieure).
	 * @throws DrawServException Si les bornes sont incohérentes
	 *                           (par exemple, lower.x >= higher.x ou lower.y >= higher.y).
	 */
	public void setBounds(Vec2 lower, Vec2 higher) throws DrawServException {
		if (higher.x <= lower.x || higher.y <= lower.y)
			throw new DrawServException("Lower bound is higher than higher bound");
		
		lowerBound = lower;
		higherBound = higher;
		divider = higher.sub(lower);
	}

	/**
	 * Définit la résolution du viewport en pixels.
	 *
	 * @param width  La largeur en pixels.
	 * @param height La hauteur en pixels.
	 * @throws DrawServException Si la résolution est invalide (largeur ou hauteur ≤ 0(classe créée)).
	 */
	public void setResolution(int width, int height) throws DrawServException {
		if (width <= 0 || height <= 0)
			throw new DrawServException("Resolution too low");
		
		this.width = width;
		this.height = height;
	}

	/**
	 * Traduction du vecteur dans l'espace abstrait vers des coordonnées en pixels.
	 *
	 * @param v Le vecteur à traduire (dans l'espace abstrait).
	 * @return Le vecteur traduit (dans l'espace en pixels).
	 */
	public Vec2 translate(Vec2 v) {
		return v.sub(lowerBound).div(divider).mul(new Vec2((double)width, (double)height));
	}
}
