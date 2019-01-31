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
        return new Circle(getX(), getY(), data.getStats().getRadius());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(data.getAnimations().getKeyFrame(aniTime, true), getX(), getY());

    }

    public void attack(ArrayList<Fighter> fighters) {
        System.out.println(fighters);
    }
}
