package uk.co.vault101;

public class Maths {
	public static final float exactDistance(final float x1, final float y1,
			final float x2, final float y2) {
		return (float) Math.sqrt(((x1 - x2) * (x1 - x2))
				+ ((y1 - y2) * (y1 - y2)));
	}

	public static final int approxDistance(final int dx, final int dy) {
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
