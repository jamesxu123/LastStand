package com.mygdx.game.Entities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.EntityUtilities.ProjectileData;
import com.mygdx.game.Player;

import java.awt.*;

public class MoneyProjectile extends Projectile {
    private final Player player;

    public MoneyProjectile(ProjectileData data, Point start, Point end, Tower t, Player player) {
        super(data, start, end, t);
        this.player = player;

        addListener(new ClickListener() {
            //When user hovers over coin, add money to Player
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                remove();
                player.addMoney((int) data.damage);
                super.enter(event, x, y, pointer, fromActor);
            }


        });
    }

    @Override
    public void act(float delta) {
        if (getAniTime() < data.decay) { //Move the coin if it's still supposed to be alive
            setPosition((float) (getX() + data.speed * Math.cos(getRotation())), (float) (getY() + data.speed * Math.sin(getRotation())));
        }
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight()); //Set bounds for hover action
        super.act(delta);
    }

}
