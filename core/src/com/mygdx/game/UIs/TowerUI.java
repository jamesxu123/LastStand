package com.mygdx.game.UIs;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private Stage stage;
    private int index;
    private ArrayList<TowerData> towers;
    private EntityGroup group;

    private int level;
    private ShapeRenderer shapeRenderer;

    public TowerUI(Rectangle rect, Skin style, ArrayList<TowerData> towers, EntityGroup towerGroup, ShapeRenderer shapeRenderer, Player player) {
        index = 0;
        this.towers = towers;
        stage = new Stage();
        this.rect = rect;
        this.shapeRenderer = shapeRenderer;


        //Circle c=new Circle(rectangle.getCenter(new Vector2()),15);
        table = new Table(style);
        table.setPosition(rect.x, rect.y);
        table.setSize(100, 100);
        //replace get key frame with icon later
//        Image curTower = new Image(towers.get(index).animations.getKeyFrame(0));
        for (TowerData towerData : towers) {
            System.out.println(towerData.name);
            table.add(new Image(towerData.animations.getKeyFrame(0)));
            table.row();
            if (player.getMoney() - towerData.cost >= 0) {
                TextButton payButton = new TextButton("buy", style);
                payButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float clickX, float clickY) {
                        System.out.println(true);
                        super.clicked(event, clickX, clickY);
                        group.getSpawner().spawn((int) rect.x, (int) rect.y, index);
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
//        table.add(curTower);
        this.group = towerGroup;




        table.setDebug(true);


        stage.addActor(table);
    }


    public void draw() {
        shapeRenderer.setColor(0, 0, 0, 0.5f);

        stage.draw();
        shapeRenderer.begin(ShapeType.Filled);

        shapeRenderer.circle(rect.x + rect.width / 2, rect.y + rect.height / 2, towers.get(index).radius);
        shapeRenderer.end();

    }

    public Stage getStage() {
        return stage;
    }

    public Rectangle getRect() {
        return rect;
    }
}
