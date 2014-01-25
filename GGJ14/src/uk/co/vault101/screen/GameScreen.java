package uk.co.vault101.screen;

import java.util.Random;

import uk.co.vault101.Main;
import uk.co.vault101.Mask;
import uk.co.vault101.Spotlight;
import uk.co.vault101.actor.Beastie;
import uk.co.vault101.terrain.Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class GameScreen implements Screen {

	Main game;
	private Stage stage;
	private Random random = new Random();
	public static Spotlight spotlight;

	public GameScreen(Main game) {
		this.game = game;
	}

	private void update() {

	}

	@Override
	public void render(float delta) {
		update();

		if (game.humans) {
			Gdx.gl.glClearColor(0, 0,
					(float) Math.sin(System.currentTimeMillis() / 100), 1);
		} else {
			Gdx.gl.glClearColor(
					(float) Math.sin(System.currentTimeMillis() / 100), 0, 0, 1);
		}
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
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
		System.out.println("graphics W: "+w+" graphics H: "+h);

		// first the ground
		Actor background = new Background("image/terrain.png");
		background.setSize(w, h);
		background.setOrigin(w/2,h/2);
		background.setPosition(0,0);
		stage.addActor(background);

		// then the beasties
		final int max_beasties = 10;
		for (int i = 0; i < max_beasties; i++) {
			Actor beast = new Beastie((100*random.nextFloat())+20); // at least
			
			beast.setX(((w / max_beasties) * i)+(w/(max_beasties<<1)));
			beast.setY(h-20*random.nextFloat());
			
			beast.setTouchable(Touchable.enabled);

			// visible by default.
			stage.addActor(beast);
		}

		// then the light mask
		Actor mask = new Mask("image/mask.png",w,h);
		mask.setSize(w, h);
		mask.setOrigin(mask.getWidth()/2, mask.getHeight()/2);
		mask.setPosition(0,0);
		mask.setTouchable(Touchable.disabled);
		stage.addActor(mask);
		
		// spotlight definition
		spotlight = new Spotlight(h, 122,72, 150, 200);

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
