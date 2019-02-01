package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Abstractions.EntityMap;
import com.mygdx.game.EntityUtilities.ProjectileData;
import com.mygdx.game.Utilities;

import java.awt.*;

public class Projectile extends Actor {
    public final float damage;
    public final Point start, end;
    public final Sprite sprite;
    public final float width, height;
    private final EntityMap entityMap;
    private float speed;
    public final Circle range = new Circle();

    public Projectile(ProjectileData projectileData, Point start, Point end, EntityMap entityMap) {
        this.damage = projectileData.damage;
        this.speed = projectileData.speed;
        this.start = start;
        this.end = end;
        this.sprite = new Sprite(projectileData.sprite);
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.entityMap = entityMap;
        this.range.radius = projectileData.range;
        setRotation((float) Math.atan2(end.y - start.y, end.x - start.x));
        setPosition(start.x, start.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        batch.draw(sprite, getX(), getY());
        sprite.setRotation((float) Math.toDegrees(getRotation()));
        sprite.draw(batch);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        if (!Utilities.inScreen(this)) {
            this.remove();
            return;
        }
        range.setPosition(getX(), getY());
        sprite.setPosition(getX(), getY());
        setPosition((float) (getX() + speed * Math.cos(getRotation())), (float) (getY() + speed * Math.sin(getRotation())));
        super.act(delta);
    }

    public void damage(Fighter fighter) {
        fighter.damage((int) damage);
        this.remove();
    }
}
