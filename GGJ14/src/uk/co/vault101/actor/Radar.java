package uk.co.vault101.actor;



import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Radar extends Actor {
	final TextureRegion region;

    public Radar (String asset) {
    	Texture texture = new Texture(Gdx.files.internal(asset));
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	setBounds(getX(), getY(),texture.getWidth(),texture.getHeight());
		region = new TextureRegion(texture, 0, 0, 512, 512);
    }

    @Override
    public void act(float delta) {
    	super.act(delta);
    }
    
    @Override
    public void draw (SpriteBatch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    
    
    
    
    
     
    
}
