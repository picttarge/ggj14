package uk.co.vault101.actor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TextActor extends Label {

	private BitmapFont font;
	private float screenWidth;
	private float yPosition;
	private float offset;

	//TODO: ADD TEXT ALIGN OPTION
	
	public TextActor(String text, float yPosition, float screenWidth, LabelStyle style) {
		super(text, style);

		this.font = style.font;
		this.screenWidth = screenWidth;
		this.yPosition = yPosition;
		
		resize();
	}
	
	public TextActor(String text, float yPosition, float screenWidth, float offset, LabelStyle style) {
		super(text, style);

		this.font = style.font;
		this.screenWidth = screenWidth;
		this.offset = offset;
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
		setPosition((screenWidth / 2) - (font.getBounds(getText()).width / 2) + offset, yPosition - font.getBounds(getText()).height);
		setBounds(getX(), getY(), font.getBounds(getText()).width, font.getBounds(getText()).height);
	}

}
