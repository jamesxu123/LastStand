package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.Directions;
import com.mygdx.game.States;
import com.mygdx.game.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FighterAnis {
    private HashMap<States, HashMap<Directions, Animation<Texture>>> container;

    public FighterAnis(String spritesPath, float aniSpeed, AssetManager manager) {
        container = new HashMap<>();
        FileHandle[] stateFiles = Utilities.listFiles(new FileHandle(spritesPath));
        for (FileHandle f : stateFiles) {
            States state = States.valueOf(f.name());
            FileHandle[] dirFiles = Utilities.listFiles(f);
            for (FileHandle d : dirFiles) {
                Directions direction = Directions.valueOf(d.name());
                ArrayList<String> picList = new ArrayList<>();
                for (FileHandle c : Utilities.listFiles(d)) {
                    picList.add(c.path());
                }
                Collections.sort(picList);
                Texture[] frames = new Texture[picList.size()];
                for (int i = 0; i < picList.size(); i++) {
                    frames[i] = manager.get(picList.get(i));

                }
                put(state, direction, new Animation<>(aniSpeed, frames));
            }
        }
    }

    public Animation<Texture> get(States state, Directions direction) {
        return container.get(state).get(direction);

    }

    private void put(States state, Directions direction, Animation<Texture> sprites) {
        if (!container.containsKey(state)) {
            container.put(state, new HashMap<>());
        }
        container.get(state).put(direction, sprites);
    }
}
