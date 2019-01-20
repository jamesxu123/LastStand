package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TowerPlaceUI {
    private Table table;
    private Stage stage;

    public TowerPlaceUI(Rectangle rectangle, Skin style) {
        stage = new Stage();
        table = new Table();
        table.setPosition(rectangle.x, rectangle.y);
        table.add(new Label("why u always lyin", style));
        stage.addActor(table);



    }

    public void draw() {
        stage.draw();

    }

}
