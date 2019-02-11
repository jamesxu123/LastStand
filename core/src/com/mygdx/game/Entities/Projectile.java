package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityUtilities.ProjectileData;
import com.mygdx.game.Utilities;

import java.awt.*;

public class Projectile extends Actor {
    private final Point start;
    private final Point end; //Start and end points
    final Sprite sprite;
    private final Tower tower; //Tower that fired projectile
    private float aniTime = 0; //Time since creation
    public final Circle range = new Circle(); //Damage radius
    final ProjectileData data;
    private Fighter target;
    private boolean done = false; //Whether or not mission has been completed


    Projectile(ProjectileData data, Point start, Fighter target, Tower t) {

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

    Projectile(ProjectileData data, Point start, Point end, Tower t) {
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

    Tower getTower() {
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
        if (!Utilities.inScreen(this) || Utilities.getDistance(start, new Point((int) getX(), (int) getY())) > this.tower.getRadius().radius || (done && data.decay == 0)) {
            /*
            Delete if:
                - Off screen
                - Exceeded range
                - Hit target already
             */
            if (getClass() != MoneyProjectile.class) {
                //Money follows different rules
                this.remove();
                return;
            }

        }
        range.setPosition(getX(), getY());
        sprite.setPosition(getX(), getY());
        if (data.homing && target != null) {
            //Dynamically update angles for homing projectiles, so it always follows the target, but only if target still exists
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
