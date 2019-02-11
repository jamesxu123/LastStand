package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityUtilities.TowerData;
import com.mygdx.game.Utilities;

import java.util.ArrayList;

public class Tower extends Actor {
    public final TowerData data;
    //the number of projectiles per tower although not implemented
    private int numProjectiles = 1;
    private float lastFired = 0;
    private int level = 0;
    private final int offsetX;
    private final int offsetY;
    private ArrayList<Fighter> inRadius;


    public Tower(int x, int y, TowerData data) {
        super();
        setPosition(x, y);
        this.data = data;
        offsetX = data.upgrades.get(0).getWidth() / 2;
        offsetY = data.upgrades.get(0).getHeight() / 2;
        inRadius = new ArrayList<>();

    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getNumProjectiles() {
        return numProjectiles;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public Fighter getClosest() {
        double minDist = Double.MAX_VALUE;
        Fighter closestFighter = null;
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
        return new Circle(getX() + offsetX, getY() + offsetY, data.radius);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(data.upgrades.get(level), getX(), getY());

    }

    public float getLastFired() {
        return lastFired;
    }

    public void setLastFired(float lastFired) {
        this.lastFired = lastFired;
    }
}
