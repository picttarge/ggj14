package uk.co.vault101.screen;

import uk.co.vault101.FontManager;
import uk.co.vault101.Main;
import uk.co.vault101.actor.MultilineTextActor;
import uk.co.vault101.actor.TextActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class HowToPlayScreen implements Screen, InputProcessor {

	private final Main game;
	private int screenWidth;
	private int screenHeight;
	private Stage stage;
	
	public static final String HOW_TO_PLAY_TITLE_TEXT = "HOW TO PLAY";
	public static final String HOW_TO_PLAY_DESCRIPTION_TEXT = 
			"Watch for civilians mixed up in the stampede - rely on the searchlight to show convincts in orange jumpsuits!";
		
	public HowToPlayScreen(final Main game) {
		this.game = game;
		stage = new Stage();
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		final TextActor titleActor = new TextActor(HOW_TO_PLAY_TITLE_TEXT, screenHeight, screenWidth, FontManager.getLargeLabel());
        stage.addActor(titleActor);
        
        final MultilineTextActor descriptionMultilineTextActor = new MultilineTextActor(HOW_TO_PLAY_DESCRIPTION_TEXT, titleActor.getY(), screenWidth, FontManager.getNormalLabel());
        for (TextActor descriptionTextActor : descriptionMultilineTextActor.getTextActors()) {
        	stage.addActor(descriptionTextActor);
        }
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
	}

	@Override
	public void show() {
		        
        final InputMultiplexer multiplexer = new InputMultiplexer();
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