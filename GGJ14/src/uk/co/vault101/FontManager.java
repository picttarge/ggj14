package uk.co.vault101;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class FontManager {

	private static LabelStyle LARGE_LABEL;
	
	public static LabelStyle getLargeLabel() {
		if (LARGE_LABEL == null) {
			LARGE_LABEL = new LabelStyle();
			LARGE_LABEL.font = new BitmapFont(	  Gdx.files.internal("font/adventure.fnt")
												, Gdx.files.internal("font/adventure.png")
												, false);
		}
		
		return LARGE_LABEL;
	}
	
}
