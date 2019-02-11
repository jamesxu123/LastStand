package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.JsonValue;

public class MapData {
    public final TiledMap map;
    public final Texture icon;
    public final String name;

    public MapData(JsonValue attributes, AssetManager manager) {
        map = manager.get(attributes.getString("map"));
        icon = manager.get(attributes.getString("icon"));
        name = attributes.getString("name");


    }

}
