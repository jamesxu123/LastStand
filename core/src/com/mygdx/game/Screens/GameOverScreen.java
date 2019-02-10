package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.LastStand;


public class GameOverScreen implements Screen {
    private LastStand game;
    private Stage ui;

    public GameOverScreen(LastStand game) {
        this.game = game;


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        TextButton menu = new TextButton("Back To Menu", game.style);
        TextButton quit = new TextButton("Exit", game.style);
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.initialize();
                super.clicked(event, x, y);
            }
        });
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                super.clicked(event, x, y);
            }
        });
        Label title = new Label("Noob u lost haha", game.style);
        title.setFontScale(1.5f);
        title.setPosition(512 - title.getWidth() / 2, 768 - 100);
        menu.setPosition(100, 768 - 200);
        quit.setPosition(100, 768 - 200 - menu.getHeight() - 25);
        ui.addActor(menu);
        ui.addActor(quit);
        ui.addActor(title);

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
