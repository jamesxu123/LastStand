package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityUtilities.ProjectileData;
import com.mygdx.game.Utilities;

import java.awt.*;

public class Projectile extends Actor {
    public final Point start, end; //Start and end points
    public final Sprite sprite;
    private Tower tower; //Tower that fired projectile
    private float aniTime = 0; //Time since creation
    public final Circle range = new Circle(); //Damage radius
    public final ProjectileData data;
    private Fighter target;
    private boolean done = false; //Whether or not mission has been completed


    public Projectile(ProjectileData data, Point start, Fighter target, Tower t) {

        this.start = start;
        this.end = Utilities.getPoint(target);
        this.target = target;
        this.sprite = new Sprite(data.animations.getKeyFrame(0)); //Only one sprite is used
        this.range.radius = data.range;
        this.data = data;
        this.tower = t;
        setRotation((float) Math.atan2(end.y - start.y, end.x - start.x));
        setPosition(start.x, start.y);
    }

    public Projectile(ProjectileData data, Point start, Point end, Tower t) {
        //Alternative projectile used by MoneyTower as there is no target fighter
        this.start = start;
        this.end = end;
        this.sprite = new Sprite(data.animations.getKeyFrame(0));
        this.range.radius = data.range;
        this.data = data;
        this.tower = t;
        setRotation((float) Math.atan2(end.y - start.y, end.x - start.x));
        setPosition(start.x, start.y);
    }

    public Tower getTower() {
        return tower;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setTexture(data.animations.getKeyFrame(aniTime, true));
        sprite.draw(batch);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        aniTime += delta;
        if (!Utilities.inScreen(this) || Utilities.getDistance(start, Utilities.getPoint(this)) > this.tower.getRadius().radius || done) {
            //Delete the projectile if it's out of the screen, exceeded its range, or if the target is killed
            this.remove();
            return;
        }
        range.setPosition(getX(), getY()); //Update range Circle position
        sprite.setPosition(getX(), getY()); //Update sprite position
        if (data.homing && target != null) { //If homing projectile, dynamically update angle to home in
            setRotation((float) Math.atan2(target.getY() - getY(), target.getX() - getX()));
        }

        sprite.setRotation((float) Math.toDegrees(getRotation())); //Update sprite rotation
        super.act(delta);
    }

    float getAniTime() {
        return aniTime;
    }

    void setAniTime(float aniTime) {
        this.aniTime = aniTime;
    }

    boolean isDone() {
        return done;
    }

    void setDone(boolean done) {
        this.done = done;
    }
}
