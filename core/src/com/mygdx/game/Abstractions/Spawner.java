package com.mygdx.game.Abstractions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.AniContainer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Spawner {
    private Group group;
    private Class actorType;
    private ArrayList<AniContainer> animations;
    private boolean spawning;
    private float totalTime;


    public Spawner(Class actorType, ArrayList<AniContainer> animations) {
        this.actorType = actorType;
        spawning = false;
        this.animations = animations;
    }


    public void spawn(int x, int y, int index) {

        try {
            group.addActor((Actor) actorType
                    .getDeclaredConstructor(AniContainer.class, int.class, int.class)
                    .newInstance(animations.get(index), x, y));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
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
