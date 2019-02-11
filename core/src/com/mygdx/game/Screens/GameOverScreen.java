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
    private Stage ui;
    private ArrayList<Integer> topScores = new ArrayList<>();

    public GameOverScreen(LastStand game) {
        ui = new Stage();
        this.game = game;
        TextButton menu = new TextButton("Back To Menu", game.style); //Back to main menu
        TextButton quit = new TextButton("Exit", game.style); //Exit game
        menu.addListener(new ClickListener() {
            //When clicked, restart the game and send user back to menu
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.initialize();
                super.clicked(event, x, y);
            }
        });
        quit.addListener(new ClickListener() {
            //When clicked, exit game
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
        Label title = new Label(String.format("Dead on wave %d!", game.player.getLevel()), game.style); //This needs to be updated each time with correct turn number
        title.setFontScale(1.5f);
        title.setPosition(512 - title.getWidth() / 2, 768 - 100);
        ui.addActor(title);
        Gdx.input.setInputProcessor(ui);
        topScores = new ArrayList<>(); //Refresh for each time the screen is loaded
        Scores.readScores().thenAccept(scores -> { //Async get the scores and wait for completion
            Collections.sort(scores);
            Collections.reverse(scores); //Sort and reverse to get them in DSC order
            Label scoreLabel = new Label("Top Scores", game.style);
            scoreLabel.setPosition(550, 460);
            for (int i = 0; i < (scores.size() <= 5 ? scores.size() : 5); i++) { //Only take the top 5 results
//                topScores.add(scores.get(i));
                TextField scoreField = new TextField((i + 1) + ". " + scores.get(i), game.style);
                scoreField.setDisabled(true); //Disable input on textfield
                scoreField.setPosition(550, 500 - scoreField.getHeight() * i);
                ui.addActor(scoreField);
            }
        });
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
