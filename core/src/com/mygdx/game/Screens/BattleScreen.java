package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.LastStand;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

public class BattleScreen implements Screen, InputProcessor {
    private LastStand game;
    private Stage ui;
    private Stage entities;
    private InputMultiplexer inputs;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private TiledMap map = new TmxMapLoader().load("map1.tmx");
    private OrthogonalTiledMapRenderer mapRenderer;


    public BattleScreen(LastStand game) {

        camera = new OrthographicCamera(screenW, screenH);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1, game.batch);
        camera.setToOrtho(false);
        mapRenderer.setView(camera);
        //map.getLayers().get(2).getObjects()


        inputs = new InputMultiplexer();
        ui = new Stage();
        entities = new Stage();
        this.game = game;
        inputs.addProcessor(ui);
        inputs.addProcessor(entities);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputs);

    }

    @Override
    public void render(float delta) {
        mapRenderer.render();

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
