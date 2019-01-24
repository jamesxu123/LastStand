package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;

public class AniContainer {
    private HashMap<States, HashMap<Directions, Animation<Texture>>> container;

    public AniContainer() {
        container = new HashMap<>();


    }

    public Animation<Texture> get(States state, Directions direction) {
        return container.get(state).get(direction);

    }

    public void put(States state, Directions direction, Animation<Texture> sprites) {
        if (!container.containsKey(state)) {
            container.put(state, new HashMap<>());
        }

        container.get(state).put(direction, sprites);
    }

}
