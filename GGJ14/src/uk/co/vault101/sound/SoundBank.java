package uk.co.vault101.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundBank {

	private static final String SOUND_FILE_LOCATION 	= "sound/";
	private static final String THEME_TUNE_LOCATION		= "one-eyed_maestro.ogg";
	
	private static Music THEME_TUNE;
	
	public static void dispose() {
		if (THEME_TUNE != null) {
			THEME_TUNE.dispose();
		}
	}
	
	public static void playThemeTune() {
		if (THEME_TUNE == null) {
			THEME_TUNE = loadSound(THEME_TUNE_LOCATION);
		}
		
		THEME_TUNE.setLooping(true);
		THEME_TUNE.play();
	}

	private static Music loadSound(String soundFile) {
		return Gdx.audio.newMusic(Gdx.files.internal(SOUND_FILE_LOCATION + soundFile));
	}
}