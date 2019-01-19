package com.mygdx.game.Abstractions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.Directions;
import com.mygdx.game.States;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Spawner {
    private int waveIndex;
    private ArrayList<Iterator<Integer>> waves;
    private ArrayList<String> spritesPath;
    private int startX;
    private int startY;
    private Group group;
    private Class<Actor> actorType;
    private ArrayList<HashMap<States, HashMap<Directions, Animation<Texture>>>> spriteAnimations;

    public Spawner(String wavePath, ArrayList<String> spritesPath, int startX, int startY, Group group, Class<Actor> actorType) {
        this.spritesPath = spritesPath;
        this.startX = startX;
        this.startY = startY;
        this.group = group;
        waves = getWaves(wavePath);
        spriteAnimations = new ArrayList<>();
        for (String p : spritesPath) {
            spriteAnimations.add(getAnimations(p));
        }
        this.actorType = actorType;
    }

    public HashMap<States, HashMap<Directions, Animation<Texture>>> getAnimations(String path) {
        HashMap<States, HashMap<Directions, Animation<Texture>>> sprites = new HashMap<>();
        File[] stateFiles = new File(path).listFiles();
        for (File f : stateFiles) {
            States state = States.valueOf(f.getName());
            if (f.getName().equals(".DS_Store")) continue;
            File[] dirFiles = f.listFiles();

            sprites.put(state, new HashMap<>());
            for (File d : dirFiles) {

                if (d.getName().equals(".DS_Store")) continue;
                Directions direction = Directions.valueOf(d.getName());
                ArrayList<String> picList = new ArrayList<>();
                for (File p : d.listFiles()) {
                    if (p.getName().equals(".DS_Store")) continue;
                    picList.add(p.getPath());
                }
                Collections.sort(picList);
                Texture[] frames = new Texture[picList.size()];
                for (int i = 0; i < picList.size(); i++) {
                    frames[i] = new Texture(picList.get(i));
                }
                sprites.get(state).put(direction, new Animation<>(0.04f, frames));
            }
        }
        return sprites;
    }

    public ArrayList<Iterator<Integer>> getWaves(String wavePath) {
        ArrayList<Iterator<Integer>> x = new ArrayList<>();

        return x;

    }

    public void spawn(float delta) {

        try {
            group.addActor(actorType
                    .getDeclaredConstructor(String.class, int.class, int.class)
                    .newInstance(spritesPath.get(0), startX, startY));
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

}
