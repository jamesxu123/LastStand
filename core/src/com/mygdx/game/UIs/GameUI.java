package com.mygdx.game.UIs;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Abstractions.EntityGroup;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Player;
import com.mygdx.game.Utilities;

import java.util.ArrayList;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

//the actual gameui that is the same throughout
//handles all input for the battlescreen
public class GameUI extends InputAdapter {
    private Stage stage;
    private final Label livesLabel;
    private final Label moneyLabel;
    private final Label levelLabel;
    private Fighter fighter;
    private final Label name;
    private final Label health;
    private TowerUI openTowerUI;
    private final ArrayList<TowerUI> towerUIs;
    private final EntityGroup fighterGroup;


    private final Player player;


    public GameUI(Player player, Skin style, EntityGroup entityGroup, AssetManager manager, PauseMenu pauseMenu) {
        this.player = player;
        pauseMenu.setPosition(400, 300);
        Texture menu = manager.get("buttons/menu.png");
        ImageButton pauseButton = new ImageButton(new TextureRegionDrawable(menu));
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                stage.addActor(pauseMenu);

            }
        });
        pauseButton.setPosition(screenW - 50, screenH - 50);

        Table gameTable = new Table();
        levelLabel = new Label("Level:", style, "subtitle");
        moneyLabel = new Label("Money:", style, "subtitle");
        livesLabel = new Label("Lives:", style, "subtitle");
        fighterGroup = entityGroup;

        towerUIs = new ArrayList<>();
        health = new Label("", style);
        name = new Label("", style);
        gameTable.add(levelLabel).width(100).center();
        gameTable.row();
        gameTable.add(moneyLabel).width(100).center();
        gameTable.row();
        gameTable.add(livesLabel).width(100).center();
        gameTable.setPosition(75, screenH - 50);

        //entityTable.add(info);
        Texture down = manager.get("buttons/skullButtonDown.png");
        Texture up = manager.get("buttons/skullButtonUp.png");
        Window pane = new Window("", style);
        ImageButton playButton = new ImageButton(new TextureRegionDrawable(up)
                , new TextureRegionDrawable(down));
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                entityGroup.getSpawner().setSpawning(true);
            }
        });
        pane.setScale(1.2f);
        pane.setResizable(true);
        pane.add(playButton);
        pane.row();
        pane.add(health);
        pane.row();
        pane.add(name);
        pane.setPosition(screenW - 200, 0);
        stage = new Stage();
        stage.addActor(gameTable);
        stage.addActor(pane);
        stage.addActor(pauseButton);
    }

    public Stage getStage() {
        return stage;
    }

    public void update() {
        player.setLevel(fighterGroup.getSpawner().getWave());
        levelLabel.setText(String.format("Level: %d", player.getLevel()));
        moneyLabel.setText(String.format("Money: %d", player.getMoney()));
        livesLabel.setText(String.format("Lives: %d", player.getLives()));

    }

    public ArrayList<TowerUI> getTowerUIs() {
        return towerUIs;
    }

    //updates info when a fighter is clicked
    private void updateInfo() {
        if (fighter.getClass() == Fighter.class) {
            health.setText(String.format("HP: %d", (int) fighter.getHealth()));
            name.setText(String.format("NAME: %s", fighter.data.name));
            if (!fighter.isAlive()) {
                fighter = null;
            }
        }
    }

    public void changeInfo(Fighter fighter) {
        this.fighter = fighter;
    }
    public void draw() {
        if (fighter != null) {
            updateInfo();
        }
        stage.draw();
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //loops through all the objects and check if the x,y is on them
        if (stage.touchDown(screenX, screenY, pointer, button)) {
            return true;
        }
        if (openTowerUI != null) {
            openTowerUI.remove();
            openTowerUI = null;
        }

        for (TowerUI t : towerUIs) {
            if (t.getRect().contains(screenX, Utilities.convertMouseY(screenY))) {
                openTowerUI = t;
                stage.addActor(openTowerUI);
            }
        }
        return false;
    }
}
