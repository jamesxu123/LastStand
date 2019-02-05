package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Entities.MoneyTower;
import com.mygdx.game.Entities.SingleTargetTower;
import com.mygdx.game.Entities.SplashTower;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.Utilities;

public class TowerData extends EntityData {
    public final String name;
    public final Animation<Texture> animations;
    public final ProjectileData projectileData;
    public final Class towerType;
    public final int radius;
    public final float coolDown;
    public final int cost;

    public TowerData(JsonValue attributes, JsonValue projectile, AssetManager manager) {
        name = attributes.getString("name");
        radius = attributes.getInt("radius");
        cost = attributes.getInt("cost");
        FileHandle[] fileList = Utilities.listFiles(new FileHandle(attributes.getString("aniPath")));
        Texture[] animationFrames = new Texture[fileList.length];
        for (int i = 0; i < fileList.length; i++) {
            animationFrames[i] = manager.get(fileList[i].path());
        }
        animations = new Animation<>(1, animationFrames);
        switch (attributes.getString("type")) {
            case "single":
                towerType = SingleTargetTower.class;
                break;

            case "splash":
                towerType = SplashTower.class;
                break;
            case "money":
                towerType = MoneyTower.class;
                break;
            default:
                towerType = Tower.class;
        }
        //data needs to be obtained from text file things
        coolDown = 0.5f;
        projectileData = new ProjectileData(projectile, manager);







    }
}
