package uk.co.vault101.screen;

import java.util.List;

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

public class CreditsScreen implements Screen, InputProcessor {

	private final Main game;
	private int screenWidth;
	private int screenHeight;
	private Stage stage;
	
	public static final String CREDITS_TITLE = "CREDITS";
	public static final String CREDITS_TEXT = "* Neon Light sound (Creative Commons - Attribution) http://www.freesound.org/people/Glaneur%20de%20sons/sounds/104960/ Glaneur de sons http://creativecommons.org/licenses/by/3.0/ * Music \"One-Eyed Maestro\" Kevin MacLeod (incompetech.com) Licensed under Creative Commons: By Attribution 3.0 http://creativecommons.org/licenses/by/3.0/  * Ambient Music \"ambient02\" yewbic (freesound.org) http://www.freesound.org/people/yewbic/sounds/33703/ Licensed under Creative Commons: Universal 1.0 Public Domain Dedication   * \"Death sound male\" Replix (freesound.org) http://www.freesound.org/people/Replix/sounds/173126/ Licensed under Creative Commons: Universal 1.0 Public Domain Dedication  * Prison Spotlight image Michael Grocott (PhotoBucket) http://s192.photobucket.com/user/mickl22/media/08Mar%20Old%20Fremantle%20Prison/_IGP1262-PBEdit.jpg.html http://photobucket.com/terms (non-commercial use)";
	
	public CreditsScreen(Main game) {
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
	}

	@Override
	public void show() {
		
		stage = new Stage();
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		TextActor titleActor = new TextActor(CREDITS_TITLE, screenHeight, screenWidth, FontManager.getLargeLabel());
        stage.addActor(titleActor);
        
        MultilineTextActor creditsMultilineTextActor = new MultilineTextActor(CREDITS_TEXT, titleActor.getY(), screenWidth, FontManager.getNormalLabel());
        List<TextActor> creditsTextActors = creditsMultilineTextActor.getTextActors();
        for (TextActor creditsTextActor : creditsTextActors) {
        	stage.addActor(creditsTextActor);
        }
        
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