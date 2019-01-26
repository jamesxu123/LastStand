package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityUtilities.TowerData;

public class Tower extends Actor {
    private TowerData data;
    private float aniTime = 0;

    public Tower(int x, int y, TowerData data) {
        super();
        setPosition(x, y);
        this.data = data;
    }

    @Override
    public void act(float delta) {
        aniTime += delta;


        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(data.getAnimations().getKeyFrame(aniTime, true), getX(), getY());

    }
}
