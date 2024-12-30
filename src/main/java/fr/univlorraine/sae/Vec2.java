package fr.univlorraine.sae;

public class Vec2 {
	public final double x, y;
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2 add(Vec2 v) {
		return new Vec2(x + v.x, y + v.y);
	}
	
	public Vec2 sub(Vec2 v) {
		return new Vec2(x - v.x, y - v.y);
	}
	
	public Vec2 mul(Vec2 v) {
		return new Vec2(x * v.x, y * v.y);
	}
	
	public Vec2 div(Vec2 v) {
		return new Vec2(x / v.x, y / v.y);
	}
	
	public Vec2 scale(double scale) {
		return new Vec2(x * scale, y * scale);
	}
	
	public boolean equals(Vec2 v) {
		return x == v.x && y == v.y;
	}
}
