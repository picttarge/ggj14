package uk.co.vault101.screen;

import static uk.co.vault101.screen.ScreenManager.getLoadingScreen;
import static uk.co.vault101.sound.SoundManager.playThemeTune;
import uk.co.vault101.FontManager;
import uk.co.vault101.Main;
import uk.co.vault101.actor.TextActor;
import uk.co.vault101.terrain.ImageActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class TitleScreen implements Screen, InputProcessor {

	private final Main game;
	private int screenWidth;
	private int screenHeight;
	private Stage stage;
	
	public static final String GAME_TITLE = "Jail Break!";
	public static final String GAME_BLURB = "Its 2050 and the prisioners of the largest jail on the planet (population 20,000) have escaped. Your the last guard alive. The safety of the country is in your hands kill all the convicts before they make it out the gate!";
	
	public static final String BUTTON_TEXT_PLAY = "PLAY";
	public static final String BUTTON_TEXT_CREDITS = "CREDITS"; 
	
	public TitleScreen(Main game) {
		this.game = game;
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		this.screenWidth = width;
		this.screenHeight = height;
		
		stage.setViewport(width, height, true);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
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
		
        ImageActor bannerImage = new ImageActor("image/prision.png", 800, 424);
        
        float actualBannerImageWidth = screenWidth*bannerImage.getAspect();
        bannerImage.setSize(screenWidth, actualBannerImageWidth);
        bannerImage.setOrigin(bannerImage.getWidth()/2, bannerImage.getHeight()/2);
        bannerImage.setPosition(0, screenHeight-bannerImage.getHeight());
        stage.addActor(bannerImage);
        
        TextActor titleText = new TextActor(GAME_TITLE, bannerImage.getY(), screenWidth, FontManager.getLargeLabel());
        stage.addActor(titleText);
		
        String[] blurbWords = GAME_BLURB.split(" ");
        
        StringBuilder blurbLine = new StringBuilder();
        float blurbTextYPosStart = titleText.getY();
        
		for (int i=0; i<blurbWords.length; i++) {
        	
			String blurbString = blurbLine.toString() + " " + blurbWords[i];
			
			if (FontManager.getNormalLabel().font.getBounds(blurbString).width < screenWidth && !isLastItem(i, blurbWords.length)) {
        		blurbLine.append(" " + blurbWords[i]); 
        	} else {
        		
        		boolean additionalLineRequired = false;
        		
        		if (isLastItem(i, blurbWords.length)) {
        			if (FontManager.getNormalLabel().font.getBounds(blurbString).width < screenWidth) {
        				blurbLine.append(" " + blurbWords[i]);
        			} else {
        				additionalLineRequired= true;
        			}
        		} 
        		
        		Actor blurbText = buildTextField(blurbLine.toString(), FontManager.getNormalLabel(), FontManager.getNormalLabel().font, blurbTextYPosStart);
        		blurbTextYPosStart = blurbText.getY();
        		blurbLine.setLength(0);
        		blurbLine.append(" " + blurbWords[i]); 
        		blurbString = " " + blurbWords[i];
        		
        		stage.addActor(blurbText);
        		
        		if (additionalLineRequired) {
        			blurbText = buildTextField(blurbWords[i], FontManager.getNormalLabel(), FontManager.getNormalLabel().font, blurbTextYPosStart);
        			stage.addActor(blurbText);
        		}
        	}
        }
        
		TextActor playText = new TextActor(BUTTON_TEXT_PLAY, (screenHeight-blurbTextYPosStart)/2, screenWidth, FontManager.getLargeLabel());
		playText.addListener(new InputListener() {
		
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			
				game.setScreen(getLoadingScreen());
				return false;
			}
		});
		playText.setTouchable(Touchable.enabled);
        stage.addActor(playText);
        
//        TextActor creditsText = new TextActor(BUTTON_TEXT_CREDITS, playText.getY()-FontManager.getLargeLabel().font.getBounds(BUTTON_TEXT_CREDITS).height, screenWidth, FontManager.getLargeLabel());
//        creditsText.addListener(new InputListener() {
//		
//			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//			
//				game.setScreen(getLoadingScreen());
//				return false;
//			}
//		});
//        creditsText.setTouchable(Touchable.enabled);
//        stage.addActor(creditsText);
        
		Gdx.input.setInputProcessor(stage);
	}

	private Actor buildTextField(String theBlurb, LabelStyle labelStyle, BitmapFont font, float blurbTextYPosStart) {
		
		Label blurbText = new Label(theBlurb, labelStyle);
		blurbText.setSize(font.getBounds(theBlurb).width, font.getBounds(theBlurb).height);
		blurbText.setOrigin(blurbText.getWidth()/2, blurbText.getHeight()/2);
		blurbText.setPosition((screenWidth/2) - (font.getBounds(theBlurb).width/2), blurbTextYPosStart-font.getBounds(theBlurb).height);
		
		return blurbText;
	}
	
	private boolean isLastItem(int i, int countOfItems) {
		return i == (countOfItems-1);
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		return false;
	}
	
	@Override
	public void hide() {
		 Gdx.input.setInputProcessor(null);
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
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

