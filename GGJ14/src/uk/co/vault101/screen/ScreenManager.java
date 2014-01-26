package uk.co.vault101.screen;

import uk.co.vault101.Main;

public class ScreenManager {

	private static TitleScreen titleScreen;
	private static GameScreen gameScreen;
	private static LoadingScreen loadingScreen;
	private static CreditsScreen creditsScreen;
	private static HowToPlayScreen howToPlayScreen;
	
	public static void initialiseGameScreens(Main game) {
		titleScreen = new TitleScreen(game);
		gameScreen = new GameScreen(game);
		loadingScreen = new LoadingScreen(game);
		creditsScreen = new CreditsScreen(game);
		howToPlayScreen = new HowToPlayScreen(game);
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
	
	public static CreditsScreen getCreditsScreen() {
		return creditsScreen;
	}
	
	public static HowToPlayScreen getHowToPlayScreen() {
		return howToPlayScreen;
	}
	
	public static void dispose() {
		if (titleScreen != null) {
			titleScreen.dispose();
		}
		
		if (gameScreen != null) {
			gameScreen.dispose();
		}
		
		if (loadingScreen != null) {
			loadingScreen.dispose();
		}
		
		if (creditsScreen != null) {
			creditsScreen.dispose();
		}
		
		if (howToPlayScreen != null) {
			howToPlayScreen.dispose();
		}
	}
}
