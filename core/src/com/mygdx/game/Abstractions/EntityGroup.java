package com.mygdx.game.Abstractions;

import com.badlogic.gdx.scenes.scene2d.Group;

public class EntityGroup extends Group {
    private Spawner spawner;

    public EntityGroup(Spawner spawner) {
        this.spawner = spawner;
        this.spawner.setGroup(this);



    }

    @Override
    public void act(float delta) {
        spawner.run(delta);
        super.act(delta);
    }

    public Spawner getSpawner() {
        return spawner;
    }
}
