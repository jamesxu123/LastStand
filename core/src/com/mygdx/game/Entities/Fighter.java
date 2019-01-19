package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Directions;
import com.mygdx.game.States;

import java.util.HashMap;


public class Fighter extends Actor {


    private HashMap<States, HashMap<Directions, Animation<Texture>>> sprites;

    private float aniTime = 0;
    private States state;
    private Directions direction;

    private int health;


    public Fighter(HashMap animations, int x, int y) {
        setPosition(x, y);
        sprites = animations;



        this.state = States.WALK;
        this.direction = Directions.RIGHT;


    }

    public void setDirection(Directions d) {
        direction = d;

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
        switch (direction) {
            case RIGHT:
                moveBy(1, 0);
                break;
            case LEFT:
                moveBy(-1, 0);
                break;
            case UP:
                moveBy(0, 1);
                break;

            case DOWN:
                moveBy(0, -1);
                break;

        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(sprites.get(state).get(direction).getKeyFrame(aniTime, true), getX(), getY());
    }


}



