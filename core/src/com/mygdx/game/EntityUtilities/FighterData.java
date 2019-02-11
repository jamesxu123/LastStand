package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.JsonValue;

public class FighterData {

    public final FighterAnis animations;
    public final float speed;
    public final float health;
    public final String name;
    public final int size;
    public final int worth;



    //stats path should be file- will replace later
    public FighterData(JsonValue attributes, AssetManager manager) {
        //gets 
        animations = new FighterAnis();
        size = attributes.getInt("size");

        name = attributes.getString("name");

        speed = attributes.getFloat("speed");
        health = attributes.getInt("health");
        worth = attributes.getInt("worth");

    }

}
