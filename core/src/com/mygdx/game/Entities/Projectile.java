package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Abstractions.EntityMap;

import java.awt.*;

public class Projectile extends Actor {
    public final float damage;
    public final float heading;
    private float speed;
    public final Point dest;
    public final Texture sprite;
    public final float width, height;
    public final float damageRadius;
    public Point pos;
    private final EntityMap entityMap;

    public Projectile(float damage, float damageRadius, float heading, float speed, Point dest, Texture sprite, EntityMap entityMap) {
        this.damage = damage;
        this.damageRadius = damageRadius;
        this.heading = heading;
        this.speed = speed;
        this.dest = dest;
        this.sprite = sprite;
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.entityMap = entityMap;
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
        this.entityMap.map[pos.x / 10][pos.y / 10].remove(this);
        pos.x += speed * Math.cos(heading);
        pos.y += speed * Math.sin(heading);
        this.entityMap.map[pos.x / 10][pos.y / 10].add(this);
        super.act(delta);
    }

    public void damage(Fighter fighter) {

    }
}
