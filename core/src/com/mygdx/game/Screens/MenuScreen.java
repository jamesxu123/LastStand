package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.LastStand;


public class MenuScreen implements Screen {
    private LastStand game;
    private Texture bkg = new Texture("backgrounds/menubkg.jpg");
    private TextButton playButton;
    private TextButton optionButton;
    private Stage ui;
    private BitmapFont font;

    public MenuScreen(final LastStand game) {
        //font demo
        this.game = game;
        Texture texture = new Texture(Gdx.files.internal("abject-failure.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("abject-failure.fnt"), new TextureRegion(texture), false);
        font.setColor(Color.RED);
        ui = new Stage();
        playButton = new TextButton("Play", game.style);
        playButton.setSize(150, 100);
        playButton.setPosition(300, 300);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.battleScreen);
            }
        });
        optionButton = new TextButton("Options", game.style);
        optionButton.setSize(150, 100);
        optionButton.setPosition(200, 100);
        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.optionScreen);
            }
        });

        ui.addActor(playButton);
        ui.addActor(optionButton);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(ui);


    }

    @Override
    public void render(float delta) {
        game.batch.begin();


        game.batch.draw(bkg, 0, 0);
        font.draw(game.batch, "Last Stand", 200, 200);
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
