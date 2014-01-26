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
	
	TextureRegion inuse;
	
	final float originalX;
	final float width;
	final float height;
	
    public Mask (float width, float height) {
    	this.width = width;
    	this.height = height;
    	{
    		Texture texture = new Texture(Gdx.files.internal("image/mask.png"));
    		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    		setBounds(0,0,width,height);
    		region = new TextureRegion(texture, 0, 0, 1024, 640);
    		originalX = getX();
    	}
    	
    	Texture texture = new Texture(Gdx.files.internal("image/maskall.png"));
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	regionBlack = new TextureRegion(texture, 0, 0, 1024, 640);
    	lightOn();
    }

    public void lightOn() {
    	inuse = region;
    }
    
    public void lightOff() {
    	inuse = regionBlack;
    }
    
    @Override
    public void act(float delta) {
    	super.act(delta);
    	//this.setRotation((System.currentTimeMillis()/10)%360);
//    	final float speed = 0.5f;
//    	
//    	// TODO: put movement of mask here
//    	setY(getY()-(speed * (float)Math.sin(Math.toRadians(System.currentTimeMillis()/100))));
    }
    
    @Override
    public void draw (SpriteBatch batch, float parentAlpha) {
   
            Color color = getColor();
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
//            batch.draw(Math.random() < (Math.random()*0.1) ? regionBlack : region, getX(), getY(), getOriginX(), getOriginY(),
//                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            batch.draw(inuse, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

    }
    
    
    
    
    
     
    
}
