package com.mygdx.game.Spawners;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.EntityUtilities.Entity;

import java.util.ArrayList;

public abstract class Spawner {
    private Group group;
    private Class actorType;
    private ArrayList<Entity> entities;
    private boolean spawning;
    private float totalTime;


    public Spawner(Class actorType, ArrayList<Entity> entities) {
        this.actorType = actorType;
        spawning = false;
        this.entities = entities;
    }


    public abstract void spawn(int x, int y, int index);

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void run(float delta) {
        if (spawning) {
            totalTime += delta;
        }
    }

    public float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    public boolean getSpawning() {
        return spawning;
    }

    public void setSpawning(boolean spawning) {
        this.spawning = spawning;
    }

    public ArrayList<Entity> getAnimations() {
        return entities;
    }
}
