package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.LastStand;

public class LoadingScreen implements Screen {
    private LastStand game;
    private Label loadingLabel;

    public LoadingScreen(LastStand game) {
        loadingLabel = new Label("", game.style);
        loadingLabel.setPosition(400, 400);
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(game.batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(game.batch.getTransformMatrix());
        if (game.manager.update()) {
            if (game.manager.isLoaded("sprites/TOWER/WIZARD/wizard-tower.png")) {
                game.initialize();
            } else {
            game.manager.load("sprites/TOWER/WIZARD/wizard-tower.png", Texture.class);
            }
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(100, 359, 824, 50);
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.rect(100, 359, 824 * game.manager.getProgress(), 50);
        shapeRenderer.end();
//        loadingLabel.setText(Float.toString(game.manager.getProgress()));
//        game.batch.begin();
//        loadingLabel.draw(game.batch, 1);
//        game.batch.end();
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
