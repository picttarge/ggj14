package uk.co.vault101.screen;

import uk.co.vault101.Main;

public class ScreenManager {

	private static TitleScreen titleScreen;
	private static GameScreen gameScreen;
	private static LoadingScreen loadingScreen;
	
	public static void initialiseGameScreens(Main game) {
		titleScreen = new TitleScreen(game);
		gameScreen = new GameScreen(game);
		loadingScreen = new LoadingScreen(game);
	}
	
	public static TitleScreen getTitleScreen() {
		return titleScreen;
	}
	
	public static GameScreen getGameScreen() {
		return gameScreen;
	}
	
	public static LoadingScreen getLoadingScreen() {
		return loadingScreen;
	}
}
