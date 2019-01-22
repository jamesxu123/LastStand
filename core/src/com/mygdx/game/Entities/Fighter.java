package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Abstractions.EntityMap;
import com.mygdx.game.Directions;
import com.mygdx.game.States;

import java.util.HashMap;


public class Fighter extends Actor {


    private HashMap<States, HashMap<Directions, Animation<Texture>>> sprites;

    private float aniTime = 0;
    private States state;
    private Directions direction;
    private int speed;

    private int health;

    private EntityMap entityMap;

    //Stats stats,
    public Fighter(HashMap animations, int x, int y) {
        speed = 1;
        setPosition(x, y);
        sprites = animations;



        this.state = States.WALK;


    }

    public Fighter(HashMap animations, int x, int y, EntityMap entityMap) {
        this(animations, x, y);
        this.entityMap = entityMap;
    }

    public void setDirection(Directions d) {
        direction = d;

    }

    public void setDirection(String d) {
        direction = Directions.valueOf(d);

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
        if (state == States.WALK) {
            switch (direction) {
                case RIGHT:
                    moveBy(speed, 0);
                    break;
                case LEFT:
                    moveBy(-speed, 0);
                    break;
                case UP:
                    moveBy(0, speed);
                    break;

                case DOWN:
                    moveBy(0, -speed);
                    break;

            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        try {
            batch.draw(sprites.get(state).get(direction).getKeyFrame(aniTime, true), getX(), getY());
        } catch (NullPointerException e) {
            batch.draw(new Texture("sprites/FIGHTER/KNIGHT/WALK/LEFT/LEFT0.png"), getX(), getY());
        }
    }


}



