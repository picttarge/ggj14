package uk.co.vault101;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class FontManager {

	private static LabelStyle LARGE_LABEL;
	private static LabelStyle NORMAL_LABEL;
	
	private static final String FONT_FILE_LOCATION 	= "font/";
	private static final String FONT_LARGE_NAME		= "adventure";
	private static final String FONT_NORMAL_NAME	= "adventure-28";
	
	public static LabelStyle getLargeLabel() {
		if (LARGE_LABEL == null) {
			LARGE_LABEL = createLabelStyle(FONT_LARGE_NAME);
		}
		
		return LARGE_LABEL;
	}
	
	public static LabelStyle getNormalLabel() {
		if (NORMAL_LABEL == null) {
			NORMAL_LABEL = createLabelStyle(FONT_NORMAL_NAME);
		}
		
		return NORMAL_LABEL;
	}
	
	private static LabelStyle createLabelStyle(String fontName) {
		
		String fontLocation = FONT_FILE_LOCATION + fontName;
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = new BitmapFont(	  Gdx.files.internal(fontLocation + ".fnt")
											, Gdx.files.internal(fontLocation + ".png")
											, false);
		
		return labelStyle;
	}
	
	
}
