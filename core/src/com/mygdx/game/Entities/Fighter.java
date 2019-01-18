package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Fighter extends Actor {


    private HashMap<States, HashMap<Directions, Animation<Texture>>> sprites;

    private float aniTime = 0;
    private States state;
    private Directions direction;

    private int health;


    public Fighter(String path, int x, int y) {
        setPosition(x, y);
        sprites = new HashMap<>();


        File[] stateFiles = new File(path).listFiles((dir, name) -> !name.equals(".DS_Store"));
        for (File f : stateFiles) {
            States state = States.valueOf(f.getName());
            //lambda to filter out .ds_store files on mac
            File[] dirFiles = f.listFiles((dir, name) -> !name.equals(".DS_Store"));
            sprites.put(state, new HashMap<>());
            for (File d : dirFiles) {

                //System.out.println(d.getName());
                if (d.getName().equals(".DS_Store")) {
                    System.out.println(true);
                    continue;
                }
                Directions direction = Directions.valueOf(d.getName());
                ArrayList<String> picList = new ArrayList<>();
                for (File p : d.listFiles((dir, name) -> !name.equals(".DS_Store"))) {
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
        this.state = States.WALK;
        this.direction = Directions.RIGHT;


    }

    public void damage(int amount) {
        if (health - amount >= 0) {
            health -= amount;
        } else {
            this.remove();
        }
    }






/*
        //due to it being a mac /.DS_Store is included whenever a listfile is taken
        ArrayList<Texture> picArrayList=new ArrayList<Texture>();
        for(File f:sortedFiles){

                picArrayList.add(new Texture(f.getPath()));
            }
        }
        */


    @Override
    public void act(float delta) {
        aniTime += delta;
        super.act(delta);
        moveBy(1, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprites.get(state).get(direction).getKeyFrame(aniTime, true), getX(), getY());
    }


    enum Directions {
        LEFT, RIGHT, UP, DOWN
    }

    enum States {
        WALK, ATTACK, DEATH
    }
}



