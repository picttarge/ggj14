package uk.co.vault101.terrain;

import java.util.Random;

import uk.co.vault101.Maths;
import uk.co.vault101.screen.GameScreen;
import uk.co.vault101.screen.ScreenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background extends Actor {
	
	private final TextureRegion region;
	
	private final Sound soundOutOfRange = Gdx.audio.newSound(Gdx.files
			.internal("sound/duff.ogg"));
	
	private final Sound lightOn = Gdx.audio.newSound(Gdx.files
			.internal("sound/104960__glaneur-de-sons__neon-light-02.ogg"));
	
    public Background () {
    	Texture texture = new Texture(Gdx.files.internal("image/terrain.png"));
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		region = new TextureRegion(texture, 0, 0, 640, 1024);
		setBounds(getX(), getY(),texture.getWidth(),texture.getHeight());
    	addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	if (!ScreenManager.getGameScreen().isActing()) {
            		lightOn.play();
            		ScreenManager.getGameScreen().userReadyForWave();
            	} else {
            	
            	    if (Maths.approxDistance((int)Math.abs(x-GameScreen.playerPos.x), (int)Math.abs(y-GameScreen.playerPos.y)) < 600) {
            		    ScreenManager.getGameScreen().shoot();
            	    } else {
            		    soundOutOfRange.play();
            	    }
            	}
            	
                return true;
            }
        });
    }

    @Override
    public void act(final float delta) {
    	super.act(delta);
    }
    
    @Override
    public void draw (final SpriteBatch batch, final float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

}
