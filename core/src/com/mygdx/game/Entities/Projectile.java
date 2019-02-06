package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityUtilities.ProjectileData;
import com.mygdx.game.Player;
import com.mygdx.game.Utilities;

import java.awt.*;

public class Projectile extends Actor {
    public final Point start, end;
    public final Sprite sprite;
    private Tower tower;
    private float aniTime = 0;
    public final Circle range = new Circle();
    public final ProjectileData data;


    public Projectile(ProjectileData data, Point start, Point end, Tower t) {

        this.start = start;
        this.end = end;
        this.sprite = new Sprite(data.animations.getKeyFrame(0));
        this.range.radius = data.range;
        this.data = data;
        setRotation((float) Math.atan2(end.y - start.y, end.x - start.x));
        setPosition(start.x, start.y);
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
        if (!Utilities.inScreen(this)) {
            this.remove();
            return;
        }
        range.setPosition(getX(), getY());
        sprite.setPosition(getX(), getY());
        sprite.setRotation((float) Math.toDegrees(getRotation()));
        super.act(delta);
    }

    public float getAniTime() {
        return aniTime;
    }
}
