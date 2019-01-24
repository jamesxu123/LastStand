package com.mygdx.game.Entities;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tower extends Actor {
    private Projectile projectile;

    public Tower(int x, int y, Projectile p) {
        super();
        setPosition(x, y);
        projectile = p;
    }

    @Override
    public void act(float delta) {

        super.act(delta);
    }
}
