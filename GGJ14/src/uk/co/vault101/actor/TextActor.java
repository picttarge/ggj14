package uk.co.vault101.actor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TextActor extends Label {

	public TextActor(String text, float yPosition, float screenWidth, LabelStyle style) {
		super(text, style);
		
		BitmapFont font = style.font;
		
		setSize(font.getBounds(text).width, font.getBounds(text).height);
		setOrigin(getWidth()/2, getHeight()/2);
		setPosition((screenWidth/2) - (font.getBounds(text).width/2), yPosition-font.getBounds(text).height);
	}
}
