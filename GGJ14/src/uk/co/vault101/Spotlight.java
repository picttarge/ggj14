package uk.co.vault101;

import com.badlogic.gdx.math.Vector2;

public class Spotlight {
	public final Vector2 topLeft;
	public final Vector2 topRight;
	public final Vector2 bottomLeft;
	public final Vector2 bottomRight;
	final float w;
	final float h;
	
	public Spotlight (float w, float h, Vector2 topLeft, Vector2 topRight, Vector2 bottomLeft, Vector2 bottomRight) {
		this.w = w;
		this.h = h;
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
	}
	
	public boolean isPointLit(float x, float y) {
		
		if (y > lerp(topLeft.y, topRight.y, x/w)) {
			return false;
		}
		if (y < lerp(bottomLeft.y, bottomRight.y, x/w)) {
			return false;
		}
		return true;
	}
	
	float lerp(float a, float b, float f)
	{
	    return a + f * (b - a);
	}
}
