package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Entities.DmgProjectile;
import com.mygdx.game.Entities.MoneyProjectile;
import com.mygdx.game.Utilities;


public class ProjectileData extends EntityData {
    //????
    public final float range;
    public final float damage;
    public final float speed;
    public final float decay;
    public final Class type;
    public Animation<Texture> animations;

    public ProjectileData(JsonValue attributes, AssetManager manager) {
        FileHandle[] fileList = Utilities.listFiles(new FileHandle(attributes.getString("aniPath")));
        Texture[] animationFrames = new Texture[fileList.length];
        for (int i = 0; i < fileList.length; i++) {
            animationFrames[i] = manager.get(fileList[i].path());
        }
        animations = new Animation<>(0.1f, animationFrames);
        range = attributes.getFloat("range");
        damage = attributes.getInt("damage");
        speed = attributes.getInt("speed");
        decay=attributes.getFloat("decay");
        switch(attributes.getString("type")){
            case "money": type= MoneyProjectile.class;
            break;
            case "damage": type= DmgProjectile.class;
            break;
            default: type=null;
        }
    }
}
