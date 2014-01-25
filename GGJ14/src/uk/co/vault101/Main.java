package uk.co.vault101;

import uk.co.vault101.screen.GameScreen;
import uk.co.vault101.screen.TitleScreen;

import com.badlogic.gdx.Game;


public class Main extends Game {
	
	TitleScreen titleScreen;
	public GameScreen  gameScreen;
	public boolean humans = true; // default
	
	@Override
	public void create() {
		titleScreen = new TitleScreen(this);
		gameScreen = new GameScreen(this);

		setScreen(titleScreen); // entry point
	}

}
