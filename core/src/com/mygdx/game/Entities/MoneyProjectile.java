package com.mygdx.game.Entities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.EntityUtilities.ProjectileData;
import com.mygdx.game.Player;

import java.awt.*;

public class MoneyProjectile extends Projectile{
    public final Player player;

    public MoneyProjectile(ProjectileData data, Point start, Point end, Tower t, Player player){
        super(data,start,end,t);
        this.player=player;

        addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                remove();
                player.addMoney((int)data.damage);
                super.enter(event, x, y, pointer, fromActor);
            }


        });
    }
    @Override
    public void act(float delta){
        if(getAniTime()<data.decay){
            setPosition((float) (getX() + data.speed * Math.cos(getRotation())), (float) (getY() + data.speed * Math.sin(getRotation())));
        }
        setBounds(getX(),getY(),sprite.getWidth(),sprite.getHeight());
        super.act(delta);
    }

}
