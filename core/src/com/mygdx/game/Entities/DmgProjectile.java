package com.mygdx.game.Entities;

import com.mygdx.game.EntityUtilities.ProjectileData;

import java.awt.*;

public class DmgProjectile extends Projectile{
    public DmgProjectile(ProjectileData data, Point start, Fighter target, Tower t){
        super(data,start, target,t);
    }
    public void damage(Fighter fighter) {

        fighter.damage((int) (data.damage * (getTower().getLevel() + 1))); //Damage scales linearly with tower level
        if (!isDone()) {
            setAniTime(0);
        }

        setDone(true);


    }
    @Override
    public void act(float delta){
        if (!isDone()) {
            setPosition((float) (getX() + data.speed * Math.cos(getRotation())), (float) (getY() + data.speed * Math.sin(getRotation())));
        } else {
            if (getAniTime() > data.decay) {
                remove();
            }
        }
        super.act(delta);
    }
}
