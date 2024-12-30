package fr.univlorraine.sae;

public class Viewport {
	private Vec2 lowerBound, higherBound, divider;
	private int width, height;
	
	public Viewport(int width, int height) throws DrawServException {
		setResolution(width, height);
		setBounds(new Vec2(-1.0, -1.0), new Vec2(1.0, 1.0));
	}
	
	public void setBounds(Vec2 lower, Vec2 higher) throws DrawServException {
		if (higher.x <= lower.x || higher.y <= lower.y)
			throw new DrawServException("Lower bound is higher than higher bound");
		
		lowerBound = lower;
		higherBound = higher;
		divider = higher.sub(lower);
	}
	
	public void setResolution(int width, int height) throws DrawServException {
		if (width <= 0 || height <= 0)
			throw new DrawServException("Resolution too low");
		
		this.width = width;
		this.height = height;
	}
	
	public Vec2 translate(Vec2 v) {
		return v.sub(lowerBound).div(divider).mul(new Vec2((double)width, (double)height));
	}
}
