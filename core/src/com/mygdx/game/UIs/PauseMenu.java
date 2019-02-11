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

public class PauseMenu extends Table {

    LastStand game;

    public PauseMenu(LastStand game) {
        this.game = game;


        TextButton menuButton = new TextButton("Menu", game.style);
        Texture resume = game.manager.get("buttons/playButton.png");
        ImageButton resumeButton = new ImageButton(new TextureRegionDrawable(resume));
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
                game.battleScreen.pause = false;
                remove();

                super.clicked(event, x, y);
            }
        });
        add(resumeButton);
        add(menuButton);

    }

    @Override
    protected void setParent(Group parent) {
        if (parent != null) {
            game.battleScreen.pause = true;

        }

        super.setParent(parent);
    }


}
