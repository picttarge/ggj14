package uk.co.vault101.actor;

import java.util.Random;

import uk.co.vault101.Maths;
import uk.co.vault101.screen.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Beastie extends Actor {
	/** const */
	private final Random random = new Random();
	private final Sound soundKilled = Gdx.audio.newSound(Gdx.files
			.internal("sound/173126__replix__death-sound-male.ogg"));
	private final Sound soundFF = Gdx.audio.newSound(Gdx.files
			.internal("sound/173126__replix__death-sound-male_pitched.ogg"));

	private final Sound soundShooting = Gdx.audio.newSound(Gdx.files
			.internal("sound/shotgun.ogg"));

	private final Sound soundOutOfRange = Gdx.audio.newSound(Gdx.files
			.internal("sound/duff.ogg"));

	private final float w;
	private final float h;
	private final float min_walk_speed = 50;
	private final Texture[] textures;

	/** vars */
	private Texture texture;
	private boolean alive = true;
	private boolean convict = false;
	private boolean illuminated = false;
	private float alpha = 1.0f;
	private float speed;

	public Beastie(final Texture[] textures, final float speed, final float w,
			final float h) {
		this.textures = textures;
		this.speed = speed >= min_walk_speed ? speed : min_walk_speed;
		this.w = w;
		this.h = h;
		texture = textures[((random.nextInt(4)) + 1)];
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

				// TODO ignore pointer > 0
				final Beastie beast = (Beastie) event.getTarget();
				if (!beast.alive) { // already dead, return (nothing to do)
					return false;
				}
				if (Maths.approxDistance(
						(int) Math.abs(x - GameScreen.playerPos.x),
						(int) Math.abs(y - GameScreen.playerPos.y)) < 600) {
					soundShooting.play();
				} else {
					soundOutOfRange.play();
					return true;
				}

				beast.alive = false; // dead now
				alpha = 1.0f;
				if (beast.convict) {
					GameScreen.kills++;
					soundKilled.play();
				} else {
					GameScreen.friendlyFire++;
					soundFF.play();
				}

				return true;
			}
		});
	}

	@Override
	public void act(final float delta) {
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
				} else {
					GameScreen.rescued++;
				}
				reset();
			}
		} else {
			alpha -= 0.5 * delta;
			if (alpha <= 0) {
				// actually gone
				reset();
			}
		}
	}

	public void reset() {
		float speed = 100 * random.nextFloat();
		this.speed = speed >= min_walk_speed ? speed : min_walk_speed;
		this.setX(random.nextFloat() * (w-getWidth()));
		this.setY(h + (20 * random.nextFloat()));
		convict = random.nextFloat() < 0.5f ? true : false;
		texture = textures[((random.nextInt(4)) + 1)];
		illuminated = false;
		alpha = 1.0f;
		if (convict) {
			GameScreen.possibleConvicts++;
		} else {
			GameScreen.possibleCivvies++;
		}
		alive = true;
	}

	@Override
	public void draw(final SpriteBatch batch, final float parentAlpha) {
		final Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * alpha);

		batch.draw(alive ? (illuminated ? textures[0] : texture) : textures[5],
				getX(), getY());

	}

	@Override
	public String toString() {
		return "{speed:" + speed + ",x:" + getX() + ",y:" + getY() + ",alpha:"
				+ alpha + ",lit:" + illuminated + ",convict:" + convict
				+ ",alive:" + alive + ",texture:" + texture;
	}

}
