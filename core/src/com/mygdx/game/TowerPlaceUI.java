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

    public TowerPlaceUI(Rectangle rectangle, Skin style) {
        stage = new Stage();


        //Circle c=new Circle(rectangle.getCenter(new Vector2()),15);
        Image uibkg = new Image(new Texture("skullButtonDown.png"));
        uibkg.setPosition(rectangle.x, rectangle.y);

        stage.addActor(uibkg);




    }

    public void draw() {
        stage.draw();

    }

}
