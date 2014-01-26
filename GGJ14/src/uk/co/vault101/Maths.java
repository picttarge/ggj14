package uk.co.vault101;

public class Maths {
	public static final float exactDistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt( ((x1-x2)*(x1-x2)) + ((y1-y2)*(y1-y2)));
	}
	
    public static final int approxDistance(int dx, int dy) {
        int h = 0;
        // Replacement for Math.sqrt function (approx. only)
        // hypotenuse = longest side + ( half the short side )
        // Note: With ints, bit shifting is better than any other op.
        if (dx > dy) {
            h = (dx + (dy >> 1));
        } else {
            h = (dy + (dx >> 1));
        }

        return h;
    }
}
