package com.mygdx.game.Abstractions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.Directions;
import com.mygdx.game.States;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FighterSpawner extends Spawner {
    public FighterSpawner(String spritesPath, Class actorClass) {
        super(actorClass);
        ArrayList<HashMap<States, HashMap<Directions, Animation<Texture>>>> spriteAnimations = new ArrayList<>();
        for (File p : new File(spritesPath).listFiles((dir, name) -> !name.equals(".DS_Store"))) {
            spriteAnimations.add(getAnimations(p));
        }
        setAnimations(spriteAnimations);

    }

    public HashMap<States, HashMap<Directions, Animation<Texture>>> getAnimations(File file) {
        HashMap<States, HashMap<Directions, Animation<Texture>>> sprites = new HashMap<>();
        File[] stateFiles = file.listFiles();
        for (File f : stateFiles) {

            if (f.getName().equals(".DS_Store")) continue;
            States state = States.valueOf(f.getName());
            File[] dirFiles = f.listFiles();

            sprites.put(state, new HashMap<>());
            for (File d : dirFiles) {

                if (d.getName().equals(".DS_Store")) continue;
                Directions direction = Directions.valueOf(d.getName());
                ArrayList<String> picList = new ArrayList<>();
                for (File p : d.listFiles()) {
                    if (p.getName().equals(".DS_Store")) continue;
                    picList.add(p.getPath());
                }
                Collections.sort(picList);
                Texture[] frames = new Texture[picList.size()];
                for (int i = 0; i < picList.size(); i++) {
                    frames[i] = new Texture(picList.get(i));
                }
                sprites.get(state).put(direction, new Animation<>(0.04f, frames));
            }
        }
        return sprites;
    }

    @Override
    public void run(float delta) {
        if (getSpawning()) {
            super.run(delta);
            spawn(200, 200);

        }

    }
}
