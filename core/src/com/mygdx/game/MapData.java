package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.JsonValue;

//container for map
public class MapData {
    public final TiledMap map;
    public final Texture icon;
    public final String name;

    public MapData(JsonValue attributes, AssetManager manager) {
        //the map itself
        map = manager.get(attributes.getString("map"));
        //the picture of the map
        icon = manager.get(attributes.getString("icon"));
        //the creative name for the map
        name = attributes.getString("name");


    }

}
