package uk.co.vault101;

import static uk.co.vault101.screen.ScreenManager.getTitleScreen;
import static uk.co.vault101.screen.ScreenManager.initialiseGameScreens;

import com.badlogic.gdx.Game;

public class Main extends Game {
	
	@Override
	public void create() {
		initialiseGameScreens(this);
		setScreen(getTitleScreen()); // entry point
	}

}
