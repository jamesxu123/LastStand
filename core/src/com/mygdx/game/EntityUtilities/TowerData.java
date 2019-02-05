package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.Utilities;

import java.io.File;

public class TowerData extends EntityData {
    //will be replaced by a simple animation
    public final Animation<Texture> animations;
    public final ProjectileData projectileData;
    public final Class towerType;
    public final int radius = 100;
    //stats will soon be replaced by stat class just for tower
    public float coolDown;

    public TowerData(String statsPath, File spritesPath, AssetManager manager, Class towerType, float damage, float coolDown) {

        File[] fileList = Utilities.getListFiles(spritesPath);
        Texture[] animationFrames = new Texture[fileList.length];
        for (int i = 0; i < fileList.length; i++) {
            animationFrames[i] = manager.get(fileList[i].getPath());
        }
        animations = new Animation<Texture>(1, animationFrames);
        this.towerType = towerType;
        //data needs to be obtained from text file things
        this.coolDown = coolDown;
        projectileData = new ProjectileData(new File("sprites/PROJECTILE/WIZARD"), "statspath", manager, damage);



    }
}
