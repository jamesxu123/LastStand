package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityUtilities.TowerData;

import java.util.ArrayList;

public class Tower extends Actor {
    private TowerData data;
    private float aniTime = 0;

    public Tower(int x, int y, TowerData data) {
        super();
        setPosition(x, y);
        this.data = data;
    }

    @Override
    public void act(float delta) {
        aniTime += delta;


        super.act(delta);
    }

    public Circle getRadius() {
        //need to get icon and offset the circle center
        return new Circle(getX() + 20, getY() + 32, data.getStats().getRadius());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(data.getAnimations().getKeyFrame(aniTime, true), getX(), getY());

    }

    public void attack(ArrayList<Fighter> fighters) {
        System.out.println(fighters);
    }
}
