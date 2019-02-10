package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.LastStand;
import com.mygdx.game.Spawners.FighterSpawner;

public class OptionScreen implements Screen {
    private LastStand game;
    private Stage ui;

    public OptionScreen(LastStand game) {

        this.game = game;
        ui = new Stage();
        Label title = new Label("Options", game.style);
        title.setFontScale(1.5f);
        title.setPosition(512 - title.getWidth() / 2, 768 - 100);
        CheckBox musicToggle = new CheckBox("music", game.style);
        CheckBox constantSpawnToggle = new CheckBox("Spawn Constantly", game.style);
        constantSpawnToggle.setSize(120, 100);
        constantSpawnToggle.setPosition(100, 300);
        musicToggle.setSize(300, 400);
        musicToggle.setPosition(100, 400);
        ImageButton backButton = new ImageButton(game.style, game.manager.get("buttons/back.png"));
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.menuScreen);
                super.clicked(event, x, y);
            }
        });

        constantSpawnToggle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (constantSpawnToggle.isChecked()) {
                    FighterSpawner.setConstantSpawn(true);


                } else {
                    FighterSpawner.setConstantSpawn(false);

                }



            }
        });
        musicToggle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (musicToggle.isChecked()) {


                }
                super.clicked(event, x, y);
            }
        });


        ui.addActor(constantSpawnToggle);
        ui.addActor(musicToggle);
        ui.addActor(title);


    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(ui);


    }

    @Override
    public void render(float delta) {
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
