package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.JsonValue;

public class FighterData extends EntityData {
    //entity will also have other attributes such as name, icon, and description
    public final FighterAnis animations;
    public final float speed;
    public final float health;
    public final String name;

    //stats path should be file- will replace later
    public FighterData(JsonValue attributes, AssetManager manager) {

        animations = new FighterAnis(attributes.getString("aniPath"), manager);

        name = attributes.getString("name");

        speed = attributes.getFloat("speed");
        health = attributes.getInt("health");
    }

}
