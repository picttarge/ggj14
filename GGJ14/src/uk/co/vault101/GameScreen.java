package uk.co.vault101;

import java.util.Random;

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
		Actor background = new Background("data/terrain.png");
		background.setSize(w, h);
		background.setOrigin(w/2,h/2);
		background.setPosition(0,0);
		stage.addActor(background);

		// then the beasties
		final int max_beasties = 50;
		for (int i = 0; i < max_beasties; i++) {
			Actor beast = new Beastie((10*random.nextFloat())+20); // at least
			beast.setX(0);
			beast.setY(((h / max_beasties) * i)+(h/(max_beasties<<1)));
			beast.setTouchable(Touchable.enabled);

			// visible by default.
			stage.addActor(beast);
		}

		// then the light mask
		Actor mask = new Mask("data/mask.png",w,h);
		mask.setSize(w, h);
		mask.setOrigin(mask.getWidth()/2, mask.getHeight()/2);
		mask.setPosition(0,0);
		mask.setTouchable(Touchable.disabled);
		stage.addActor(mask);

		// horrible race condition if it takes longer than 2 seconds to load
		// and please don't be > 1 MB
		// or too high a frequency
		// or 100 other reasons sound isn't "ready"
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
