package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Directions;
import com.mygdx.game.EntityUtilities.FighterData;
import com.mygdx.game.Player;
import com.mygdx.game.States;
/*
Generic Fighter class that loads data from a JSON file
 */

public class Fighter extends Actor {


    public final FighterData data;
    private final Player player;
    private float health;
    private float aniTime = 0;
    private States state;
    private Directions direction;


    //Stats stats
    public Fighter(FighterData data, int x, int y, Directions direction, Player player) {
        setPosition(x, y); //Starting position
        this.player = player;


        this.data = data;
        health = data.health;
        this.direction = direction;


        this.state = States.WALK;
    }


    public void setDirection(String d) {
        direction = Directions.valueOf(d); //Convert from String to Directions enum

    }


    public void damage(int amount) {
        //Damage itself
        health -= amount;
        if (!isAlive()) {
            state = States.DEATH;
            player.addMoney(data.worth);
            aniTime = 0;
        }
    }


    @Override
    public void act(float delta) {
//        System.out.println(data.name);
//        System.out.println(data.animations.get(state, direction));

        //Set size based on sprite size
        setWidth(data.animations.get().getKeyFrame(aniTime).getWidth());
        setHeight(data.animations.get().getKeyFrame(aniTime).getHeight());
        setBounds(getX(),getY(),getWidth(),getHeight());

        aniTime += delta; //Keep track of time since spawn


        if (state == States.WALK) {
            //Move the sprite in the respective directions
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
        } else if (state == States.DEATH) {
            if (aniTime > 2) { //Show dead sprite as a corpse
                remove();

            }

        }
        super.act(delta);

    }

    public float getHealth() {
        return health;
    }

    public Directions getDirections() {
        return direction;
    }

    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Draw with different sprites for animation
        boolean looping = state != States.DEATH;
        Texture frame = data.animations.get().getKeyFrame(aniTime, looping);
        batch.draw(frame, getX(), getY(), frame.getWidth() * data.size, frame.getHeight() * data.size);
        //^^^^ draw with proper size as indicated by JSON

    }
}



