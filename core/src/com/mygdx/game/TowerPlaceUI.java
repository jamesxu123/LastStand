package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TowerPlaceUI {
    private Table table;
    private Stage stage;

    private Image uiBkg;

    public TowerPlaceUI(Rectangle rectangle, Skin style) {
        stage = new Stage();


        //Circle c=new Circle(rectangle.getCenter(new Vector2()),15);
        uiBkg = new Image(new Texture("skullButtonDown.png"));
        uiBkg.setPosition(rectangle.x, rectangle.y);

        stage.addActor(uiBkg);
    }

    public void draw() {
        stage.draw();
    }

    public void removeUI() {
        stage.dispose();
    }

}
