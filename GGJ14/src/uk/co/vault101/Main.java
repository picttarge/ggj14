package uk.co.vault101;

import uk.co.vault101.screens.GameScreen;
import uk.co.vault101.screens.TitleScreen;

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
