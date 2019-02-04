package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Directions;
import com.mygdx.game.EntityUtilities.FighterData;
import com.mygdx.game.States;


public class Fighter extends Actor {


    public final FighterData data;
    private float health;
    private float aniTime = 0;
    private States state;
    private Directions direction;



    //Stats stats,
    public Fighter(FighterData data, int x, int y) {
        setPosition(x, y);

        this.data = data;
        health = data.health;




        this.state = States.WALK;


    }

    public void setDirection(Directions d) {
        direction = d;

    }

    public void setDirection(String d) {
        direction = Directions.valueOf(d);

    }


    public void damage(int amount) {
        health -= amount;
        if (!isAlive()) {
            state = States.DEATH;
            aniTime = 0;
        }
    }


    @Override
    public void act(float delta) {
        aniTime += delta;
        if (state == States.WALK) {

            super.act(delta);
            if (state == States.WALK) {
                switch (direction) {
                    case RIGHT:
                        moveBy(data.speed, 0);
                        break;
                    case LEFT:
                        moveBy(-data.speed, 0);
                        break;
                    case UP:
                        moveBy(0, data.speed);
                        break;

                    case DOWN:
                        moveBy(0, -data.speed);
                        break;

                }
            }
        } else if (state == States.DEATH) {
            if (aniTime > 2) {
                remove();
            }

        }

    }

    public Directions getDirections() {
        return direction;
    }

    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        boolean looping = state != States.DEATH;
        batch.draw(data.animations.get(state, direction).getKeyFrame(aniTime, looping), getX(), getY());
        
    }


}



