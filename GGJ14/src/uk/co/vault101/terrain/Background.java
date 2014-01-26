package uk.co.vault101.terrain;

import uk.co.vault101.Maths;
import uk.co.vault101.screen.GameScreen;

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
	final TextureRegion region;

	final Sound soundShooting = Gdx.audio.newSound(Gdx.files
			.internal("sound/shotgun.ogg"));
	
	final Sound soundOutOfRange = Gdx.audio.newSound(Gdx.files
			.internal("sound/duff.ogg"));
	
    public Background (String asset) {
    	Texture texture = new Texture(Gdx.files.internal(asset));
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	setBounds(getX(), getY(),texture.getWidth(),texture.getHeight());
		region = new TextureRegion(texture, 0, 0, 640, 1024);
		setBounds(getX(), getY(),texture.getWidth(),texture.getHeight());
    	addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("touchDown on ground: ("+x+","+y+") "+pointer+" "+button);
            	
            	if (!GameScreen.acting) {
            		System.out.println("FIRST TIME SWITCH TO GAMESCREEN.ACTING = true");
            		GameScreen.acting = true;
            	}
            	
            	if (Maths.approxDistance((int)Math.abs(x-GameScreen.playerPos.x), (int)Math.abs(y-GameScreen.playerPos.y)) < 600) {
            		soundShooting.play();
            	} else {
            		soundOutOfRange.play();
            	}
            	
                return true;
            }
        });
    }

    @Override
    public void act(float delta) {
    	super.act(delta);
    }
    
    @Override
    public void draw (SpriteBatch batch, float parentAlpha) {
    	//System.out.println("trying to draw actor");
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    
    
    
    
    
     
    
}
