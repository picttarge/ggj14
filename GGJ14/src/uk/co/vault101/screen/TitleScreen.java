package uk.co.vault101.screen;

import static uk.co.vault101.screen.ScreenManager.getLoadingScreen;
import static uk.co.vault101.sound.SoundManager.playThemeTune;

import java.util.List;

import uk.co.vault101.FontManager;
import uk.co.vault101.Main;
import uk.co.vault101.actor.MultilineTextActor;
import uk.co.vault101.actor.TextActor;
import uk.co.vault101.terrain.ImageActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class TitleScreen implements Screen, InputProcessor {

	private final Main game;
	private int screenWidth;
	private int screenHeight;
	private Stage stage;
	
	public static final String GAME_TITLE = "Jail Break!";
	public static final String GAME_BLURB = "It's 2050 and the prisoners of the largest jail on the planet (population 20,000) have escaped. You are the last guard alive! The safety of the country is in your hands - eliminate all the escaped convicts before they make it out the gate!";
	
	public static final String BUTTON_TEXT_PLAY = "PLAY";
	public static final String BUTTON_TEXT_HOW_TO_PLAY = "HOW TO PLAY";
	public static final String BUTTON_TEXT_CREDITS = "CREDITS"; 
	
	public TitleScreen(Main game) {
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
		
		playThemeTune();
		stage = new Stage();
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		// SHOW BANNER IMAGE
        ImageActor bannerImage = new ImageActor("image/prision.png", 800, 424);
        
        float actualBannerImageWidth = screenWidth*bannerImage.getAspect();
        bannerImage.setSize(screenWidth, actualBannerImageWidth);
        bannerImage.setOrigin(bannerImage.getWidth()/2, bannerImage.getHeight()/2);
        bannerImage.setPosition(0, screenHeight-bannerImage.getHeight());
        stage.addActor(bannerImage);
        
        // SHOW TITLE TEXT
        TextActor titleText = new TextActor(GAME_TITLE, bannerImage.getY(), screenWidth, FontManager.getLargeLabel());
        stage.addActor(titleText);
		
        // SHOW BLURB TEXT
        MultilineTextActor blurbMultilineTextActor = new MultilineTextActor(GAME_BLURB, titleText.getY(), screenWidth, FontManager.getNormalLabel());
        List<TextActor> blurbTextActors = blurbMultilineTextActor.getTextActors();
        
        float previousFieldYPosition = 0;
        
        TextActor blurbTextActor = null;
        for (int i = 0; i < blurbTextActors.size(); i++) {
        	blurbTextActor = blurbTextActors.get(i);
			stage.addActor(blurbTextActor);
        	previousFieldYPosition = blurbTextActor.getY();
        }
        
		int buttonSpacer = 20;
		
		// SHOW PLAY BUTTON
		TextActor playText = new TextActor(BUTTON_TEXT_PLAY, previousFieldYPosition-FontManager.getLargeLabel().font.getBounds(BUTTON_TEXT_PLAY).height, screenWidth, FontManager.getLargeLabel());
		playText.addListener(new InputListener() {
		
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			
				game.setScreen(getLoadingScreen());
				return false;
			}
		});
		playText.setTouchable(Touchable.enabled);
        stage.addActor(playText);
        
        // SHOW HOW TO PLAY BUTTON
		TextActor howToPlayText = new TextActor(BUTTON_TEXT_HOW_TO_PLAY, playText.getY() - buttonSpacer, screenWidth, FontManager.getLargeLabel());
		howToPlayText.addListener(new InputListener() {

			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				game.setScreen(ScreenManager.getHowToPlayScreen());
				return false;
			}
		});
		howToPlayText.setTouchable(Touchable.enabled);
        stage.addActor(howToPlayText);
             
        // SHOW CREDITS BUTTON
		TextActor creditsText = new TextActor(BUTTON_TEXT_CREDITS, howToPlayText.getY()-buttonSpacer, screenWidth, FontManager.getLargeLabel());
        creditsText.addListener(new InputListener() {
		
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			
				game.setScreen(ScreenManager.getCreditsScreen());
				return false;
			}
		});
        creditsText.setTouchable(Touchable.enabled);
        stage.addActor(creditsText);
        
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		return false;
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
			Gdx.app.exit();
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

