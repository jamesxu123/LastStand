package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.LastStand;
import com.mygdx.game.Spawners.FighterSpawner;

//the optionScreen that allows you to change if spawn happens constantly and mute or turn on music
public class OptionScreen implements Screen {
    private LastStand game;
    private Stage ui;
    private Texture bkg;


    public OptionScreen(LastStand game) {

        this.game = game;
        bkg = game.manager.get("backgrounds/selectionbkg.png");
        ui = new Stage();
        Label title = new Label("Options", game.style);
        title.setFontScale(1.5f);
        title.setPosition(512 - title.getWidth() / 2, 768 - 100);
        CheckBox musicToggle = new CheckBox("music", game.style);
        CheckBox constantSpawnToggle = new CheckBox("Spawn Constantly", game.style);
        constantSpawnToggle.setSize(50, 50);
        constantSpawnToggle.setPosition(120, 200);
        musicToggle.setSize(50, 50);
        musicToggle.setPosition(120, 300);
        Texture back = game.manager.get("buttons/back.png");
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(back));
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.menuScreen);
                super.clicked(event, x, y);
            }
        });
        backButton.setPosition(120, 400);

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
                    game.music.setLooping(true);
                    game.music.play();
                } else {
                    game.music.stop();
                }
                super.clicked(event, x, y);
            }
        });


        ui.addActor(constantSpawnToggle);
        ui.addActor(musicToggle);
        ui.addActor(title);
        ui.addActor(backButton);


    }

    @Override
    public void show() {
        game.batch.begin();
        game.batch.draw(bkg, 0, 0);
        game.batch.end();

        Gdx.input.setInputProcessor(ui);


    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(bkg, 0, 0);
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
