package com.mygdx.game.Entities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.EntityUtilities.ProjectileData;
import com.mygdx.game.Player;

import java.awt.*;

//projectile that makes money so the damage stat is transformed into a worth stat
public class MoneyProjectile extends Projectile {
    private final Player player;

    public MoneyProjectile(ProjectileData data, Point start, Point end, Tower t, Player player) {
        super(data, start, end, t);
        this.player = player;
        //adds coin to you when you hover over it
        addListener(new ClickListener() {
            //When user hovers over coin, add money to Player
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                remove();
                player.addMoney((int) data.damage * (getTower().getLevel() + 1));
                super.enter(event, x, y, pointer, fromActor);
            }


        });
    }

    @Override
    public void act(float delta) {
        if (getAniTime() < data.decay) { //Move the coin if it's still supposed to be alive
            move();
        }
        //allows coin to be touched
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight()); //Set bounds for hover action
        super.act(delta);
    }

}
