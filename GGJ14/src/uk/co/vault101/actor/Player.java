package uk.co.vault101.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Actor {

	private final Texture texture = new Texture(
			Gdx.files.internal("image/player.png"));
	private final Texture textureDead = new Texture(
			Gdx.files.internal("image/blood_splash.png"));
	private boolean alive = true;

	public Player() {
		setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
	}

	@Override
	public void act(final float delta) {
		super.act(delta);
		if (!alive) {
			// TODO?
		}
	}

	@Override
	public void draw(final SpriteBatch batch, final float parentAlpha) {
		batch.draw(alive ? texture : textureDead, getX(), getY());
	}

}
