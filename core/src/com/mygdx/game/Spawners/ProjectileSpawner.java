package com.mygdx.game.Spawners;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Abstractions.EntityMap;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.Projectile;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.EntityUtilities.ProjectileData;
import com.mygdx.game.Utilities;

import java.awt.*;

public class ProjectileSpawner extends Spawner {
    private int damage;
    private float speed;

    private Texture sprite;
    private EntityMap entityMap;

    private Tower tower;

    private Projectile projectile;
    private final ProjectileData projectileData;

    private long coolDown;
    private long lastFired;

    public ProjectileSpawner(Tower tower, int damage, float speed, long coolDown, Texture sprite, EntityMap entityMap) {
        this.damage = damage;
        this.speed = speed;
        this.sprite = sprite;
        this.entityMap = entityMap;
        this.tower = tower;
        this.coolDown = coolDown;
        this.projectileData = new ProjectileData(sprite, damage, speed, 10, tower);
        setSpawning(true);
    }

    @Override
    public void run(float delta) {
        if (getSpawning()) {
            if (TimeUtils.millis() - lastFired > coolDown) {
                lastFired = TimeUtils.millis();
                Fighter fighter = entityMap.closestFighter(this.tower);
                projectile = new Projectile(projectileData, Utilities.getPoint(tower), Utilities.getPoint(fighter), entityMap);
            }
        }
        super.run(delta);
    }

    @Override
    public void spawn(int x, int y, int index) {
        if (projectile != null) {
            getGroup().addActor(projectile);
            projectile = null;
        }
    }
}
