package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;

public class TowerData extends EntityData {
    //will be replaced by a simple animation
    private Texture animations;
    //stats will soon be replaced by stat class just for tower
    private Stats stats;

    public TowerData(String statsPath, File spritesPath, AssetManager manager) {
        animations = manager.get(spritesPath.getPath());

    }
}
