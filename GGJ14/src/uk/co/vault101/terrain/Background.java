package uk.co.vault101.terrain;

import uk.co.vault101.actor.Beastie;

import com.badlogic.gdx.Gdx;
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


    public Background (String asset) {
    	Texture texture = new Texture(Gdx.files.internal(asset));
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	setBounds(getX(), getY(),texture.getWidth(),texture.getHeight());
		region = new TextureRegion(texture, 0, 0, 640, 1024);
		setBounds(getX(), getY(),texture.getWidth(),texture.getHeight());
    	addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("touchDown on ground: ("+x+","+y+") "+pointer+" "+button);
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
