package uk.co.vault101;

public class Spotlight {
	int leftTop;
	int leftBottom;
	int rightTop;
	int rightBottom;
	final float screenHeight;
	
	public Spotlight (float screenHeight, int leftTop, int leftBottom, int rightTop, int rightBottom) {
		this.screenHeight = screenHeight;
		this.leftTop = leftTop;
		this.leftBottom = leftBottom;
		this.rightTop = rightTop;
		this.rightBottom = rightBottom;
	}
	
	boolean isPointLit(float x, float y) {
		return false; // fix
	}
	
	float lerp(float a, float b, float f)
	{
	    return a + f * (b - a);
	}
}
