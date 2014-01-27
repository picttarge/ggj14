package uk.co.vault101.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Icon extends Actor {

	private final Texture texture;
	
	public Icon(Texture texture) {
		this.texture = texture;
		setBounds(0,0, texture.getWidth(), texture.getHeight());
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY());
	}

}
