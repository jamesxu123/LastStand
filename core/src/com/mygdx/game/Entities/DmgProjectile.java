package com.mygdx.game.Entities;

import com.mygdx.game.EntityUtilities.ProjectileData;

import java.awt.*;

public class DmgProjectile extends Projectile{
    public DmgProjectile(ProjectileData data, Point start, Fighter target, Tower t){
        super(data,start, target,t);
    }
    public void damage(Fighter fighter) {
        fighter.damage((int) data.damage);
        this.remove();
    }
    @Override
    public void act(float delta){
        setPosition((float) (getX() + data.speed * Math.cos(getRotation())), (float) (getY() + data.speed * Math.sin(getRotation())));
        super.act(delta);
    }
}
