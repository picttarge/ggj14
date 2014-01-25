package uk.co.vault101;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Beastie extends Actor {
	final Texture texture = new Texture(Gdx.files.internal("data/player.png"));
	final Texture textureDead = new Texture(Gdx.files.internal("data/blood_splash.png"));
	final Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/splat0.ogg"));
	private boolean alive = true;
	
	private float speed;
	
    public Beastie (float speed) {
    	this.speed = speed; 
    	setBounds(getX(), getY(),texture.getWidth(),texture.getHeight());
    	addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println("touchDown on Beastie: ("+x+","+y+") "+pointer+" "+button);
            	sound.play();
                ((Beastie)event.getTarget()).alive = !alive;
                return true;
            }
        });
    }

    @Override
    public void act(float delta) {
    	super.act(delta);
    	if(alive){
            this.setX(this.getX()+(speed*delta));
        }
    	//System.out.println("ACTING: to be or not to be that is the question!");
    }
    
    @Override
    public void draw (SpriteBatch batch, float parentAlpha) {
    	//System.out.println("trying to draw actor");
//        Color color = getColor();
//        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
//        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
//            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

            batch.draw(alive ? texture : textureDead,getX(), getY());

    }
    
    
    
    
    
     
    
}
