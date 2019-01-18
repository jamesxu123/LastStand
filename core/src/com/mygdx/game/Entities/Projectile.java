package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Abstractions.EntityMap;
import com.mygdx.game.Utilities;

import java.awt.*;

public class Projectile extends Actor {
    public final float damage;
    private float speed;
    public final Point dest;
    public final Sprite sprite;
    public final float width, height;
    public final float damageRadius;
    private final EntityMap entityMap;

    public Projectile(float damage, float damageRadius, float heading, float speed, Point dest, Texture sprite, EntityMap entityMap) {
        this.damage = damage;
        this.damageRadius = damageRadius;
        setRotation((float)Math.toRadians(heading));
        this.speed = speed;
        this.dest = dest;
        this.sprite = new Sprite(sprite);
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.entityMap = entityMap;
        setPosition(200, 200);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        batch.draw(sprite, getX(), getY());
        sprite.setRotation((float)Math.toDegrees(getRotation()));
        sprite.draw(batch);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        if (!Utilities.inScreen(this)) {
            this.remove();
            return;
        }
        sprite.setPosition(getX(), getY());
//        System.out.printf("(%f, %f)\n", getX(), getY());

//        if (!Utilities.inScreen(this)) {
//            this.remove();
//            return;
//        }
        setPosition((float)(getX() + speed * Math.cos(getRotation())), (float)(getY() + speed * Math.sin(getRotation())));
        super.act(delta);
    }

    public void damage(Fighter fighter) {

    }
}
