package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.LastStand;
import com.mygdx.game.Player;

public class LoadingScreen implements Screen {
    private final LastStand game;
    private Stage stage;
    private ProgressBar godsProgressBar;

    public LoadingScreen(LastStand game) {
        Label loadingLabel = new Label("", game.style);
        loadingLabel.setPosition(400, 400);
        this.game = game;
        stage = new Stage();
        godsProgressBar = new ProgressBar(0, 10000, 1, false, game.style);
        godsProgressBar.setPosition(400, 300);
        //god level powers are activated when you press nithin's progressbar
        godsProgressBar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Player.setGodfinger(true);
                super.clicked(event, x, y);
            }
        });
        stage.addActor(godsProgressBar);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(game.batch.getProjectionMatrix()); //Needed for shapeRenderer to work alongside SpriteBatch
        shapeRenderer.setTransformMatrix(game.batch.getTransformMatrix());
        if (game.manager.update()) {
            //Loads file async, returns true when done
            if (game.manager.isLoaded("sprites/TOWER/WIZARD/wizard-tower-1.png") && game.manager.isLoaded("sprites/TOWER/WIZARD/wizard-tower-2.png") && game.manager.isLoaded("sprites/TOWER/WIZARD/wizard-tower-3.png") && game.manager.isLoaded("sprites/TOWER/WIZARD/wizard-tower-4.png")) {
                //Gdx seems to have weird issues with these files hmm.....
                //only for james not my mac
                game.initialize();
            } else {
                game.manager.load("sprites/TOWER/WIZARD/wizard-tower-1.png", Texture.class);
                game.manager.load("sprites/TOWER/WIZARD/wizard-tower-2.png", Texture.class);
                game.manager.load("sprites/TOWER/WIZARD/wizard-tower-3.png", Texture.class);
                game.manager.load("sprites/TOWER/WIZARD/wizard-tower-4.png", Texture.class);
            }
        }
        //james progress bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); //Nice progress bar
        shapeRenderer.rect(100, 359, 824, 50); //Back of the bar
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.rect(100, 359, 824 * game.manager.getProgress(), 50); //Progress!
        shapeRenderer.end();
        //nithin progress bar
        //although it may be smaller it has powers
        stage.draw();
        godsProgressBar.setValue(game.manager.getProgress() * 10000);


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
