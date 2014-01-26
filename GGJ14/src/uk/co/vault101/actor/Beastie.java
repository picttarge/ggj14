package uk.co.vault101.actor;

import java.util.Random;

import uk.co.vault101.Maths;
import uk.co.vault101.screen.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Beastie extends Actor {
	private Random random = new Random();
	final Texture texture;
	final Texture textureDead = new Texture(
			Gdx.files.internal("image/blood_splash.png"));
	final Texture textureIlluminated = new Texture(
			Gdx.files.internal("image/civilian0.png")); // convict
	
	final Sound soundKilled = Gdx.audio.newSound(Gdx.files
			.internal("sound/splat0.ogg"));
	final Sound soundFF = Gdx.audio.newSound(Gdx.files
			.internal("sound/splat1.ogg"));
	
	final Sound soundShooting = Gdx.audio.newSound(Gdx.files
			.internal("sound/shotgun.ogg"));
	
	final Sound soundOutOfRange = Gdx.audio.newSound(Gdx.files
			.internal("sound/duff.ogg"));
	
	private boolean alive = true;
	private boolean convict = false;
	private boolean illuminated = false;

	private float speed;

	public Beastie(float speed) {
		this.speed = speed;
		texture = new Texture(Gdx.files.internal("image/civilian"
				+ ((random.nextInt(4)) + 1) + ".png"));
		convict = random.nextFloat() < 0.5f ? true : false;
		if (convict) {
			GameScreen.possibleConvicts++;
		} else {
			GameScreen.possibleCivvies++;
		}
		setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
		addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("touchDown on Beastie: (" + x + "," + y
						+ ") " + pointer + " " + button);
				
				Beastie beast = (Beastie) event.getTarget();
				if (!beast.alive) {
					return false;
				}
				if (Maths.approxDistance((int)Math.abs(x-GameScreen.playerPos.x), (int)Math.abs(y-GameScreen.playerPos.y)) < 600) {
            		soundShooting.play();
            	} else {
            		soundOutOfRange.play();
            		return true;
            	}

				
				beast.alive = false;
				if (beast.convict) {
					GameScreen.kills++;
					soundKilled.play();
					System.out.println("#KILLs now at "+GameScreen.kills);
				} else {
					GameScreen.friendlyFire++;
					soundFF.play();
					System.out.println("#friendlyFire now at "+GameScreen.friendlyFire);
				}

				return true;
			}
		});
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (alive) {
			this.setY(this.getY() - (speed * delta));
			if (convict) {
				// test if in spotlight
				illuminated = GameScreen.spotlight.isPointLit(getX(), getY());
			}
			if (getY() < 0) {
				
				if (convict) {
					GameScreen.escapees++;
					System.out.println("#escapees now at "+GameScreen.escapees+" ("+GameScreen.possibleConvicts+" convicts encountered)");
				} else {
					GameScreen.rescued++;
					System.out.println("#rescued now at "+GameScreen.rescued+" ("+GameScreen.possibleCivvies+" civvies encountered)");
				}
				alive = false;
				setVisible(false); // will not draw either
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// System.out.println("trying to draw actor");
		// Color color = getColor();
		// batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		// batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
		// getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

		batch.draw(alive ? (illuminated ? textureIlluminated : texture)
				: textureDead, getX(), getY());

	}

}
