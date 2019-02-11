package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.LastStand;


public class MenuScreen implements Screen {
    private LastStand game;
    private Texture bkg;
    private Label mapName;
    private Stage ui;

    public MenuScreen(final LastStand game) {
        //font demo
        this.game = game;
        bkg = game.manager.get("backgrounds/menubkg.jpg");

        ui = new Stage();
        TextButton playButton = new TextButton("Play", game.style);
        playButton.setSize(150, 100);
        playButton.setPosition(300, 300);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.battleScreen);
            }
        });
        TextButton optionButton = new TextButton("Options", game.style);
        optionButton.setSize(150, 100);
        optionButton.setPosition(200, 100);
        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.optionScreen);
            }
        });
        ImageButton leftButton = new ImageButton(game.style);
        leftButton.setPosition(800, 350);
        leftButton.setSize(50, 50);
        ImageButton rightButton = new ImageButton(game.style);
        rightButton.setPosition(600, 350);
        rightButton.setSize(50, 50);

        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.mapIndex > 0) {
                    game.mapIndex -= 1;
                } else {
                    game.mapIndex = game.mapDatas.size() - 1;
                }
                mapName.setText(game.mapDatas.get(game.mapIndex).name);
                super.clicked(event, x, y);
            }
        });
        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.mapIndex = (game.mapIndex + 1) % game.mapDatas.size();
                mapName.setText(game.mapDatas.get(game.mapIndex).name);
                super.clicked(event, x, y);
            }
        });

        mapName = new Label(game.mapDatas.get(game.mapIndex).name, game.style);
        mapName.setPosition(400, 100);

        ui.addActor(playButton);
        ui.addActor(optionButton);
        ui.addActor(mapName);

        ui.addActor(leftButton);
        ui.addActor(rightButton);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(ui);


    }

    @Override
    public void render(float delta) {
        game.batch.begin();


        game.batch.draw(bkg, 0, 0);
        game.batch.draw(game.mapDatas.get(game.mapIndex).icon, 400, 400);
        game.batch.end();
        ui.draw();


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
