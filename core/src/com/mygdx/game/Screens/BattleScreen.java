package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Abstractions.EntityGroup;
import com.mygdx.game.Abstractions.EntityMap;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.Projectile;
import com.mygdx.game.LastStand;

import java.awt.*;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;
import static com.mygdx.game.Utilities.convertMouseY;

public class BattleScreen implements Screen, InputProcessor {
    private LastStand game;
    private Stage ui;
    private Stage entities;
    private InputMultiplexer inputs;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapObjects collisionObjs;
    private EntityGroup fighters;
    private EntityGroup projectiles;


    public BattleScreen(LastStand game) {
        fighters = new EntityGroup();
        projectiles = new EntityGroup();
        map=new TmxMapLoader().load("map1.tmx");
        camera = new OrthographicCamera(screenW, screenH);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1, game.batch);
        camera.setToOrtho(false);
        mapRenderer.setView(camera);
        //map.getLayers().get(2).getObjects()

        collisionObjs= map.getLayers().get("Object Layer 1").getObjects();
        inputs = new InputMultiplexer();
        ui = new Stage();
        entities = new Stage();
        this.game = game;
        inputs.addProcessor(this);
        inputs.addProcessor(ui);
        inputs.addProcessor(entities);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputs);
        fighters.addActor(new Fighter(game.enemies.get(0), 200, 100));
        fighters.addActor(new Fighter(game.enemies.get(1), 200, 200));
        projectiles.addActor(new Projectile(0, 0, 45, 2, new Point(0,0), new Texture("sprites/knight.png"), new EntityMap()));
        entities.addActor(projectiles);
        entities.addActor(fighters);
    }

    @Override
    public void render(float delta) {
        mapRenderer.render();
        ui.draw();
        entities.act(delta);
        entities.draw();
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
