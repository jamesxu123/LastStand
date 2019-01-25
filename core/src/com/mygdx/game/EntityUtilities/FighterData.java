package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;

import java.io.File;

public class FighterData extends EntityData {
    //entity will also have other attributes such as name, icon, and description
    private FighterAnis animations;
    private Stats stats;

    //stats path should be file- will replace later
    public FighterData(String statsPath, File spritesPath, AssetManager manager) {

        animations = new FighterAnis(spritesPath, manager);
        stats = new FighterStats(statsPath);

    }

    public FighterAnis getAnimations() {
        return animations;
    }

    public Stats getStats() {
        return stats;
    }
}
