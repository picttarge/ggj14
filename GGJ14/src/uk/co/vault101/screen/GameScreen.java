package uk.co.vault101.screen;

import java.util.Random;

import uk.co.vault101.Main;
import uk.co.vault101.Mask;
import uk.co.vault101.Spotlight;
import uk.co.vault101.actor.Beastie;
import uk.co.vault101.actor.Player;
import uk.co.vault101.terrain.Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class GameScreen implements Screen {

	Main game;
	private Stage stage;
	private Random random = new Random();
	public static Spotlight spotlight;
	public static Vector2 playerPos;
	public static int kills = 0;
	public static int friendlyFire = 0;
	public static int escapees = 0;
	public static int rescued = 0;
	public static int possibleConvicts = 0;
	public static int possibleCivvies = 0;
	
	public static boolean acting = false;

	public GameScreen(Main game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (acting) {
			stage.act(Gdx.graphics.getDeltaTime());
		}
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {

		stage = new Stage();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		playerPos = new Vector2(w/2,0);
		
		// first the ground
		Actor background = new Background("image/terrain.png");
		background.setSize(w, h);
		background.setOrigin(w / 2, h / 2);
		background.setPosition(0, 0);
		background.setTouchable(Touchable.enabled);
		stage.addActor(background);
		
		// then the player's base
		
		
		// then the player
		Actor player = new Player();
		player.setPosition(playerPos.x, playerPos.y);
		stage.addActor(player);

		// then the beasties
		final int max_beasties = 32;
		for (int i = 0; i < max_beasties; i++) {
			Actor beast = new Beastie((100 * random.nextFloat()) + 20);

			beast.setX(((w / max_beasties) * i) + (w / (max_beasties << 1)));
			beast.setY(h + (20 * random.nextFloat())+100);

			beast.setTouchable(Touchable.enabled);
			// visible by default.
			stage.addActor(beast);
		}

		// then the light mask
		Actor mask = new Mask("image/mask.png", w, h);
		mask.setSize(w, h);
		mask.setOrigin(mask.getWidth() / 2, mask.getHeight() / 2);
		mask.setPosition(0, 0);
		mask.setTouchable(Touchable.disabled);
		stage.addActor(mask);

		// spotlight definition
		spotlight = new Spotlight(w, h, 
				new Vector2(0,1075), new Vector2(w,980), new Vector2(0,708), new Vector2(w,860));

		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();

	}

}
