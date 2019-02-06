package com.mygdx.game.Spawners;

import com.badlogic.gdx.scenes.scene2d.Group;

//abstract class that allows a group to spawn a member to its super group
public abstract class Spawner {
    //the super group- it has a two way relationship
    private Group group;
    private static boolean spawning=false;
    private float totalTime;

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

}
