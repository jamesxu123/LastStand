package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;


public class Fighter extends Actor {


    private HashMap<States, HashMap<Directions, Animation<Texture>>> sprites = new HashMap<States, HashMap<Directions, Animation<Texture>>>();
    private float aniTime = 0;
    private States state;
    private Directions direction;

    private int health;

    public void damage(int amount) {
        if (health - amount >= 0) {
            health -= amount;
        } else {
            this.remove();
        }
    }


    public Fighter() {
        state = States.WALK;
        direction = Directions.RIGHT;

    }

    @Override
    public void act(float delta) {
        aniTime += delta;
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprites.get(state).get(direction).getKeyFrame(aniTime, true), getX(), getY());
    }

    public enum Directions {
        LEFT, RIGHT, UP, DOWN
    }

    public enum States {
        WALK, ATTACK, DEATH
    }


}
