package uk.co.vault101.actor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TextActor extends Label {

	private BitmapFont font;
	private float screenWidth;
	private float yPosition;

	public TextActor(String text, float yPosition, float screenWidth, LabelStyle style) {
		super(text, style);

		font = style.font;
		this.screenWidth = screenWidth;
		this.yPosition = yPosition;
		resize();
	}

	@Override
	public void setText(CharSequence newText) {
		super.setText(newText);
		resize();
	}

	private void resize() {
		setSize(font.getBounds(getText()).width, font.getBounds(getText()).height);
		setOrigin(getWidth() / 2, getHeight() / 2);
		setPosition((screenWidth / 2) - (font.getBounds(getText()).width / 2), yPosition - font.getBounds(getText()).height);
	}

}
