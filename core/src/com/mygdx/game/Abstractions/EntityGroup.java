package com.mygdx.game.Abstractions;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.Spawners.Spawner;

//the only functionality from Group that is extended is adding a spawner
//we could have made separate group classes for each entity and merged it
//with the spawners and eliminating them but oh well
public class EntityGroup extends Group {
    private final Spawner spawner;

    public EntityGroup(Spawner spawner) {
        this.spawner = spawner;
        //allows for a two way relationship between spawner and group
        this.spawner.setGroup(this);
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
