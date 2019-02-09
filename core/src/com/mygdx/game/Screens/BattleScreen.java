package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
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

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

public class BattleScreen implements Screen {
    private LastStand game;
    private Stage entities;
    private InputMultiplexer inputs;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapObjects collisionObjs;
    private EntityGroup enemies;
    private EntityGroup projectiles;
    private EntityGroup towers;
    private EntityMap entityMap;
    private Array<RectangleMapObject> pathNodes;
    private GameUI gameUI;

    private Player player;

    public BattleScreen(LastStand game) {
        this.game = game;
    }



    @Override
    public void show() {
        entityMap = new EntityMap(game.shapeRenderer);
        player = new Player();
        //gets the objects embedded in the tiled map
        RectangleMapObject spawnPoint = game.getMap().getLayers().get("Start").getObjects().getByType(RectangleMapObject.class).get(0);
        pathNodes = game.getMap().getLayers().get("Path Nodes").getObjects().getByType(RectangleMapObject.class);

        //creates a new group which has a spawner. the spawner gets the initial spawn point

        FighterSpawner fighterSpawner = new FighterSpawner((int) spawnPoint.getRectangle().x, (int) spawnPoint.getRectangle().y,
                spawnPoint.getProperties().get("Direction").toString(), game.fighterDatas);
        enemies = new EntityGroup(fighterSpawner);
        gameUI = new GameUI(player, game.style, enemies, this);
        fighterSpawner.setGameUI(gameUI);

        towers = new EntityGroup(new TowerSpawner(game.towerDatas));
        projectiles = new EntityGroup(new ProjectileSpawner(towers, player));

        camera = new OrthographicCamera(screenW, screenH);
        //camera is only needed to center the map on the screen
        mapRenderer = new OrthogonalTiledMapRenderer(game.getMap(), 1, game.batch);
        camera.setToOrtho(false);
        mapRenderer.setView(camera);

        collisionObjs = game.getMap().getLayers().get("Tower Placement").getObjects();
        //input multiplexer holds a bunch of input taking things and passes input to them
        inputs = new InputMultiplexer();
        entities = new Stage();

        inputs.addProcessor(gameUI);
        inputs.addProcessor(gameUI.getStage());
        inputs.addProcessor(entities);

        for (RectangleMapObject rectangleObject : collisionObjs.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            gameUI.getTowerUIs().add(new TowerUI(rectangle, game.style, game.towerDatas, towers, game.shapeRenderer, player));
        }

        Gdx.input.setInputProcessor(inputs);
        entities.addActor(towers);
        entities.addActor(enemies);
        entities.addActor(projectiles);

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
        entityMap.collide(delta,projectiles);
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
        entities.draw();


    }

    public void debug() {
        entityMap.debug();
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (TowerUI t : gameUI.getTowerUIs()) {

            game.shapeRenderer.rect(t.getRect().x, t.getRect().y, t.getRect().width, t.getRect().height);
        }
        game.shapeRenderer.end();
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (RectangleMapObject rmo : pathNodes) {
            Rectangle r = rmo.getRectangle();
            game.shapeRenderer.rect(r.x, r.y, r.width, r.height);
        }
        game.shapeRenderer.end();

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


}
