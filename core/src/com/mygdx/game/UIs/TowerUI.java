package com.mygdx.game.UIs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Abstractions.EntityGroup;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.EntityUtilities.TowerData;
import com.mygdx.game.Player;

import java.util.ArrayList;

//ui that is placed when a player touches a tower placing spot
public class TowerUI extends Table {
    private Rectangle rect;
    private int index = 0;
    private EntityGroup group;
    private Tower tower;
    private Label priceLabel;
    private ShapeRenderer shapeRenderer;
    private Image towerImg;
    private ArrayList<TowerData> towerDatas;


    public TowerUI(Rectangle rect, Skin style, ArrayList<TowerData> towerDatas, EntityGroup towerGroup, ShapeRenderer shapeRenderer, Player player) {
        super(style);
        index = 0;

        this.rect = rect;
        this.shapeRenderer = shapeRenderer;
        this.towerDatas = towerDatas;
        towerImg = new Image(towerDatas.get(index).animations.getKeyFrame(0));
        priceLabel = new Label(Integer.toString(towerDatas.get(index).cost), style);
        //Circle c=new Circle(rectangle.getCenter(new Vector2()),15);
        setDebug(true);
        setPosition(rect.x - rect.width / 2, rect.y - rect.height / 2);
        setSize(200, 150);

//        table.add(curTower);
        ImageButton right = new ImageButton(new TextureRegionDrawable(new Texture("forward.png")));
        ImageButton left = new ImageButton(new TextureRegionDrawable(new Texture("backward.png")));
        left.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (index > 0) {
                    index -= 1;
                } else {
                    index = towerDatas.size() - 1;
                }
                priceLabel.setText(Integer.toString(towerDatas.get(index).cost));
                towerImg.setDrawable(new TextureRegionDrawable(towerDatas.get(index).animations.getKeyFrame(0)));
                super.clicked(event, x, y);
            }
        });
        right.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = (index + 1) % towerDatas.size();
                priceLabel.setText(Integer.toString(towerDatas.get(index).cost));
                towerImg.setDrawable(new TextureRegionDrawable(towerDatas.get(index).animations.getKeyFrame(0)));
                super.clicked(event, x, y);
            }
        });

        this.group = towerGroup;
        add(left);
        add(towerImg);
        add(right);
        row();
        TextButton payButton = new TextButton("buy", style);
        payButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float clickX, float clickY) {
                if (player.getMoney() - towerDatas.get(index).cost >= 0) {

                    super.clicked(event, clickX, clickY);
                    group.getSpawner().spawn((int) rect.x, (int) rect.y, towerDatas.indexOf(towerDatas.get(index)));
                    player.addMoney(-towerDatas.get(index).cost);

                }
            }
        });
        add(priceLabel).center();
        row();
        add(payButton).size(40, 20).center();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {


        super.draw(batch, parentAlpha);
        shapeRenderer.setColor(0, 0, 0, 0.4f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (tower == null) {
            shapeRenderer.circle(rect.x + rect.width / 2, rect.y + rect.height / 2, towerDatas.get(index).radius);

        } else {
            shapeRenderer.circle(rect.x + rect.width / 2, rect.y + rect.height / 2, tower.data.radius);


        }
        shapeRenderer.end();
    }


    public Rectangle getRect() {
        return rect;
    }
}
