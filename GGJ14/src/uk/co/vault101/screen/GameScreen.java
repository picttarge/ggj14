package uk.co.vault101.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.co.vault101.FontManager;
import uk.co.vault101.Main;
import uk.co.vault101.Mask;
import uk.co.vault101.Spotlight;
import uk.co.vault101.actor.Beastie;
import uk.co.vault101.actor.Player;
import uk.co.vault101.actor.TextActor;
import uk.co.vault101.terrain.Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class GameScreen implements Screen {

	Main game;
	private Stage stage;
	private Stage textStage;
	private Random random = new Random();
	private Actor mask;
	public static Spotlight spotlight;
	public static Vector2 playerPos;
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

	static List<Actor> allBeasts = new ArrayList<Actor>();
	int INITIAL = 10;
	int wave = 0;
	int win_kills_at_least;
	int win_escapees_less_than_equal_to;
	int win_friendly_fire_less_than_equal_to;
	int win_rescues_at_least;

	float w, h;

	TextActor titleText;
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
	}

	void update() {
		titleText.setText("Kills:" + kills + " Esc:"
				+ escapees + " FF:" + friendlyFire
				+ " Resc:" + rescued);

		// test win condition
		if ((kills >= win_kills_at_least)
				&& (escapees <= win_escapees_less_than_equal_to)
				&& (friendlyFire <= win_friendly_fire_less_than_equal_to)
				&& (rescued >= win_rescues_at_least)) {
			// win
			totalEverHighestWaveCompleted = wave;

			acting = false;
			nextWave(WINLOSE.WIN);
		}

		// test failure (not possible to win) condition
		if ((escapees > win_escapees_less_than_equal_to)
				|| (friendlyFire > win_friendly_fire_less_than_equal_to)) {
			// not possible to win
			totalEverHighestWaveCompleted = wave - 1;

			acting = false;
			resetAll(INITIAL, WINLOSE.LOSE);
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

	public void nextWave(WINLOSE winlose) {
		wave++;

		INITIAL = 9 + wave;

		win_kills_at_least = 2 + wave;
		win_escapees_less_than_equal_to = wave / 3;
		win_friendly_fire_less_than_equal_to = wave / 3;
		win_rescues_at_least = 2 + wave;

		resetAll(INITIAL, winlose);
	}

	public void resetAll(int max_beasties, WINLOSE winlose) {

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
			if (beast instanceof Beastie) {
				((Beastie) beast).reset();
			}
		}
		// only make as many new as you need
		final int make = max_beasties - allBeasts.size();
		for (int i = 0; i < make; i++) {
			Actor beast = new Beastie((100 * random.nextFloat()), w, h);

			beast.setX(((w / max_beasties) * i) + (w / (max_beasties << 1)));
			beast.setY(h + (20 * random.nextFloat()) + 100);

			beast.setTouchable(Touchable.enabled);
			allBeasts.add(beast);
			// visible by default.
			stage.addActor(beast);
		}
	}

	@Override
	public void show() {

		stage = new Stage();
		textStage = new Stage();

		playerPos = new Vector2(w / 2, 0);

		// first the ground
		Actor background = new Background("image/terrain.png");
		background.setSize(w, h);
		background.setOrigin(w / 2, h / 2);
		background.setPosition(0, 0);
		background.setTouchable(Touchable.enabled);
		stage.addActor(background);

		// then the player's base
		// Actor radar = new Background("image/zoneofterror.png");
		// radar.setSize(512,512);
		// radar.setOrigin(GameScreen.playerPos.x,GameScreen.playerPos.y);
		// radar.setPosition(-radar.getWidth()/2, -radar.getHeight()/2);
		// stage.addActor(radar);

		// set up the text
		// IMPORTANT ORDER
		
		titleText = new TextActor("scores", h, w, FontManager.getNormalLabel());

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
		
		// then the beasties
		nextWave(WINLOSE.NOOP);

		// then the light mask
		mask = new Mask("image/mask.png", w, h);
		mask.setSize(w, h);
		mask.setOrigin(mask.getWidth() / 2, mask.getHeight() / 2);
		mask.setPosition(0, 0);
		mask.setTouchable(Touchable.disabled);
		stage.addActor(mask);

		// spotlight definition
		spotlight = new Spotlight(w, h, new Vector2(0, 1075), new Vector2(w,
				980), new Vector2(0, 708), new Vector2(w, 860));

		// then the player
		Actor player = new Player();
		player.setPosition(playerPos.x, playerPos.y);
		stage.addActor(player);

		// scores added to main stage
		stage.addActor(titleText);

		// other disappearing text added to text Stage
		textStage.addActor(winLoseText);
		textStage.addActor(preWaveText);
		textStage.addActor(preWinConditions);
		textStage.addActor(preWinConditionKills);
		textStage.addActor(preWinConditionEscapes);
		textStage.addActor(preWinConditionFF);
		textStage.addActor(preWinConditionRescues);

		Gdx.input.setInputProcessor(stage);

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
		stage.dispose();
		textStage.dispose();
	}

}
