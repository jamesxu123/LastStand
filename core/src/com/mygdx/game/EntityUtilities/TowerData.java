package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Utilities;

import java.util.ArrayList;

public class TowerData {
    private final String name;
    public final ArrayList<Texture> upgrades;
    public final ProjectileData projectileData;
    public final int radius;
    public final float coolDown;
    public final int cost;

    public TowerData(JsonValue attributes, JsonValue projectile, AssetManager manager) {
        name = attributes.getString("name");
        radius = attributes.getInt("radius");
        cost = attributes.getInt("cost");

        upgrades = new ArrayList<>();
        for (FileHandle f : Utilities.listFiles(new FileHandle(attributes.getString("aniPath")))) {
            upgrades.add(manager.get(f.path()));

        }


        //data needs to be obtained from text file things
        coolDown = attributes.getFloat("coolDown");
        projectileData = new ProjectileData(projectile, manager);







    }
}
