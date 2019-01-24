package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.EntityUtilities.Entity;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.Screens.GameOverScreen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.OptionScreen;

import java.io.File;
import java.util.ArrayList;

public class LastStand extends Game {
	public static final int screenW = 1000;
	public static final int screenH = 720;
	public int numLevels = 1;
	public int level = 0;
	public Skin style;
	public SpriteBatch batch;
	public MenuScreen menuScreen;
	public BattleScreen battleScreen;
	public OptionScreen optionScreen;
    public GameOverScreen gameOverScreen;
	//public ArrayList<Texture> towerIcons;
	public ArrayList<Entity> fighters;



	private static Skin createSkin() {
		AssetManager manager = new AssetManager();
		manager.load("orange/skin/uiskin.json", Skin.class);
		manager.finishLoading();
		return manager.get("orange/skin/uiskin.json");
	}




	@Override
	public void create () {
		style = createSkin();
		/*towerIcons = new ArrayList<>();
		towerIcons.addAll(Arrays.asList(new File("sprites/Tower")
				.listFiles((name, dir) -> !name.equals(".DS_Store"))).stream()
				.map(n -> new Texture(n.getPath())).collect(Collectors.toList()));
				*/
		fighters = new ArrayList<>();
		File[] fighterAnimations = Utilities.getListFiles(new File("sprites/FIGHTER"));
		//File[] fighterStats=Utilities.getListFiles(new File());
		for (int i = 0; i < fighterAnimations.length; i++) {
			fighters.add(new Entity("", fighterAnimations[i], Fighter.class));
		}
        batch = new SpriteBatch();
        gameOverScreen = new GameOverScreen(this);
		menuScreen = new MenuScreen(this);
		battleScreen = new BattleScreen(this);
		optionScreen = new OptionScreen(this);
		setScreen(menuScreen);

	}
	//public String getLevelFile(){

	//}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
}
