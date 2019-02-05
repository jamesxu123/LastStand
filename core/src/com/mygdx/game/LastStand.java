package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.EntityUtilities.FighterData;
import com.mygdx.game.EntityUtilities.TowerData;
import com.mygdx.game.Screens.*;

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
    public JsonReader jsonReader;

    //recursively gets all files to load and puts it in the asynchronous loaders queue
    public void loadAllFiles(FileHandle file) {
        for (FileHandle f : file.list()) {
            if (f.isDirectory()) {

                loadAllFiles(f);
            } else {
                switch (f.extension()) {
                    case "png":
                    case "jpg":
                        manager.load(f.path(), Texture.class);
                        break;

                }
            }
        }

    }


    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        //skin is needed for loadingScreen so it is loaded beforehand
        jsonReader = new JsonReader();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("orange/skin/uiskin.json", Skin.class);
        manager.finishLoading();
        style = manager.get("orange/skin/uiskin.json");
        loadAllFiles(new FileHandle("sprites/"));
        loadAllFiles(new FileHandle("backgrounds/"));
        for (FileHandle f : new FileHandle("maps/").list()) {
            if (f.extension().equals("tmx")) {
                manager.load(f.path(), TiledMap.class);
            }

        }

        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }

    //called when manager is done loading everything from loadingscreen
    public void initialize() {
        JsonValue objects = jsonReader.parse(new FileHandle("entities.json"));
        towerDatas = new ArrayList<>();
        fighterDatas = new ArrayList<>();
        for (int i = 0; i < objects.get("towers").size; i++) {
            towerDatas.add(new TowerData(objects.get("towers").get(i), objects.get("projectiles").get(objects.get("towers").get(i).getString("projectile")), manager));
        }
        for (int i = 0; i < objects.get("fighters").size; i++) {
            fighterDatas.add(new FighterData(objects.get("fighters").get(i), manager));
        }

        gameOverScreen = new GameOverScreen(this);
        menuScreen = new MenuScreen(this);
        battleScreen = new BattleScreen(this);
        optionScreen = new OptionScreen(this);
        setScreen(menuScreen);
    }


    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //calls the current screens render
        super.render();
    }
}
