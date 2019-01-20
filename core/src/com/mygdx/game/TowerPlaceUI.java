package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TowerPlaceUI {
    private Table table;

    public TowerPlaceUI(Rectangle rectangle, Skin style) {
        table = new Table();
        table.setPosition(rectangle.x, rectangle.y);
        //table.add();


    }

}
