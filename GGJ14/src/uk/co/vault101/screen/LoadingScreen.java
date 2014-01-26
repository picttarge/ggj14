package uk.co.vault101.screen;

import uk.co.vault101.FontManager;
import uk.co.vault101.Main;
import uk.co.vault101.actor.TextActor;
import uk.co.vault101.sound.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LoadingScreen implements Screen {

	private final Main game;
	private int screenWidth;
	private int screenHeight;
	private Stage stage;
	private long rendercount = 0;
	
	public static final String LOADING_TEXT = "LOADING....";
	
	public LoadingScreen(Main game) {
		this.game = game;
	}
	
	@Override
	public void dispose() {
		if (stage!=null) {
			stage.dispose();
		}
		
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void resize(int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
		
		stage.setViewport(width, height, true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		if (rendercount == 2) {
			game.setScreen(ScreenManager.getGameScreen());
		}
		rendercount++;
	}

	@Override
	public void show() {
		
		SoundManager.stopThemeTune();
		SoundManager.playLoadingTune();
		
		stage = new Stage();
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		TextActor loadingText = new TextActor(LOADING_TEXT, (screenHeight/2)+FontManager.getLargeLabel().font.getBounds(LOADING_TEXT).height, screenWidth, FontManager.getLargeLabel());
        stage.addActor(loadingText);
	}

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void hide() { }
}