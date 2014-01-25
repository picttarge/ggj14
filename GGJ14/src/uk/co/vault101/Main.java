package uk.co.vault101;

import uk.co.vault101.screen.GameScreen;
import uk.co.vault101.screen.TitleScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Main extends Game {
	
	public static SpriteBatch batch;
	TitleScreen titleScreen;
	public GameScreen  gameScreen;
	public boolean humans = true; // default
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		titleScreen = new TitleScreen(this);
		gameScreen = new GameScreen(this);

		setScreen(titleScreen); // entry point
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}
