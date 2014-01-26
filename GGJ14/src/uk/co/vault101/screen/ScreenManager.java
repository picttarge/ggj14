package uk.co.vault101.screen;

import uk.co.vault101.Main;

public class ScreenManager {

	private static TitleScreen titleScreen;
	private static GameScreen gameScreen;
	
	public static void initialiseGameScreens(Main game) {
		titleScreen = new TitleScreen(game);
		gameScreen = new GameScreen(game);
	}
	
	public static TitleScreen getTitleScreen() {
		return titleScreen;
	}
	
	public static GameScreen getGameScreen() {
		return gameScreen;
	}
}
