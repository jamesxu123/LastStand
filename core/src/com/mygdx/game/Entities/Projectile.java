package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityUtilities.ProjectileData;
import com.mygdx.game.Utilities;

import java.awt.*;

public class Projectile extends Actor {
    public final Point start, end;
    public final Sprite sprite;
    private Tower tower;
    private float aniTime = 0;
    public final Circle range = new Circle();
    public final ProjectileData data;
    private Fighter target;
    private boolean done = false;


    public Projectile(ProjectileData data, Point start, Fighter target, Tower t) {

        this.start = start;
        this.end = new Point((int)target.getX(), (int)target.getY());
        this.target = target;
        this.sprite = new Sprite(data.animations.getKeyFrame(0));
        this.range.radius = data.range;
        this.data = data;
        this.tower = t;
        setRotation((float) Math.atan2(end.y - start.y, end.x - start.x));
        setPosition(start.x, start.y);
    }

    public Projectile(ProjectileData data, Point start, Point end, Tower t) {
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
        if (!Utilities.inScreen(this) || Utilities.getDistance(start, new Point((int) getX(), (int) getY())) > this.tower.getRadius().radius || (target != null && !target.isAlive())) {
            this.remove();
            return;
        }
        range.setPosition(getX(), getY());
        sprite.setPosition(getX(), getY());
        if (data.homing && target != null) {
            setRotation((float) Math.atan2(target.getY() - getY(), target.getX() - getX()));
        }

        sprite.setRotation((float) Math.toDegrees(getRotation()));
        super.act(delta);
    }

    public float getAniTime() {
        return aniTime;
    }

    public void setAniTime(float aniTime) {
        this.aniTime = aniTime;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
