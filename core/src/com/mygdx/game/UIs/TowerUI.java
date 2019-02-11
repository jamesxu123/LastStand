package com.mygdx.game.UIs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
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
    private int level = 0;
    private EntityGroup group;
    private Tower tower;
    private Label priceLabel;
    private ShapeRenderer shapeRenderer;
    private Image towerImg;
    private ArrayList<TowerData> towerDatas;
    private ImageButton left;
    private ImageButton right;
    private TextButton payButton;
    private TextButton sellButton;
    private Label padLabel;


    public TowerUI(Rectangle rect, Skin style, ArrayList<TowerData> towerDatas, EntityGroup towerGroup, ShapeRenderer shapeRenderer, Player player, AssetManager manager) {
        super(style);
        index = 0;
        padLabel = new Label("", style);

        this.rect = rect;
        this.shapeRenderer = shapeRenderer;
        this.towerDatas = towerDatas;
        this.group = towerGroup;
        towerImg = new Image(towerDatas.get(index).upgrades.get(level));
        priceLabel = new Label(Integer.toString(towerDatas.get(index).cost), style);
        //Circle c=new Circle(rectangle.getCenter(new Vector2()),15);
        setPosition(rect.x - 100 + rect.width / 2, rect.y - 75 + rect.height / 2);
        setSize(200, 150);
        Texture backward = manager.get("buttons/backward.png");
        Texture forward = manager.get("buttons/forward.png");
        right = new ImageButton(new TextureRegionDrawable(forward));
        left = new ImageButton(new TextureRegionDrawable(backward));
        left.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (index > 0) {
                    index -= 1;
                } else {
                    index = towerDatas.size() - 1;
                }
                priceLabel.setText(towerDatas.get(index).cost + "$");
                towerImg.setDrawable(new TextureRegionDrawable(towerDatas.get(index).upgrades.get(level)));
                super.clicked(event, x, y);
            }
        });
        right.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                index = (index + 1) % towerDatas.size();
                priceLabel.setText(towerDatas.get(index).cost + "$");
                towerImg.setDrawable(new TextureRegionDrawable(towerDatas.get(index).upgrades.get(level)));
                super.clicked(event, x, y);
            }
        });
        payButton = new TextButton("buy", style);
        payButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float clickX, float clickY) {
                if (tower == null) {
                    if (player.getMoney() - towerDatas.get(index).cost >= 0) {
                        group.getSpawner().spawn((int) rect.x, (int) rect.y, towerDatas.indexOf(towerDatas.get(index)));
                        int size = group.getSpawner().getGroup().getChildren().size;
                        tower = (Tower) group.getSpawner().getGroup().getChildren().get(size - 1);
                        towerImg.setDrawable(new TextureRegionDrawable(tower.data.upgrades.get(level + 1)));
                        player.addMoney(-towerDatas.get(index).cost);
                        priceLabel.setText(tower.data.cost * (2 + level) + "$");
                        createMenu();


                    }
                } else {
                    if (player.getMoney() - tower.data.cost * (2 + level) >= 0) {
                        level += 1;
                        tower.setLevel(level);
                        priceLabel.setText(Integer.toString(tower.data.cost * (2 + level)));
                        player.addMoney(-tower.data.cost * (2 + level));


                    }
                    if (tower.data.upgrades.size() > level + 1) {

                        towerImg.setDrawable(new TextureRegionDrawable(tower.data.upgrades.get(level + 1)));

                    } else {


                        payButton.remove();
                        towerImg.remove();
                        towerImg = null;
                        padTop(100);

                    }
                }

                super.clicked(event, clickX, clickY);
            }
        });
        sellButton = new TextButton("sell", style);
        sellButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float clickX, float clickY) {
                player.addMoney((tower.data.cost * (2 + level)) / 2);
                tower.remove();
                tower = null;
                level = 0;
                towerImg = new Image(towerDatas.get(index).upgrades.get(level));
                createMenu();
                super.clicked(event, clickX, clickY);

            }
        });
        createMenu();


    }

    public void createMenu() {
        clear();
        if (tower == null) {
            add(left);
            add(towerImg);
            add(right);
            row();
            row();
            add(padLabel);
            add(priceLabel);
            row();
            add(padLabel);
            add(payButton);


        } else if (tower.data.upgrades.size() > 1) {
            add(padLabel);
            add(towerImg);
            add(padLabel);
            row();
            add(padLabel);
            add(priceLabel);
            row();
            add(payButton);
            add(sellButton);

        } else {
            padTop(100);
            add(sellButton);
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {


        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(0, 0, 0, 0.4f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (tower == null) {
            shapeRenderer.circle(rect.x + rect.width / 2, rect.y + rect.height / 2, towerDatas.get(index).radius);

        } else {
            shapeRenderer.circle(rect.x + rect.width / 2, rect.y + rect.height / 2, tower.data.radius);
        }
        shapeRenderer.end();
        batch.begin();
        super.draw(batch, parentAlpha);

    }


    public Rectangle getRect() {
        return rect;
    }
}
