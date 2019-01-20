package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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
import com.mygdx.game.Abstractions.FighterSpawner;
import com.mygdx.game.Abstractions.Spawner;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.Projectile;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.LastStand;

import java.util.ArrayList;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;
import static com.mygdx.game.Utilities.convertMouseY;

public class BattleScreen implements Screen, InputProcessor {
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
    public BattleScreen(LastStand game) {

        entityMap = new EntityMap();
        map = new TmxMapLoader().load("map1.tmx");
        RectangleMapObject spawnPoint = map.getLayers().get("Start").getObjects().getByType(RectangleMapObject.class).get(0);
        pathNodes = map.getLayers().get("Path Nodes").getObjects().getByType(RectangleMapObject.class);


        enemies = new EntityGroup(new FighterSpawner("sprites/FIGHTER", Fighter.class,
                (int) spawnPoint.getRectangle().x, (int) spawnPoint.getRectangle().y, spawnPoint.getProperties().get("Direction").toString()));
        projectiles = new EntityGroup(new Spawner(Projectile.class));
        towers = new EntityGroup(new Spawner(Tower.class));

        camera = new OrthographicCamera(screenW, screenH);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1, game.batch);
        camera.setToOrtho(false);
        mapRenderer.setView(camera);
        //map.getLayers().get(2).getObjects()

        collisionObjs = map.getLayers().get("Tower Placement").getObjects();

        inputs = new InputMultiplexer();
        entities = new Stage();
        this.game = game;
        inputs.addProcessor(this);
        inputs.addProcessor(entities);

    }

    public void update() {
        ArrayList<Actor> movingEntities = new ArrayList<>();
        for (Actor a : enemies.getChildren()) {
            movingEntities.add(a);
        }
        for (Actor a : projectiles.getChildren()) {
            movingEntities.add(a);

        }

        entityMap.constructMap(movingEntities);
        entityMap.switchDirection(pathNodes);
        //entityMap.update(Gdx.graphics.getDeltaTime());
        //instead of being a tile map thing this can be a part of entity map


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputs);
        //projectiles.addActor(new Projectile(0, 0, 45, 2, new Point(0,0), new Texture("sprites/knight.png"), new EntityMap()));
        entities.addActor(projectiles);
        entities.addActor(enemies);
        entities.addActor(towers);
        //when game wave starts spawning is set to true
        //when wave is over it is set to false
        enemies.getSpawner().setSpawning(true);
    }

    @Override
    public void render(float delta) {
        update();
        mapRenderer.render();
        entities.act(delta);
        entities.draw();
    }

    enum Current {
        PLAYING
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
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (RectangleMapObject rectangleObject : collisionObjs.getByType(RectangleMapObject.class)) {


            Rectangle rectangle = rectangleObject.getRectangle();
            System.out.println(rectangle);
            if (rectangle.contains(screenX, convertMouseY(screenY))) {
                System.out.println(true);
                return true;

            }
        }
        System.out.println(false);


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;

    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
