package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Utilities {
    public static boolean inScreen(Actor actor) {
        if (!(0 <= actor.getX()) || !(actor.getX() <= Gdx.graphics.getWidth())) {
            if (!(0 <= actor.getY()) || !(actor.getY() <= Gdx.graphics.getHeight())) {
                return true;
            }
        }
        return false;
    }
}
