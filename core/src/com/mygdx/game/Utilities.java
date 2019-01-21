package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

//the current size doesnt affect the bounds; the screen coords are relative to what it starts with
public class Utilities {
    public static Random rand = new Random();
    public static boolean inScreen(Actor actor) {
        if ((0 <= actor.getX()) || (actor.getX() <= screenW)) {
            return (0 <= actor.getY()) || (actor.getY() <= screenH);
        }
        return false;
    }

    public static int convertMouseY(int y) {
        return screenH - y;
    }
}
