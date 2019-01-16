package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.*;

public class Projectile extends Actor {
    public final float damage;
    public final float heading;
    private float speed;
    public final Point dest;
    public final Texture sprite;
    public final float width, height;
    public Point pos;

    public Projectile(float damage, float heading, float speed, Point dest, Texture sprite) {
        this.damage = damage;
        this.heading = heading;
        this.speed = speed;
        this.dest = dest;
        this.sprite = sprite;
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = (float) pos.x;
        float y = (float) pos.y;
        batch.draw(sprite, x, y);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        pos.x += speed * Math.cos(heading);
        pos.y -= speed * Math.sin(heading);
        super.act(delta);
    }
}
