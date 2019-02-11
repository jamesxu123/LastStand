package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.LastStand;
import com.mygdx.game.Spawners.FighterSpawner;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

//the optionScreen that allows you to change if spawn happens constantly and mute or turn on music
public class OptionScreen implements Screen {
    private final LastStand game;
    private final Stage ui;
    private final Texture bkg;


    public OptionScreen(LastStand game) {

        this.game = game;
        bkg = game.manager.get("backgrounds/bkgoptions.jpg");
        ui = new Stage(); //Create stage to hold UI elements
        Label title = new Label("Options", game.style, "title");
        //title.setFontScale(1.5f);
        title.setPosition(screenW / 2 - title.getWidth() / 2, screenH - 100);
        Button musicToggle = new Button(game.style, "music"); //Music on/off
        Label spawnLabel = new Label("Spawn Constantly (HARD)", game.style);
        spawnLabel.setPosition(120, 150);
        CheckBox constantSpawnToggle = new CheckBox("", game.style, "switch"); //Gets rid of waves, super hard move


        constantSpawnToggle.setPosition(500, 280);
        musicToggle.setSize(50, 50);
        musicToggle.setPosition(500, 350);
        Texture back = game.manager.get("buttons/back.png"); //Texture for back button
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(back)); //Back button
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Return user to menu
                game.setScreen(game.menuScreen);
                super.clicked(event, x, y);
            }
        });
        backButton.setPosition(120, screenH - 100);

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
                    game.music.setLooping(true); //Keep music playing
                    game.music.play();
                } else {
                    game.music.stop();
                }
                super.clicked(event, x, y);
            }
        });

        ui.addActor(spawnLabel);
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
