package uk.co.vault101.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageActor extends Actor {
	final TextureRegion region;


    public ImageActor (String asset, int width, int height) {
    	Texture texture = new Texture(Gdx.files.internal(asset));
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	
    	setBounds(getX(), getY(),texture.getWidth(),texture.getHeight());
		region = new TextureRegion(texture, 0, 0, width, height);
    }

    @Override
    public void act(float delta) {
    	super.act(delta);
    }
    
    public float getAspect() {
    	return getHeight()/getWidth();
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
