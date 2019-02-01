package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.EntityUtilities.FighterData;
import com.mygdx.game.EntityUtilities.TowerData;
import com.mygdx.game.Screens.*;

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
	public LoadingScreen loadingScreen;
	public AssetManager manager = new AssetManager();
	public ShapeRenderer shapeRenderer;
	public ArrayList<FighterData> fighterDatas;
	public ArrayList<TowerData> towerDatas;

	//recursively gets all files to load and puts it in the asynchronous loaders queue
	public void loadAllFiles(File file) {
		for (File f : Utilities.getListFiles(file)) {
			if (f.isDirectory()) {

				loadAllFiles(f);
			} else {
				switch (f.getName().split("\\.")[1]) {
					case "png":
					case "jpg":
						manager.load(f.getPath(), Texture.class);
						break;

					//figure out how to add text files and map
				}
			}
		}

	}





	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		//skin is needed for loadingScreen so it is loaded beforehand
		manager.load("orange/skin/uiskin.json", Skin.class);
		manager.finishLoading();
		style = manager.get("orange/skin/uiskin.json");
		loadAllFiles(new File("sprites/"));
		loadAllFiles(new File("backgrounds/"));
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	//called when mananger is done loading everything from loadingscreen
	public void initialize() {


		fighterDatas = new ArrayList<>();
		File[] fighterAnimations = Utilities.getListFiles(new File("sprites/FIGHTER"));
		for (int i = 0; i < fighterAnimations.length; i++) {
			fighterDatas.add(new FighterData("", fighterAnimations[i], manager));
		}
		towerDatas = new ArrayList<>();
		towerDatas.add(new TowerData("", new File("sprites/TOWER/wizard"), manager, Tower.class));
		//towerDatas.addAll(new TowerData("","sprites/TOWER/moneymaker"),manager, MoneyTower.class);
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
		//calls the current screens render
		super.render();
	}
}
