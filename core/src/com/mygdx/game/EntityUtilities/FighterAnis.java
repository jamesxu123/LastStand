package com.mygdx.game.EntityUtilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.Directions;
import com.mygdx.game.States;
import com.mygdx.game.Utilities;

import java.util.ArrayList;
import java.util.HashMap;

//class to hold the animations of the fighters which takes a direction and a state as keys
public class FighterAnis {
    //holds the animations
    private final HashMap<States, HashMap<Directions, Animation<Texture>>> container;

    public FighterAnis(String spritesPath, float aniSpeed, AssetManager manager) {
        container = new HashMap<>();
        //gets all states
        FileHandle[] stateFiles = Utilities.listFiles(new FileHandle(spritesPath));
        for (FileHandle f : stateFiles) {
            //gets the state of the folder
            States state = States.valueOf(f.name());
            FileHandle[] dirFiles = Utilities.listFiles(f);
            for (FileHandle d : dirFiles) {
                //gets the direction of the pics in the folder
                Directions direction = Directions.valueOf(d.name());
                ArrayList<String> picList = new ArrayList<>();
                //gets the path of the files in the folder
                for (FileHandle c : Utilities.listFiles(d)) {
                    picList.add(c.path());
                }
                //loads files and puts it into a primitive array because animation only take primitive arrays
                Texture[] frames = new Texture[picList.size()];
                for (int i = 0; i < picList.size(); i++) {
                    frames[i] = manager.get(picList.get(i));

                }
                //puts the value with the keys
                //frames is the speed every frame takes
                put(state, direction, new Animation<>(aniSpeed, frames));
            }
        }
    }

    //accesses hashmap for the necessary value without being verbose
    public Animation<Texture> get(States state, Directions direction) {
        return container.get(state).get(direction);

    }

    //puts value in hashmap
    private void put(States state, Directions direction, Animation<Texture> sprites) {
        //if the state has not been initialized it is initialized
        if (!container.containsKey(state)) {
            container.put(state, new HashMap<>());
        }
        container.get(state).put(direction, sprites);
    }
}
