package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.OptionScreen;

public class LastStand extends Game {
	public static final int screenW = 1000;
	public static final int screenH = 720;
	public Skin style;
	public SpriteBatch batch;
	public MenuScreen menuScreen;
	public BattleScreen battleScreen;
	public OptionScreen optionScreen;

	private static Skin createSkin() {
		AssetManager manager = new AssetManager();
		manager.load("neon/skin/neon-ui.json", Skin.class);
		manager.finishLoading();
		return manager.get("neon/skin/neon-ui.json");
	}

	@Override
	public void create () {
		style = createSkin();
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this);
		battleScreen = new BattleScreen(this);
		optionScreen = new OptionScreen(this);
		setScreen(menuScreen);

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
}
