package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

//Utility class for functions that are used throughout the files
//the current size doesnt affect the bounds; the screen coords are relative to what it starts with
public class Utilities {
    public static Random rand = new Random();

    public static boolean inScreen(float x, float y) {
        if ((0 <= x) || (x < screenW)) {
            return (0 <= y) || (y < screenH);
        }
        return false;
    }

    public static int convertMouseY(int y) {
        return screenH - y;
    }

    public static FileHandle[] listFiles(FileHandle f) {
        FileHandle[] fileHandles = f.list(n -> !new FileHandle(n).extension().equals("DS_Store"));
        Arrays.sort(fileHandles, Comparator.comparing(FileHandle::name));
        return fileHandles;
    }

    public static double getDistance(Actor a, Actor b) {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

    public static Point getPoint(Actor a) {
        return new Point((int) a.getX(), (int) a.getY());
    }

    public static boolean inScreen(Actor a) {

        return inScreen(a.getX(), a.getY());

    }

}
