package com.mygdx.game.UIs;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Abstractions.EntityGroup;
import com.mygdx.game.Player;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.Utilities;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

//the actual gameui that is the same throughout
public class GameUI extends InputAdapter {
    private Stage stage;
    private Label livesLabel;
    private Label moneyLabel;
    private Label levelLabel;
    private Table infoTable;
    private Table table;
    private Window pane;
    private BattleScreen screen;


    private Player player;


    public GameUI(Player player, Skin style, EntityGroup entityGroup, BattleScreen screen) {
        this.player = player;
        table = new Table();
        levelLabel = new Label("Level:", style);
        moneyLabel = new Label("Money:", style);
        livesLabel = new Label("Lives:", style);
        this.screen=screen;
        table.setDebug(true);

        table.add(levelLabel).width(100).center();
        table.row();
        table.add(moneyLabel).width(100).center();
        table.row();
        table.add(livesLabel).width(100).center();
        table.setPosition(75, screenH - 50);
        pane = new Window("", style);
        ImageButton playButton = new ImageButton(new TextureRegionDrawable(new Texture("skullButtonUp.png"))
                , new TextureRegionDrawable(new Texture("skullButtonDown.png")));
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                entityGroup.getSpawner().setSpawning(true);
            }
        });
        pane.add(playButton);
        pane.setPosition(screenW, 0);
        stage = new Stage();
        stage.addActor(table);
        stage.addActor(pane);
    }

    public Stage getStage() {
        return stage;
    }

    public void update() {
        levelLabel.setText(String.format("Level: %d", player.getLevel()));
        moneyLabel.setText(String.format("Money: %d", player.getMoney()));
        livesLabel.setText(String.format("Lives: %d", player.getLives()));

    }
    public void changeInfo() {

    }
    public void draw() {

        stage.draw();
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //loops through all the objects and check if the x,y is on them
        if (screen.getOpenTowerUI() != null) {
            screen.getInputs().removeProcessor(screen.getOpenTowerUI().getStage());
        }
        screen.setOpenTowerUI(null);

        for (TowerUI t : screen.getTowerUIs()) {
            if (t.getRect().contains(screenX, Utilities.convertMouseY(screenY))) {


                screen.setOpenTowerUI(t);
                screen.getInputs().addProcessor(screen.getOpenTowerUI().getStage());


            }
        }


        return false;
    }
}
