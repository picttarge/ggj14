package uk.co.vault101;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mask extends Actor {
	
	private final TextureRegion region50;
	private final TextureRegion region;
	private final TextureRegion regionBlack;

	private TextureRegion inuse;

	private final float width;
	private final float height;
	
	public boolean isLightOn = false;
	public static float rot = 0.0f;
	private float time = 0.0f;
	private final float margin;
	private final float amplitude = 3;

	public Mask(final float width, final float height) {
		this.width = width;
		this.height = height;
		
		this.margin = 2*(float)((height-392)*Math.tan(Math.toRadians(amplitude)));
		this.setBounds(0,0,width,height);
		
		final Texture textureA = new Texture(
				Gdx.files.internal("image/mask50.png"));
		textureA.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		region50 = new TextureRegion(textureA, 0, 0, 1024, 640);
		
		final Texture textureB = new Texture(
				Gdx.files.internal("image/mask.png"));
		textureB.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		region = new TextureRegion(textureB, 0, 0, 1024, 640);

		final Texture textureC = new Texture(
				Gdx.files.internal("image/maskall32.png"));
		textureC.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		regionBlack = new TextureRegion(textureC, 0, 0, 32,32);
	}

	public void lightOn(boolean full) {
		inuse = full ? region : region50;
		isLightOn = true;
		time = 0;
	}

	public void lightOff() {
		inuse = regionBlack;
		isLightOn = false;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		this.setOrigin(width, height-392);
		float freq = 0.15f; //oscillations per second 
		 
		float decay = 0; //no decay
		time += delta;
		rot = (float) (amplitude*Math.sin(freq*time*2*Math.PI)/Math.exp(decay*time));
		this.setRotation(rot);
	}

	@Override
	public void draw(final SpriteBatch batch, final float parentAlpha) {

		final Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(inuse, getX()-(margin/2), getY()-(margin/2), getOriginX(), getOriginY(),
				getWidth()+margin, getHeight()+margin, getScaleX(), getScaleY(),
				getRotation());

	}

}
