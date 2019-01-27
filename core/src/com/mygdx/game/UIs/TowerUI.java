package com.mygdx.game.UIs;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Abstractions.EntityGroup;
import com.mygdx.game.EntityUtilities.TowerData;

import java.util.ArrayList;

//ui that is placed when a player touches a tower placing spot
public class TowerUI {
    private Rectangle rect;
    private Table table;
    private Stage stage;
    private int index;
    private ArrayList<TowerData> towers;
    private EntityGroup group;

    private int level;

    public TowerUI(Rectangle rect, Skin style, ArrayList<TowerData> towers, EntityGroup towerGroup) {
        index = 0;
        this.towers = towers;
        stage = new Stage();
        this.rect = rect;


        //Circle c=new Circle(rectangle.getCenter(new Vector2()),15);
        table = new Table(style);
        table.setPosition(rect.x, rect.y);
        table.setSize(100, 100);
        //replace get key frame with icon later
        Image curTower = new Image(towers.get(index).getAnimations().getKeyFrame(0));
        table.add(curTower);
        table.row();
        this.group = towerGroup;

        TextButton payButton = new TextButton("buy", style);
        payButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float clickX, float clickY) {
                System.out.println(true);
                super.clicked(event, clickX, clickY);
                group.getSpawner().spawn((int) rect.x, (int) rect.y, index);

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

    public Rectangle getRect() {
        return rect;
    }
}
