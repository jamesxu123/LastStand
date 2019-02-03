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

    }

    public Directions getDirections() {
        return direction;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(data.animations.get(state, direction).getKeyFrame(aniTime, true), getX(), getY());



    }


}



