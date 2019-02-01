package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;

import java.io.File;

public class FighterData extends EntityData {
    //entity will also have other attributes such as name, icon, and description
    private FighterAnis animations;
    float speed = 1;
    float health = 20;

    //stats path should be file- will replace later
    public FighterData(String statsPath, File spritesPath, AssetManager manager) {

        animations = new FighterAnis(spritesPath, manager);


    }

    public FighterAnis getAnimations() {
        return animations;
    }

    public float getSpeed() {
        return speed;
    }

    public float getHealth() {
        return health;
    }
}
