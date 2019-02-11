package com.mygdx.game.UIs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.LastStand;

//pause menu which has two options;
//resume or go to menu
public class PauseMenu extends Table {

    private final LastStand game;

    public PauseMenu(LastStand game) {
        this.game = game;


        TextButton menuButton = new TextButton("Menu", game.style);
        Texture resume = game.manager.get("buttons/playButton.png");
        ImageButton resumeButton = new ImageButton(new TextureRegionDrawable(resume));
        //sends you to menu and recreates the game
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.initialize();
                game.setScreen(game.menuScreen);
                super.clicked(event, x, y);
            }
        });
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
                super.clicked(event, x, y);
            }
        });
        add(resumeButton);
        row();
        add(menuButton);

    }

    //setParent is overrided so that when it is opened the screen updates can be paused
    //I know very genius Nithin
    @Override
    protected void setParent(Group parent) {
        //if parent is null that means it is being removed
        game.battleScreen.pause = parent != null;

        super.setParent(parent);
    }


}
