package uk.co.vault101.screen;

import uk.co.vault101.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LoadingScreen implements Screen {

	Main game;
    private Music music2 = Gdx.audio.newMusic(Gdx.files.internal("sound/33703__yewbic__ambience02.ogg"));
	private OrthographicCamera camera;
	private Texture texture;
	private Sprite sprite;
	private int width;
	private int height;
	private long rendercount = 0;
	
	public LoadingScreen(Main game) {
		this.game = game;
	}
	
	@Override
	public void dispose() {
		texture.dispose();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
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
		
		Main.batch.setProjectionMatrix(camera.combined);
		Main.batch.begin();
		
		sprite.draw(Main.batch);
		Main.batch.end();
		if (rendercount == 2) {
			game.setScreen(ScreenManager.getGameScreen());
		}
		rendercount++;
	}

	@Override
	public void show() {
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w,h);
		
		texture = new Texture(Gdx.files.internal("image/loading.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 640, 1024);
		
		sprite = new Sprite(region);
		sprite.setSize(w, h);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		
		music2.setLooping(true);
		music2.play();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
