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

public class TitleScreen implements Screen, InputProcessor {

	Main game;
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("sound/one-eyed_maestro.ogg"));
    private Music music2 = Gdx.audio.newMusic(Gdx.files.internal("sound/30306__ERH__tension.ogg"));
	private OrthographicCamera camera;
	private Texture texture;
	private Sprite sprite;
	private int width;
	private int height;
	
	public TitleScreen(Main game) {
		this.game = game;
	}
	
	@Override
	public void dispose() {
		texture.dispose();
		music.dispose();
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
	}

	@Override
	public void show() {
		
		music.setLooping(true);
		music.play();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w,h);
		
		texture = new Texture(Gdx.files.internal("image/title.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 640, 1024);
		
		sprite = new Sprite(region);
		sprite.setSize(w, h);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		 Gdx.input.setInputProcessor(null);
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (screenX < width / 2) {
			// humans
			game.humans = true;
		} else {
			// vampires
			game.humans = false;
		}
		
		music.stop();
		music2.setLooping(true);
		music2.play();
		game.setScreen(game.gameScreen);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
