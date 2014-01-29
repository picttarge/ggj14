package uk.co.vault101.screen;

import static uk.co.vault101.screen.ScreenManager.initialiseGameScreens;
import uk.co.vault101.FontManager;
import uk.co.vault101.Main;
import uk.co.vault101.actor.TextActor;
import uk.co.vault101.sound.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LoadingScreen implements Screen, InputProcessor {

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
			initialiseGameScreens(game);
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
        
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void hide() { }

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK){
			game.setScreen(ScreenManager.getTitleScreen());
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}