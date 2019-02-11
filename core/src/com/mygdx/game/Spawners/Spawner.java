package com.mygdx.game.Spawners;

import com.badlogic.gdx.scenes.scene2d.Group;

//abstract class that allows a group to spawn a member to its super group
public abstract class Spawner {
    //the super group- it has a two way relationship
    private Group group;
    private static boolean spawning=false;
    private float totalTime;

    public void spawn(int x, int y, int index) {
    }

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

    public int getWave() {
        return -1;
    }

    float getTotalTime() {
        return totalTime;
    }

    void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    boolean getSpawning() {
        return spawning;
    }

    public void setSpawning(boolean spawning) {
        Spawner.spawning = spawning;
    }

}
