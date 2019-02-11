package com.mygdx.game.Abstractions;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.Spawners.Spawner;

public class EntityGroup extends Group {
    private final Spawner spawner;

    public EntityGroup(Spawner spawner) {
        this.spawner = spawner;
        this.spawner.setGroup(this);



    }

    @Override
    public void childrenChanged() {

    }

    @Override
    public void act(float delta) {
        //spawner is told to run but it will only run if spawning is true
        spawner.run(delta);
        //passes it on to actors
        super.act(delta);
    }

    public Spawner getSpawner() {
        return spawner;
    }
}
