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
    private final Rectangle rect;
    private int index;
    private int level;
    private final EntityGroup group;
    private Tower tower;
    private final Label priceLabel;
    private final ShapeRenderer shapeRenderer;
    private Image towerImg;
    private final ArrayList<TowerData> towerDatas;
    private final ImageButton left;
    private final ImageButton right;
    private final TextButton payButton;
    private final TextButton sellButton;
    private final Label padLabel;


    public TowerUI(Rectangle rect, Skin style, ArrayList<TowerData> towerDatas, EntityGroup towerGroup, ShapeRenderer shapeRenderer, Player player, AssetManager manager) {
        super(style);
        //index for the position in the towerdatas
        index = 0;
        //the level of the tower
        level = 0;
        padLabel = new Label("", style); //label for padding because commands like padleft squished the image
        this.rect = rect;

        this.shapeRenderer = shapeRenderer;
        this.towerDatas = towerDatas;
        this.group = towerGroup;
        towerImg = new Image(towerDatas.get(index).upgrades.get(level));

        priceLabel = new Label(towerDatas.get(index).cost + "$", style);
        //puts the table in the middle of the rec
        setSize(200, 150);
        setPosition(rect.x - getWidth() / 2 + rect.width / 2, rect.y - getHeight() / 2 + rect.height / 2);

        Texture backward = manager.get("buttons/backward.png");
        Texture forward = manager.get("buttons/forward.png");
        right = new ImageButton(new TextureRegionDrawable(forward));
        left = new ImageButton(new TextureRegionDrawable(backward));
        left.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //if left is pressed it moves index back one
                if (index > 0) {
                    index -= 1;
                } else { //accounts for going negative by setting index to last in that case
                    index = towerDatas.size() - 1;
                }
                //updates the tower ui
                priceLabel.setText(towerDatas.get(index).cost + "$");
                towerImg.setDrawable(new TextureRegionDrawable(towerDatas.get(index).upgrades.get(level)));
                super.clicked(event, x, y);
            }
        });
        right.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //if index goes over the size it is set back to 0
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

                    if (player.hasEnough(towerDatas.get(index).cost)) {//checks if the player has enough money
                        group.getSpawner().spawn((int) rect.x, (int) rect.y, towerDatas.indexOf(towerDatas.get(index)));
                        int size = group.getSpawner().getGroup().getChildren().size;
                        tower = (Tower) group.getSpawner().getGroup().getChildren().get(size - 1);
                        towerImg.setDrawable(new TextureRegionDrawable(tower.data.upgrades.get(level + 1)));
                        player.removeMoney(towerDatas.get(index).cost);
                        priceLabel.setText(tower.data.cost * (2 + level) + "$");
                        createMenu();


                    }
                } else {
                    //the amount that is needed to pay is increased by the initial amount every level
                    if (player.hasEnough(tower.data.cost * (2 + level))) {
                        player.removeMoney(tower.data.cost * (2 + level));
                        level += 1;
                        tower.setLevel(level);
                        priceLabel.setText(Integer.toString(tower.data.cost * (2 + level)));

                    }
                    if (tower.data.upgrades.size() > level + 1) {

                        towerImg.setDrawable(new TextureRegionDrawable(tower.data.upgrades.get(level + 1)));

                    } else {


                        payButton.remove();
                        priceLabel.remove();
                        towerImg.remove();
                        towerImg = null;
                        padTop(getHeight() / 2);

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

    //creates menu based on if there is a tower there currently
    private void createMenu() {
        //get rid of all actors in table
        clear();
        //creates the table based on the needs ate the moment
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


        } else if (tower.data.upgrades.size() > 1 && level < tower.data.upgrades.size()) {
            add(towerImg);
            add(padLabel);
            row();
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


        batch.end(); //need to end batch before using shaperenderer
        //necessary for transparent circle
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(0, 0, 0, 0.4f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //draws circle from center of tower
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
