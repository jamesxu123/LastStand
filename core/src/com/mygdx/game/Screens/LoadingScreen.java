package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
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

        if (game.manager.update()) {
            System.out.println(game.manager.getLoadedAssets());
            game.initialize();
        }
        loadingLabel.setText(Float.toString(game.manager.getProgress()));
        game.batch.begin();
        loadingLabel.draw(game.batch, 1);
        game.batch.end();
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
