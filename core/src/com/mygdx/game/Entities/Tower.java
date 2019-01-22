package com.mygdx.game.Entities;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tower extends Actor {
    private Projectile projectile;

    public Tower(Projectile p) {
        super();
        projectile = p;
    }

    @Override
    public void act(float delta) {

        super.act(delta);
    }
}
