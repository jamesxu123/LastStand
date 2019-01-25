package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.Directions;
import com.mygdx.game.States;
import com.mygdx.game.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FighterAnis {
    private HashMap<States, HashMap<Directions, Animation<Texture>>> container;

    public FighterAnis(File spritesFile, AssetManager manager) {
        container = new HashMap<>();

        //files are given by taking the folders it is in as keys
        File[] stateFiles = Utilities.getListFiles(spritesFile);
        for (File f : stateFiles) {
            States state = States.valueOf(f.getName());
            File[] dirFiles = Utilities.getListFiles(f);
            for (File d : dirFiles) {
                Directions direction = Directions.valueOf(d.getName());
                ArrayList<String> picList = new ArrayList<>();
                for (File c : Utilities.getListFiles(d)) {
                    picList.add(c.getPath());
                }
                Collections.sort(picList);
                Texture[] frames = new Texture[picList.size()];
                for (int i = 0; i < picList.size(); i++) {
                    frames[i] = manager.get(picList.get(i));

                }
                put(state, direction, new Animation<>(0.04f, frames));
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
