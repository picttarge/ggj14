package uk.co.vault101.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.co.vault101.FontManager;
import uk.co.vault101.Main;
import uk.co.vault101.Mask;
import uk.co.vault101.Spotlight;
import uk.co.vault101.actor.Beastie;
import uk.co.vault101.actor.Icon;
import uk.co.vault101.actor.Player;
import uk.co.vault101.actor.TextActor;
import uk.co.vault101.terrain.Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class GameScreen implements Screen {

	Main game;
	private final Stage stage;
	private final Stage textStage;
	private final Random random;
	private final Actor background;
	private final Actor mask;
	public static Spotlight spotlight;
	public static Vector2 playerPos;
	private final Actor player;
	public static int kills = 0;
	public static int friendlyFire = 0;
	public static int escapees = 0;
	public static int rescued = 0;
	public static int possibleConvicts = 0;
	public static int possibleCivvies = 0;

	// stats
	public static int totalEverHighestWaveCompleted = 0;
	public static int totalEverKills = 0;
	public static int totalEverFriendlyFire = 0;
	public static int totalEverEscapees = 0;
	public static int totalEverRescued = 0;
	public static int totalEverPossibleConvicts = 0;
	public static int totalEverPossibleCivvies = 0;

	public static boolean acting = false;

	private Texture[] icons;
	private Actor[] iconActor;
	private Texture[] beastTextures;
	
	static List<Actor> allBeasts = new ArrayList<Actor>();
	int INITIAL = 10;
	int wave = 0;
	int win_kills_at_least;
	int win_escapees_less_than_equal_to;
	int win_friendly_fire_less_than_equal_to;
	int win_rescues_at_least;

	float w, h;

	TextActor[] scoreText;
	TextActor winLoseText;
	TextActor preWaveText;
	TextActor preWinConditions;
	TextActor preWinConditionKills;
	TextActor preWinConditionEscapes;
	TextActor preWinConditionFF;
	TextActor preWinConditionRescues;
	
	enum WINLOSE {
		WIN, LOSE, NOOP
	}

	public GameScreen(Main game) {
		this.game = game;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		stage = new Stage();
		textStage = new Stage();
		random = new Random();
		
		beastTextures = new Texture[6];
		for (int i=0; i<5;i++) {
			beastTextures[i] = new Texture(Gdx.files.internal("image/civilian"+i+".png"));
		}
		beastTextures[5] = new Texture(Gdx.files.internal("image/blood_splash.png"));
		
		icons = new Texture[4];
		iconActor = new Actor[icons.length];
		for (int i=0; i < icons.length; i++) {
			icons[i] = new Texture(Gdx.files.internal("image/icon"+i+".png"));
			iconActor[i] = new Icon(icons[i]);
			iconActor[i].setPosition((i*(w/icons.length))+((w/icons.length)/2)-16-32, h-32);
		}
		
		background = new Background();
		background.setSize(w, h);
		background.setOrigin(w / 2, h / 2);
		background.setPosition(0, 0);
		background.setTouchable(Touchable.enabled);
		
		mask = new Mask(w, h);
		mask.setSize(w, h);
		mask.setOrigin(mask.getWidth() / 2, mask.getHeight() / 2);
		mask.setPosition(0, 0);
		mask.setTouchable(Touchable.disabled);
		
		spotlight = new Spotlight(
				w, h, 
				new Vector2(0, 1075), new Vector2(w,980), 
				new Vector2(0, 708), new Vector2(w, 860));
		
		player = new Player();
		playerPos = new Vector2(w / 2, 0);
		player.setPosition(playerPos.x, playerPos.y);
		
		// set up the text
		// IMPORTANT ORDER
		
		scoreText = new TextActor[4];
		for (int i=0; i < scoreText.length; i++) {
			// .setPosition((i*(w/icons.length))+((w/icons.length)/2)-16, h-32);
			scoreText[i] = new TextActor("###", h+8, w*((i+1)*0.5f), -32, FontManager.getNormalLabel());
		}
		
		winLoseText = new TextActor("winlose", h-270, w,
				FontManager.getLargeLabel());
		preWaveText = new TextActor("wave", (h / 2) + 96, w,
				FontManager.getNormalLabel());
		preWinConditions = new TextActor("wave", (h / 2) + 56, w,
				FontManager.getNormalLabel());
		preWinConditionKills = new TextActor("wave", (h / 2) + 16, w,
				FontManager.getNormalLabel());
		preWinConditionEscapes = new TextActor("wave", (h / 2) - 16, w,
				FontManager.getNormalLabel());
		preWinConditionFF = new TextActor("wave", (h / 2) - 48, w,
				FontManager.getNormalLabel());
		preWinConditionRescues = new TextActor("wave", (h / 2) - 80, w,
				FontManager.getNormalLabel());
	}

	void update() {
		scoreText[0].setText(""+kills);
		if (kills >= win_kills_at_least) {
			scoreText[0].setColor(Color.GREEN);
		} else {
			scoreText[0].setColor(Color.RED);
		}
		scoreText[1].setText(""+escapees);
		if (escapees <= win_escapees_less_than_equal_to) {
			scoreText[1].setColor(Color.GREEN);
		} else {
			scoreText[1].setColor(Color.RED);
		}
		scoreText[2].setText(""+friendlyFire);
		if (friendlyFire <= win_friendly_fire_less_than_equal_to) {
			scoreText[2].setColor(Color.GREEN);
		} else {
			scoreText[2].setColor(Color.RED);
		}
		scoreText[3].setText(""+rescued);
		if (rescued >= win_rescues_at_least) {
			scoreText[3].setColor(Color.GREEN);
		} else {
			scoreText[3].setColor(Color.RED);
		}

		// test win condition
		if ((kills >= win_kills_at_least)
				&& (escapees <= win_escapees_less_than_equal_to)
				&& (friendlyFire <= win_friendly_fire_less_than_equal_to)
				&& (rescued >= win_rescues_at_least)) {
			// win
			totalEverHighestWaveCompleted = wave;

			acting = false;
			resetStage(WINLOSE.WIN);
		}

		// test failure (not possible to win) condition
		if ((escapees > win_escapees_less_than_equal_to)
				|| (friendlyFire > win_friendly_fire_less_than_equal_to)) {
			// not possible to win
			totalEverHighestWaveCompleted = wave - 1;

			acting = false;
			resetAll(INITIAL, WINLOSE.LOSE, false);
		}
	}

	@Override
	public void render(float delta) {

		update();

		Gdx.gl.glClearColor(0, 0, 0, 1);

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (acting) {
			((Mask)mask).lightOn();
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
		} else {
			((Mask)mask).lightOff();
			stage.draw();
			textStage.draw();
		}

	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		textStage.setViewport(width, height, true);
	}

	public void nextWave(WINLOSE winlose, final boolean stageReset) {
		wave++;

		INITIAL = 9 + wave;

		win_kills_at_least = 2 + wave;
		win_escapees_less_than_equal_to = wave / 3;
		win_friendly_fire_less_than_equal_to = wave / 3;
		win_rescues_at_least = 2 + wave;

		resetAll(INITIAL, winlose, stageReset);
	}

	public void resetAll(int max_beasties, WINLOSE winlose, final boolean stageReset) {

		totalEverKills += kills;
		kills = 0;
		
		totalEverEscapees += escapees;
		escapees = 0;
		
		totalEverFriendlyFire += friendlyFire;
		friendlyFire = 0;
		
		totalEverRescued += rescued;
		rescued = 0;
		
		totalEverPossibleCivvies += possibleCivvies;
		possibleCivvies = 0;
		
		totalEverPossibleConvicts += possibleConvicts;
		possibleConvicts = 0;
		
		if (winlose == WINLOSE.WIN) {
			winLoseText.setText("WIN!");
			winLoseText.setColor(Color.GREEN);
		} else if (winlose == WINLOSE.LOSE) {
			winLoseText.setColor(Color.RED);
			winLoseText.setText("Failed!");
		} else {
			// no-op
			winLoseText.setText("");
		}
		
		preWaveText.setText("Ready for Wave " + wave);
		
		preWinConditions.setColor(Color.YELLOW);
		preWinConditions.setText("Win conditions:");
		
		preWinConditionKills.setColor(Color.RED);
		preWinConditionKills.setText("Kills: " + win_kills_at_least);
		
		preWinConditionEscapes.setColor(Color.RED);
		preWinConditionEscapes.setText("Allowed Escapes: "
				+ win_escapees_less_than_equal_to);
		
		preWinConditionFF.setColor(Color.GREEN);
		preWinConditionFF.setText("Allowed Friendly-fire: "
				+ win_friendly_fire_less_than_equal_to);
		
		preWinConditionRescues.setColor(Color.GREEN);
		preWinConditionRescues.setText("Rescues: " + win_rescues_at_least);
		
		for (Actor beast : allBeasts) {
	        ((Beastie) beast).reset();
	        if (stageReset) {
	        	stage.addActor(beast);
	        }
		}
		// only make as many new as you need
		final int make = max_beasties - allBeasts.size();
		for (int i = 0; i < make; i++) {
			Actor beast = new Beastie(beastTextures, (100 * random.nextFloat()), w, h);

			((Beastie) beast).reset();
			
			beast.setTouchable(Touchable.enabled);
			allBeasts.add(beast);
			// visible by default.
			stage.addActor(beast);
		}
	}

	@Override
	public void show() {

		resetStage(WINLOSE.NOOP); // initial (no-op win/lose state)

		Gdx.input.setInputProcessor(stage);

	}

	private void resetStage(WINLOSE winlose) {

		
		stage.clear();

		// first the ground
		stage.addActor(background);
				
		// then the beasties
		nextWave(winlose, true);

		// then the light mask
		stage.addActor(mask);

		// then the player
		stage.addActor(player);

		// the score icons
		for (int i=0; i < iconActor.length; i++) {
			stage.addActor(iconActor[i]); // score icons
			stage.addActor(scoreText[i]); // score values
		}

		// other disappearing text added to text Stage
		textStage.addActor(winLoseText);
		textStage.addActor(preWaveText);
		textStage.addActor(preWinConditions);
		textStage.addActor(preWinConditionKills);
		textStage.addActor(preWinConditionEscapes);
		textStage.addActor(preWinConditionFF);
		textStage.addActor(preWinConditionRescues);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		if(stage != null){
			stage.dispose();
		}
		
		if(textStage != null){
			textStage.dispose();
		}
	}

}
