package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.JsonValue;

//container for all fighter stats and animations
public class FighterData {

    public final FighterAnis animations;
    public final float speed;
    public final float health;
    public final String name;
    public final int size;
    public final int worth;


    public FighterData(JsonValue attributes, AssetManager manager) {
        //gets all values for the fighter from the JsonValue and loads them
        animations = new FighterAnis(attributes.getString("aniPath"), attributes.getFloat("aniSpeed"), manager);
        //size for larger and smaller enemies
        size = attributes.getInt("size");

        name = attributes.getString("name");

        speed = attributes.getFloat("speed");

        health = attributes.getInt("health");
        //how much the player receives when they kill the enemy
        worth = attributes.getInt("worth");

    }

}
