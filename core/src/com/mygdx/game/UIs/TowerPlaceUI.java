package com.mygdx.game.UIs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Abstractions.EntityGroup;

import java.util.ArrayList;

//ui that is placed when a player touches a tower placing spot
public class TowerPlaceUI {
    private float x;
    private float y;
    private Table table;
    private Stage stage;
    private int index;
    private ArrayList<Texture> towers;
    private EntityGroup group;

    public TowerPlaceUI(float x, float y, Skin style, ArrayList<Texture> towers, EntityGroup towerGroup) {
        index = 0;
        this.towers = towers;
        stage = new Stage();


        //Circle c=new Circle(rectangle.getCenter(new Vector2()),15);
        table = new Table(style);
        table.setPosition(x, y);
        table.setSize(100, 100);
        Image curTower = new Image(towers.get(index));
        table.add(curTower);
        table.row();
        this.group = towerGroup;

        TextButton payButton = new TextButton("buy", style);
        payButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float clickX, float clickY) {
                super.clicked(event, clickX, clickY);
                group.getSpawner().spawn((int) x, (int) y, index);
            }
        });
        table.add(payButton).size(40, 20);


        table.setDebug(true);


        stage.addActor(table);
    }

    public void draw() {
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

}
