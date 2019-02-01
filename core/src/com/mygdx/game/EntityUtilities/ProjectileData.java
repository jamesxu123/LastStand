package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Entities.Tower;

public class ProjectileData extends EntityData {
    public final Texture sprite;
    public final float damage;
    public final float speed;
    public final Tower tower;
    public final float range;

    public ProjectileData(Texture sprite, float damage, float speed, float range, Tower tower) {
        this.sprite = sprite;
        this.damage = damage;
        this.speed = speed;
        this.tower = tower;
        this.range = range;
    }
}
