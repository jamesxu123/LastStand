package com.mygdx.game;

//simple class to hold players current in-game stats
public class Player {
    private int money;
    private int lives;
    private int level;

    public Player() {
        money = 100;
        lives = 100;

    }

    public void addMoney(int add) {
        money += add;

    }

    public void loseLife() {
        lives -= 1;
    }

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
