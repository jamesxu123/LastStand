package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
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
    public static final int screenW = 1024;
    public static final int screenH = 768;
    public Skin style;
    public SpriteBatch batch;
    public MenuScreen menuScreen;
    public BattleScreen battleScreen;
    public OptionScreen optionScreen;
    public GameOverScreen gameOverScreen;
    private LoadingScreen loadingScreen;
    public AssetManager manager = new AssetManager();
    public ShapeRenderer shapeRenderer;
    public ArrayList<FighterData> fighterDatas;
    public ArrayList<TowerData> towerDatas;
    public ArrayList<MapData> mapDatas;
    public int mapIndex = 0;
    private JsonReader jsonReader;
    public Player player;
    public Music music;

    //recursively gets all files to load and puts it in the asynchronous loaders queue
    private void loadAllFiles(FileHandle file) {
        //loads music
        music = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
        for (FileHandle f : file.list()) {
            if (f.isDirectory()) {
                loadAllFiles(f);
            } else {
                switch (f.extension()) {
                    case "png":
                    case "jpg":
                        manager.load(f.path(), Texture.class);
                        break;
                    case ".json":
                        manager.load(f.path(), Skin.class);
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
        manager.load("skins/shade/skin/uiskin.json", Skin.class);
        manager.finishLoading();
        style = manager.get("skins/shade/skin/uiskin.json");
        loadAllFiles(new FileHandle("sprites/"));
        for (FileHandle f : Utilities.listFiles(new FileHandle("maps/"))) {
            if (f.extension().equals("tmx")) {
                manager.load(f.path(), TiledMap.class);
            }
        }
        loadAllFiles(new FileHandle("skins/"));
        loadAllFiles(new FileHandle("backgrounds/"));
        loadAllFiles(new FileHandle("buttons/"));
        loadAllFiles(new FileHandle("mapIcons/"));


        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }

    //called when manager is done loading everything from loadingscreen
    public void initialize() {
        mapDatas = new ArrayList<>();
        JsonValue maps = jsonReader.parse(new FileHandle("maps.json"));
        for (int i = 0; i < maps.size; i++) {
            System.out.println(maps.get(i));
            mapDatas.add(new MapData(maps.get(i), manager));
        }
        JsonValue entities = jsonReader.parse(new FileHandle("entities.json"));
        towerDatas = new ArrayList<>();
        fighterDatas = new ArrayList<>();
        for (int i = 0; i < entities.get("towers").size; i++) {
            towerDatas.add(new TowerData(entities.get("towers").get(i), entities.get("projectiles").get(entities.get("towers").get(i).getString("projectile")), manager));
        }
        for (int i = 0; i < entities.get("fighters").size; i++) {
            fighterDatas.add(new FighterData(entities.get("fighters").get(i), manager));
        }
        player = new Player();
        gameOverScreen = new GameOverScreen(this);
        menuScreen = new MenuScreen(this);
        battleScreen = new BattleScreen(this);
        optionScreen = new OptionScreen(this);
        setScreen(menuScreen);
    }

    public TiledMap getMap() {
        return mapDatas.get(mapIndex).map;
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
