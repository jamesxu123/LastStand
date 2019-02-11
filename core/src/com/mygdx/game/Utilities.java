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
public class Utilities {
    public static Random rand = new Random();

    public static boolean inScreen(float x, float y) {
        //Check if an object is inside the screen
        if ((0 <= x) || (x < screenW)) {
            return (0 <= y) || (y < screenH);
        }
        return false;
    }

    public static int convertMouseY(int y) {
        return screenH - y;
    }

    public static FileHandle[] listFiles(FileHandle f) {
        //List all files in a directory that arent .DS_Store
        //mac problem
        FileHandle[] fileHandles = f.list(n -> !new FileHandle(n).extension().equals("DS_Store"));
        Arrays.sort(fileHandles, Comparator.comparing(FileHandle::name));
        return fileHandles;
    }

    public static double getDistance(Actor a, Actor b) {
        //Get the distance between two actors
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

    public static Point getPoint(Actor a) {
        //Get the actor position as a Point
        return new Point((int) a.getX(), (int) a.getY());
    }

    public static boolean inScreen(Actor a) {
        //Overload to check an Actor directly
        return inScreen(a.getX(), a.getY());

    }

    public static double getDistance(Point a, Point b) {
        //Get the distance between two Points
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

}
