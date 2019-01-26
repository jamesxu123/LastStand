package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.Utilities;

import java.io.File;

public class TowerData extends EntityData {
    //will be replaced by a simple animation
    private Animation<Texture> animations;
    //stats will soon be replaced by stat class just for tower
    private Stats stats;
    private Class towerType;

    public TowerData(String statsPath, File spritesPath, AssetManager manager, Class towerType) {
        File[] fileList = Utilities.getListFiles(spritesPath);
        Texture[] animationFrames = new Texture[fileList.length];
        for (int i = 0; i < fileList.length; i++) {
            animationFrames[i] = manager.get(fileList[i].getPath());
        }
        animations = new Animation<Texture>(1, animationFrames);
        this.towerType = towerType;


    }

    public Class getTowerType() {
        return towerType;
    }

    public Animation<Texture> getAnimations() {
        return animations;
    }
}
