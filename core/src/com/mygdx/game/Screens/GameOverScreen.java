package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.LastStand;
import com.mygdx.game.Scores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class GameOverScreen implements Screen {
    private LastStand game;
    private Stage ui = new Stage();
    private ArrayList<Integer> topScores = new ArrayList<>();

    public GameOverScreen(LastStand game) {
        this.game = game;
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

        menu.setPosition(100, 768 - 200);
        quit.setPosition(100, 768 - 200 - menu.getHeight() - 25);
        ui.addActor(menu);
        ui.addActor(quit);

    }

    @Override
    public void show() {
        Label title = new Label(String.format("Noob u lost haha wave %d", game.player.getLevel()), game.style);
        title.setFontScale(1.5f);
        title.setPosition(512 - title.getWidth() / 2, 768 - 100);
        ui.addActor(title);
        Gdx.input.setInputProcessor(ui);
        Scores.readScores().thenAccept(scores -> {
            Collections.sort(scores);
            Collections.reverse(scores);
            System.out.println(Arrays.toString(scores.toArray()));
            for (int i = 0; i < 5; i++) {
                topScores.add(scores.get(i));
            }
        });
    }

    @Override
    public void render(float delta) {
        int counter = 0;
        for (int score : topScores) {
            TextField scoreField = new TextField(counter + ". " + score, game.style);
            scoreField.setDisabled(true);
            scoreField.setPosition(500, 500 - scoreField.getHeight() * counter - 20);
            counter++;
            ui.addActor(scoreField);

        }
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
