package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.LastStand;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OptionScreen implements Screen {
    private LastStand game;
    private File waveFile;
    private BufferedWriter writer;
    private Stage ui;
    private int waveSize = 25;
    private int troops = 0;
    private int number = 0;
    private float time = 0;
    private boolean start = false;

    public OptionScreen(LastStand game) {

        this.game = game;
        ui = new Stage();

        TextButton btn = new TextButton("1", game.style);
        TextButton btn2 = new TextButton("2", game.style);
        btn.setPosition(100, 300);
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                number = 1;
            }
        });
        btn2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                number = 2;
            }
        });


        waveFile = new File("wave1.txt");
        try {
            writer = new BufferedWriter(new FileWriter(waveFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui.addActor(btn);
        ui.addActor(btn2);





    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(ui);


    }

    @Override
    public void render(float delta) {
        ui.draw();
        if (start) {
            time += delta;
        }

        if (number != 0) {
            troops += 1;
            start = true;
            try {
                System.out.println(number);
                writer.append(time + " " + number);
                writer.append('\n');
                time = 0;
                number = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (troops >= waveSize) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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
