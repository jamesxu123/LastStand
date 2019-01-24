package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.Screens.GameOverScreen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.OptionScreen;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

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
	public ArrayList<Texture> towerIcons;
	public ArrayList<AniContainer> animations;



	private static Skin createSkin() {
		AssetManager manager = new AssetManager();
		manager.load("orange/skin/uiskin.json", Skin.class);
		manager.finishLoading();
		return manager.get("orange/skin/uiskin.json");
	}

	private ArrayList<AniContainer> createFighterAnimations(String spritesPath) {
		ArrayList<AniContainer> aniContainers = new ArrayList<>();
		for (File p : new File(spritesPath).listFiles((d, name) -> !name.equals(".DS_Store"))) {
			AniContainer sprites = new AniContainer();
			File[] stateFiles = p.listFiles();
			for (File f : stateFiles) {

				if (f.getName().equals(".DS_Store")) continue;
				States state = States.valueOf(f.getName());
				File[] dirFiles = f.listFiles();
				for (File d : dirFiles) {

					if (d.getName().equals(".DS_Store")) continue;
					Directions direction = Directions.valueOf(d.getName());
					ArrayList<String> picList = new ArrayList<>();
					for (File c : d.listFiles()) {
						if (c.getName().equals(".DS_Store")) continue;
						picList.add(c.getPath());
					}
					Collections.sort(picList);
					Texture[] frames = new Texture[picList.size()];
					for (int i = 0; i < picList.size(); i++) {
						frames[i] = new Texture(picList.get(i));

					}
					sprites.put(state, direction, new Animation<Texture>(0.04f, frames));
				}
			}
			aniContainers.add(sprites);

		}
		return aniContainers;


	}



	@Override
	public void create () {
		style = createSkin();
		towerIcons = new ArrayList<>();
		towerIcons.addAll(Arrays.asList(new File("sprites/Tower")
				.listFiles((name, dir) -> !name.equals(".DS_Store"))).stream()
				.map(n -> new Texture(n.getPath())).collect(Collectors.toList()));
		animations = createFighterAnimations("sprites/FIGHTER");
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
