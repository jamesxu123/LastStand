package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Abstractions.EntityGroup;
import com.mygdx.game.Abstractions.EntityMap;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.LastStand;
import com.mygdx.game.Player;
import com.mygdx.game.Spawners.FighterSpawner;
import com.mygdx.game.Spawners.ProjectileSpawner;
import com.mygdx.game.Spawners.TowerSpawner;
import com.mygdx.game.UIs.GameUI;
import com.mygdx.game.UIs.TowerUI;
import com.mygdx.game.Utilities;

import java.util.ArrayList;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

public class BattleScreen extends InputAdapter implements Screen {
    private LastStand game;
    private Stage entities;
    private InputMultiplexer inputs;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapObjects collisionObjs;
    private EntityGroup enemies;
    private EntityGroup projectiles;
    private EntityGroup towers;
    private EntityMap entityMap;
    private Array<RectangleMapObject> pathNodes;
    private GameUI gameUI;
    private TowerUI openTowerUI;
    private ArrayList<TowerUI> towerUIs;
    private Player player;

    public BattleScreen(LastStand game) {

        entityMap = new EntityMap(game.shapeRenderer);
        player = new Player();

        map = new TmxMapLoader().load("map1.tmx");
        //gets the objects embedded in the tiled map
        RectangleMapObject spawnPoint = map.getLayers().get("Start").getObjects().getByType(RectangleMapObject.class).get(0);
        pathNodes = map.getLayers().get("Path Nodes").getObjects().getByType(RectangleMapObject.class);


        //creates a new group which has a spawner. the spawner gets the initial spawn point
        enemies = new EntityGroup(new FighterSpawner((int) spawnPoint.getRectangle().x, (int) spawnPoint.getRectangle().y,
                spawnPoint.getProperties().get("Direction").toString(), "level_1", game.fighterDatas));
        gameUI = new GameUI(player, game.style, enemies);
        towers = new EntityGroup(new TowerSpawner(game.towerDatas));
        projectiles = new EntityGroup(new ProjectileSpawner(towers));

        camera = new OrthographicCamera(screenW, screenH);
        //camera is only needed to center the map on the screen
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1, game.batch);
        camera.setToOrtho(false);
        mapRenderer.setView(camera);

        collisionObjs = map.getLayers().get("Tower Placement").getObjects();
        //input multiplexer holds a bunch of input taking things and passes input to them
        inputs = new InputMultiplexer();
        entities = new Stage();
        this.game = game;
        inputs.addProcessor(gameUI.getStage());
        inputs.addProcessor(this);
        inputs.addProcessor(entities);
        towerUIs = new ArrayList<>();
        for (RectangleMapObject rectangleObject : collisionObjs.getByType(RectangleMapObject.class)) {


            Rectangle rectangle = rectangleObject.getRectangle();
            towerUIs.add(new TowerUI(rectangle, game.style, game.towerDatas, towers, game.shapeRenderer, player));
        }

    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputs);
        entities.addActor(projectiles);
        entities.addActor(enemies);
        entities.addActor(towers);

    }

    private void update(float delta) {
        entities.act(delta);
        //checks if the player still has hearts and if not gameover

        if (!player.isAlive()) {
            game.setScreen(game.gameOverScreen);
            return;
        }
        /* this is where we get all objects into our 2d array so that
        we can check collision,turn them and stuff*/

        entityMap.constructMap(enemies.getChildren(), player);
        entityMap.switchDirection(pathNodes);
        entityMap.constructMap(projectiles.getChildren(), player);
        entityMap.collide(delta);
        gameUI.update();
        for (Actor a : towers.getChildren()) {
            Tower t = (Tower) a;
            t.setInRadius(entityMap.getInRadius(t));
        }
        entityMap.resetMap();


    }

    @Override
    public void render(float delta) {

        update(delta);

        mapRenderer.render();


        gameUI.draw();
        if (openTowerUI != null) {
            openTowerUI.draw();

        }
        //entityMap.debug();
        entities.draw();
        //for




    }

    enum Current {
        PLAYING, Pause
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {


    }

    @Override
    public void dispose() {

    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //loops through all the objects and check if the x,y is on them
        if (openTowerUI != null) {
            inputs.removeProcessor(openTowerUI.getStage());
        }
        openTowerUI = null;

        for (TowerUI t : towerUIs) {
            if (t.getRect().contains(screenX, Utilities.convertMouseY(screenY))) {


                openTowerUI = t;
                inputs.addProcessor(openTowerUI.getStage());


            }
        }


        return false;
    }
}
