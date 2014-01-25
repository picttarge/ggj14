package uk.co.vault101;

import com.badlogic.gdx.Game;


public class Main extends Game {
	
	TitleScreen titleScreen;
	GameScreen  gameScreen;
	boolean humans = true; // default
	
	@Override
	public void create() {
		titleScreen = new TitleScreen(this);
		gameScreen = new GameScreen(this);

		setScreen(titleScreen); // entry point
	}

}
