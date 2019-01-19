package com.mygdx.game.Abstractions;

import com.badlogic.gdx.scenes.scene2d.Group;

public class EntityGroup extends Group {
    //the way you are envisioning entity map it would not belong here
    //public final EntityMap entityMap = new EntityMap();
    private Spawner spawner;

    public EntityGroup() {
        spawner = new Spawner();

    }

}
