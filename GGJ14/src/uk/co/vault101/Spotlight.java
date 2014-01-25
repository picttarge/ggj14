package uk.co.vault101;

public class Spotlight {
	int topYLeft;
	int topYRight;
	int bottomYLeft;
	int bottomYRight;
	final float w;
	final float h;
	
	public Spotlight (float w, float h, int topXLeft, int topXRight, int bottomXLeft, int bottomXRight) {
		this.w = w;
		this.h = h;
		this.topYLeft = topXLeft;
		this.topYRight = topXRight;
		this.bottomYLeft = bottomXLeft;
		this.bottomYRight = bottomXRight;
	}
	
	public boolean isPointLit(float x, float y) {
		
		if (h-y < lerp(topYLeft, topYRight, (w-x)/w)) {
			return false;
		}
		if (h-y > lerp(bottomYLeft, bottomYRight, (w-x)/w)) {
			return false;
		}
		return true;
	}
	
	float lerp(float a, float b, float f)
	{
	    return a + f * (b - a);
	}
}
