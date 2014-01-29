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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class GameScreen implements Screen {

	private final static boolean DEBUG = true;
	
	private final Main game;
	private final Stage stage;
	private final Stage textStage;
	private final Random random;
	private final Actor background;
	private final Actor mask;
	private final Actor player;
	
	private final Sound soundWar;
	private final Sound soundWin;
	private final Sound soundFailed;
	private final Sound[] soundShooting;

	/** Public */
	public static Vector2 playerPos;
	public static int possibleConvicts = 0;
	public static int possibleCivvies = 0;
	public static Spotlight spotlight;
	
	// stats
	private static int kills = 0;
	private static int friendlyFire = 0;
	private static int escapees = 0;
	private static int rescued = 0;
	
	private static int totalEverHighestWaveCompleted = 0;
	private static int totalEverKills = 0;
	private static int totalEverFriendlyFire = 0;
	private static int totalEverEscapees = 0;
	private static int totalEverRescued = 0;
	private static int totalEverPossibleConvicts = 0;
	private static int totalEverPossibleCivvies = 0;

	private static boolean acting = false;

	private final Texture[] icons;
	private final Actor[] iconActor;
	private final Texture[] beastTextures;

	private final static List<Actor> allBeasts = new ArrayList<Actor>();
	private int INITIAL = 10;
	private int wave = 0;
	
	private static int win_kills_at_least;
	private static int win_escapees_less_than_equal_to;
	private static int win_friendly_fire_less_than_equal_to;
	private static int win_rescues_at_least;
	
	private static int next_win_kills_at_least;
	private static int next_win_escapees_less_than_equal_to;
	private static int next_win_friendly_fire_less_than_equal_to;
	private static int next_win_rescues_at_least;

	private float w, h;

	private TextActor[] scoreText;
	private TextActor winLoseText;
	private TextActor preWaveText;
	private TextActor preWinConditions;
	private TextActor preWinConditionKills;
	private TextActor preWinConditionEscapes;
	private TextActor preWinConditionFF;
	private TextActor preWinConditionRescues;

	enum WINLOSE {
		WIN, LOSE, NOOP
	}

	private void debug(String s) {
		System.out.println(s);
	}
	
	public GameScreen(final Main game) {
		this.game = game;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		stage = new Stage();
		textStage = new Stage();
		random = new Random();
				
		soundShooting = new Sound[8];
    	for (int i=0; i < soundShooting.length; i++) {
    		if (DEBUG) debug("[SOUND] Loading sniper "+i+"...");
    		soundShooting[i] = Gdx.audio.newSound(Gdx.files
    			.internal("sound/182435__qubodup__sniper-shots-dod-56706-dod-146324-"+i+".ogg"));
    	}
		
    	if (DEBUG) debug("[SOUND] Loading war...");
		soundWar = Gdx.audio.newSound(Gdx.files
				.internal("sound/87718__robinhood76__01451-war-scene-arrangement-1.ogg"));
		
		soundWin = Gdx.audio.newSound(Gdx.files
				.internal("sound/166540__qubodup__success-quest-complete-rpg-sound.ogg"));
		
		soundFailed = Gdx.audio.newSound(Gdx.files
				.internal("sound/24810__spt3125__timp-rolls.ogg"));
		
		beastTextures = new Texture[6];
		for (int i = 0; i < 5; i++) {
			if (DEBUG) debug("[IMAGE] Loading civvie "+i+"...");
			beastTextures[i] = new Texture(Gdx.files.internal("image/civilian"
					+ i + ".png"));
		}
		beastTextures[5] = new Texture(
				Gdx.files.internal("image/blood_splash.png"));

		icons = new Texture[4];
		iconActor = new Actor[icons.length];
		for (int i = 0; i < icons.length; i++) {
			if (DEBUG) debug("[IMAGE] Loading icon "+i+"...");
			icons[i] = new Texture(
					Gdx.files.internal("image/icon" + i + ".png"));
			iconActor[i] = new Icon(icons[i]);
			iconActor[i].setPosition((i * (w / icons.length))
					+ ((w / icons.length) / 2) - 16 - 32, h - 32);
		}

		if (DEBUG) debug("[IMAGE] Loading terrain...");
		background = new Background();
		background.setSize(w, h);
		background.setOrigin(w / 2, h / 2);
		background.setPosition(0, 0);
		background.setTouchable(Touchable.enabled);

		if (DEBUG) debug("[IMAGE] Loading mask...");
		mask = new Mask(w, h);
		mask.setSize(w, h);
		mask.setOrigin(mask.getWidth() / 2, mask.getHeight() / 2);
		mask.setPosition(0, 0);
		mask.setTouchable(Touchable.disabled);

		spotlight = new Spotlight(w, new Vector2(0, 1075), new Vector2(w,
				980), new Vector2(0, 708), new Vector2(w, 860));

		if (DEBUG) debug("[IMAGE] Loading player...");
		player = new Player();
		playerPos = new Vector2((w / 2) - (player.getWidth() / 2), 0);
		player.setPosition(playerPos.x, playerPos.y);

		// set up the text
		// IMPORTANT ORDER

		scoreText = new TextActor[4];
		for (int i = 0; i < scoreText.length; i++) {
			if (DEBUG) debug("[TEXT] Loading text "+i+"...");
			scoreText[i] = new TextActor("0", h + 8, w * ((i + 1) * 0.5f),
					-32, FontManager.getNormalLabel());
		}

		if (DEBUG) debug("[TEXT] making new text x7...");
		winLoseText = new TextActor("", h - 270, w,
				FontManager.getLargeLabel());
		preWaveText = new TextActor("", (h / 2) + 96, w,
				FontManager.getNormalLabel());
		preWinConditions = new TextActor("", (h / 2) + 56, w,
				FontManager.getNormalLabel());
		preWinConditionKills = new TextActor("", (h / 2) + 16, w,
				FontManager.getNormalLabel());
		preWinConditionEscapes = new TextActor("", (h / 2) - 16, w,
				FontManager.getNormalLabel());
		preWinConditionFF = new TextActor("", (h / 2) - 48, w,
				FontManager.getNormalLabel());
		preWinConditionRescues = new TextActor("", (h / 2) - 80, w,
				FontManager.getNormalLabel());
		
		if (DEBUG) debug("[FINISHED LOADING]");
	}

	void update() {
		
		testWinCondition();

		testLoseCondition();
	}

	private void testLoseCondition() {
		// test failure (not possible to win) condition
		if (acting && ((escapees > win_escapees_less_than_equal_to)
				|| (friendlyFire > win_friendly_fire_less_than_equal_to))) {
			// not possible to win
			totalEverHighestWaveCompleted = wave - 1;

			acting = false;
			resetAll(INITIAL, WINLOSE.LOSE, false);
		}
	}

	private void testWinCondition() {
		// test win condition
		if (acting && (kills >= win_kills_at_least)
				&& (escapees <= win_escapees_less_than_equal_to)
				&& (friendlyFire <= win_friendly_fire_less_than_equal_to)
				&& (rescued >= win_rescues_at_least)) {
			// win
			totalEverHighestWaveCompleted = wave;

			acting = false;
			resetStage(WINLOSE.WIN);
		}
	}

	private void updateScoreTextKills() {
		scoreText[0].setText("" + kills);
		if (kills >= win_kills_at_least) {
			scoreText[0].setColor(Color.GREEN);
		} else {
			scoreText[0].setColor(Color.RED);
		}
	}
	
	private void updateScoreTextEscapes() {
		scoreText[1].setText("" + escapees);
		if (escapees < win_escapees_less_than_equal_to) {
			scoreText[1].setColor(Color.GREEN);
		} else if (escapees == win_escapees_less_than_equal_to) {
			scoreText[1].setColor(Color.YELLOW);
		} else {
			scoreText[1].setColor(Color.RED);
		}
	}
	
	private void updateScoreTextFriendlyFire() {
		scoreText[2].setText("" + friendlyFire);
		if (friendlyFire < win_friendly_fire_less_than_equal_to) {
			scoreText[2].setColor(Color.GREEN);
		} else if (friendlyFire == win_friendly_fire_less_than_equal_to) {
			scoreText[2].setColor(Color.YELLOW);
		} else {
			scoreText[2].setColor(Color.RED);
		}
	}
	
	private void updateScoreTextRescued() {
		scoreText[3].setText("" + rescued);
		if (rescued >= win_rescues_at_least) {
			scoreText[3].setColor(Color.GREEN);
		} else {
			scoreText[3].setColor(Color.RED);
		}
	}

	@Override
	public void render(final float delta) {

		update();

		Gdx.gl.glClearColor(0, 0, 0, 1);

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (isActing()) {
			((Mask) mask).lightOn();
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
		} else {
			((Mask) mask).lightOff();
			stage.draw();
			textStage.draw();
		}

	}
	
	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		textStage.setViewport(width, height, true);
	}

	private void nextWave(final WINLOSE winlose, final boolean stageReset) {
		wave++;

		INITIAL = 9 + wave;

		next_win_kills_at_least = 4 + (totalEverKills/wave) + wave;
		next_win_escapees_less_than_equal_to = wave / 5;
		next_win_friendly_fire_less_than_equal_to = wave / 4;
		next_win_rescues_at_least = 2 + wave;

		if (winlose == WINLOSE.NOOP) {
			resetScores();
		}

		resetAll(INITIAL, winlose, stageReset);
	}

	private void resetAll(final int max_beasties,
			final WINLOSE winlose,
			final boolean stageReset) {

		if (DEBUG) debug("[GAME] Resetting all text...");
		
		totalEverKills += kills;
		totalEverEscapees += escapees;
		totalEverFriendlyFire += friendlyFire;
		totalEverRescued += rescued;
		totalEverPossibleCivvies += possibleCivvies;
		totalEverPossibleConvicts += possibleConvicts;
		
		if (winlose == WINLOSE.WIN) {
			soundWin.play();
			winLoseText.setText("WIN!");
			winLoseText.setColor(Color.GREEN);
		} else if (winlose == WINLOSE.LOSE) {
			soundFailed.play();
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
		preWinConditionKills.setText("Kills: >= " + next_win_kills_at_least);

		preWinConditionEscapes.setColor(Color.RED);
		preWinConditionEscapes.setText("Convict Escapes: < "
				+ next_win_escapees_less_than_equal_to);

		preWinConditionFF.setColor(Color.GREEN);
		preWinConditionFF.setText("Civilian deaths: < "
				+ next_win_friendly_fire_less_than_equal_to);

		preWinConditionRescues.setColor(Color.GREEN);
		preWinConditionRescues.setText("Rescues: >= " + next_win_rescues_at_least);

		for (Actor beast : allBeasts) {
			if (DEBUG) debug("[GAME] Resetting civvie and adding to stage...");
			((Beastie) beast).reset();
			if (stageReset) {
				stage.addActor(beast);
			}
		}
		// only make as many new as you need
		final int make = max_beasties - allBeasts.size();
		for (int i = 0; i < make; i++) {
			if (DEBUG) debug("[GAME] Making new civvie "+i+"...");
			Actor beast = new Beastie(beastTextures,
					(100 * random.nextFloat()), w, h);

			((Beastie) beast).reset();

			beast.setTouchable(Touchable.enabled);
			allBeasts.add(beast);
			// visible by default.
			stage.addActor(beast);
		}
		if (DEBUG) debug("[GAME RESET ALL COMPLETE]");
	}

	private static void resetScores() {
		
		win_kills_at_least = next_win_kills_at_least;
		win_escapees_less_than_equal_to = next_win_escapees_less_than_equal_to;
		win_friendly_fire_less_than_equal_to = next_win_friendly_fire_less_than_equal_to;
		win_rescues_at_least = next_win_rescues_at_least;
		
		kills = 0;
		escapees = 0;
		friendlyFire = 0;
		rescued = 0;
		possibleCivvies = 0;
		possibleConvicts = 0;
	}
	
	@Override
	public void show() {

		resetStage(WINLOSE.NOOP); // initial (no-op win/lose state)

		Gdx.input.setInputProcessor(stage);

	}

	private void resetStage(final WINLOSE winlose) {

		if (DEBUG) debug("[STAGE] Clearing...");
		stage.clear();

		// first the ground
		if (DEBUG) debug("[STAGE] Adding terrain...");
		stage.addActor(background);

		// then the beasties
		if (DEBUG) debug("[STAGE] Preparing next wave...");
		nextWave(winlose, true);

		// then the light mask
		if (DEBUG) debug("[STAGE] Adding light mask...");
		stage.addActor(mask);

		// then the player
		if (DEBUG) debug("[STAGE] Adding player...");
		stage.addActor(player);

		// the score icons
		for (int i = 0; i < iconActor.length; i++) {
			if (DEBUG) debug("[STAGE] Adding icon and score text "+i+"...");
			stage.addActor(iconActor[i]); // score icons
			stage.addActor(scoreText[i]); // score values
		}

		if (DEBUG) debug("[STAGE] Adding other pre-wave text...");
		// other disappearing text added to text Stage
		textStage.addActor(winLoseText);
		textStage.addActor(preWaveText);
		textStage.addActor(preWinConditions);
		textStage.addActor(preWinConditionKills);
		textStage.addActor(preWinConditionEscapes);
		textStage.addActor(preWinConditionFF);
		textStage.addActor(preWinConditionRescues);
		
		if (DEBUG) debug("[STAGE RESET COMPLETE]");
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
		if (stage != null) {
			stage.dispose();
		}

		if (textStage != null) {
			textStage.dispose();
		}
	}

	public boolean isActing() {
		return acting;
	}

	public void userReadyForWave() {
		acting = true;
		soundWar.play();
		resetScores();
	}

	public void shoot() {
		soundShooting[random.nextInt(soundShooting.length)].play();
	}
	
	public void addKills() {
		kills++;
		updateScoreTextKills();
	}
	
	public void addFriendlyFire() {
		friendlyFire++;
		updateScoreTextFriendlyFire();
	}
	
	public void addEscapes() {
		escapees++;
		updateScoreTextEscapes();
	}
	
	public void addRescues() {
		rescued++;
		updateScoreTextRescued();
	}

}
