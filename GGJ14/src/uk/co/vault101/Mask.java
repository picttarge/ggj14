package uk.co.vault101;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mask extends Actor {
	final TextureRegion region;
	final TextureRegion regionBlack;
	final float originalX;
	
    public Mask (String mask, float width, float height) {
    	{
    	Texture texture = new Texture(Gdx.files.internal(mask));
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	setBounds(0,0,width,height);
		region = new TextureRegion(texture, 0, 0, 1024, 640);
    	originalX = getX();
    	}
    	Texture texture = new Texture(Gdx.files.internal("image/maskall.png"));
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		regionBlack = new TextureRegion(texture, 0, 0, 1024, 640);
    	
    }

    @Override
    public void act(float delta) {
    	super.act(delta);
    	final float speed = 0;
    	
    	// TODO: put movement of mask here
    	setX(getX()-(speed*delta));
    }
    
    @Override
    public void draw (SpriteBatch batch, float parentAlpha) {
   
            Color color = getColor();
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
            batch.draw(Math.random() < (Math.random()*0.1) ? regionBlack : region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

    }
    
    
    
    
    
     
    
}
