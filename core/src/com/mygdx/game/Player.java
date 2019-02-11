package com.mygdx.game;

//simple class to hold players current in-game stats
public class Player {
    private int money;
    private int lives;
    private int level;
    private static boolean godFinger;


    public Player() {
        //allows player to kill enemy with finger
        godFinger = false;
        money = 350;
        lives = 1;

    }

    public static void setGodfinger(boolean godFng) {
        godFinger = godFng;
    }

    public void addMoney(int add) {
        money += add;

    }

    public static boolean isGodFinger() {
        return godFinger;
    }

    public void setGodFinger(boolean godFinger) {
        Player.godFinger = godFinger;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    //loses life
    public void loseLife() {
        lives -= 1;
    }

    //checks if player is still alive
    public boolean isAlive() {
        return lives > 0;

    }

    public int getMoney() {
        return money;
    }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
    }
}
