package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Directions;
import com.mygdx.game.EntityUtilities.FighterData;
import com.mygdx.game.States;


public class Fighter extends Actor {


    private FighterData data;

    private float aniTime = 0;
    private States state;
    private Directions direction;
    private int speed;


    private int health;


    //Stats stats,
    public Fighter(FighterData data, int x, int y) {
        speed = 1;
        setPosition(x, y);
        this.data = data;




        this.state = States.WALK;


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

    public Directions getDirections() {
        return direction;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(data.getAnimations().get(state, direction).getKeyFrame(aniTime, true), getX(), getY());



    }


}



