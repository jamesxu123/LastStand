package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.Utilities;

import java.io.File;

public class ProjectileData extends EntityData {
    //????
    public final float range;
    public final float damage;
    public final float speed;
    public Animation<Texture> animations;

    public ProjectileData(File spritesPath, String statsPath, AssetManager manager, float damage) {
        File[] fileList = Utilities.getListFiles(spritesPath);
        Texture[] animationFrames = new Texture[fileList.length];
        for (int i = 0; i < fileList.length; i++) {
            animationFrames[i] = manager.get(fileList[i].getPath());
        }
        animations = new Animation<>(1, animationFrames);
        range = 20;
        this.damage = damage;
        speed = 5;


    }
}
