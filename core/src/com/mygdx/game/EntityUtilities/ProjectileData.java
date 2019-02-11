package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Entities.DmgProjectile;
import com.mygdx.game.Entities.MoneyProjectile;
import com.mygdx.game.Utilities;

//container for all projectile stats and animations
public class ProjectileData {
    public final float range;
    public final float damage;
    public final float speed;
    public final float decay;
    public final Class type;
    public final boolean homing;
    public Animation<Texture> animations;

    //gets all attributes and adds them to variables
    public ProjectileData(JsonValue attributes, AssetManager manager) {
        //creates a primitive array of the frames and putting it in the Animations
        FileHandle[] fileList = Utilities.listFiles(new FileHandle(attributes.getString("aniPath")));
        Texture[] animationFrames = new Texture[fileList.length];
        for (int i = 0; i < fileList.length; i++) {
            animationFrames[i] = manager.get(fileList[i].path());
        }
        animations = new Animation<>(0.1f, animationFrames);
        //the area of effect
        //if it is one, it is a single target thing
        range = attributes.getFloat("range");
        //amount it inflicts
        damage = attributes.getInt("damage");
        //
        speed = attributes.getInt("speed");
        //if it is a damage projectile it determines how long it lasts after hitting the initial enemy
        decay=attributes.getFloat("decay");
        //determines if it follows the enemy or not
        homing = attributes.getBoolean("homing");
        //determines the type of the projectile which will determine the meaning of damage
        switch(attributes.getString("type")){
            case "money": type= MoneyProjectile.class;
            break;
            case "damage": type= DmgProjectile.class;
            break;
            default: type=null;
        }
    }
}
