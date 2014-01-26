package uk.co.vault101;

import static uk.co.vault101.screen.ScreenManager.getTitleScreen;
import static uk.co.vault101.screen.ScreenManager.initialiseGameScreens;
import uk.co.vault101.screen.ScreenManager;
import uk.co.vault101.sound.SoundManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
	
	public static SpriteBatch batch;
	
	@Override
	public void create() {
		
		batch = new SpriteBatch();
		
		initialiseGameScreens(this);
		
		setScreen(getTitleScreen()); // entry point
	}
	
	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		SoundManager.dispose();
		ScreenManager.dispose();
		FontManager.dispose();
	}
}
