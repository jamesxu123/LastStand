package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityUtilities.TowerData;
import com.mygdx.game.Utilities;

import java.util.ArrayList;

public class Tower extends Actor {
    public final TowerData data;
    private float aniTime = 0;
    private float lastFired = 0;
    private ArrayList<Fighter> inRadius;


    public Tower(int x, int y, TowerData data) {
        super();
        setPosition(x, y);
        this.data = data;
        inRadius = new ArrayList<>();
    }

    @Override
    public void act(float delta) {
        aniTime += delta;


        super.act(delta);
    }

    public Fighter getClosest() {
        double minDist = Double.MAX_VALUE;
        Fighter closestFighter = inRadius.get(0);
        for (Fighter fighter : inRadius) {
            if (Utilities.getDistance(this, fighter) < minDist) {
                closestFighter = fighter;
            }
        }

        return closestFighter;
    }

    public ArrayList<Fighter> getInRadius() {

        return inRadius;
    }

    public void setInRadius(ArrayList<Fighter> inRadius) {
        this.inRadius = inRadius;
    }

    public Circle getRadius() {
        //need to get icon and offset the circle center
        return new Circle(getX() + 20, getY() + 32, data.radius);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(data.animations.getKeyFrame(aniTime, true), getX(), getY());

    }

    public float getLastFired() {
        return lastFired;
    }

    public void setLastFired(float lastFired) {
        this.lastFired = lastFired;
    }

    public void attack(ArrayList<Fighter> fighters) {
        System.out.println(fighters);
    }
}
