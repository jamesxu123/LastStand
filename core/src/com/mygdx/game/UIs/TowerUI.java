package com.mygdx.game.UIs;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Abstractions.EntityGroup;
import com.mygdx.game.EntityUtilities.TowerData;
import com.mygdx.game.Player;

import java.util.ArrayList;

//ui that is placed when a player touches a tower placing spot
public class TowerUI {
    private Rectangle rect;
    private Table table;
    private Group ui;
    private int index;
    private ArrayList<TowerData> towers;
    private EntityGroup group;
    private ShapeRenderer shapeRenderer;
    private Player p;

    public TowerUI(Rectangle rect, Skin style, ArrayList<TowerData> towers, EntityGroup towerGroup, ShapeRenderer shapeRenderer, Player player) {
        index = 0;
        this.towers = towers;
        ui = new Group();
        this.rect = rect;
        this.shapeRenderer = shapeRenderer;



        //Circle c=new Circle(rectangle.getCenter(new Vector2()),15);
        table = new Table(style);
        table.setPosition(rect.x, rect.y);
        table.setSize(rect.width, rect.height);

//        table.add(curTower);
        this.group = towerGroup;
        for (TowerData towerData : towers) {
            table.add(new Image(towerData.animations.getKeyFrame(0)));
            table.row();
            if (player.getMoney() - towerData.cost >= 0) {
                TextButton payButton = new TextButton("buy", style);
                payButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float clickX, float clickY) {

                        super.clicked(event, clickX, clickY);
                        group.getSpawner().spawn((int) rect.x, (int) rect.y, towers.indexOf(towerData));
                        player.addMoney(-towerData.cost);
                    }
                });
                table.add(payButton).size(40, 20);
                table.row();
            } else {
                Label label = new Label("Not Enough!", style);
                table.add(label).size(40, 20);
                table.row();
            }
        }



        table.setDebug(true);


        ui.addActor(table);

    }


    public Group getUI() {
        return ui;
    }

    public Rectangle getRect() {
        return rect;
    }
}
