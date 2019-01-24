package com.mygdx.game.EntityUtilities;

import com.mygdx.game.Entities.Fighter;

import java.io.File;

public class Entity {
    //entity will also have other attributes such as name, icon, and description
    private AniContainer aniContainer;
    private Stats stats;

    public Entity(String statsPath, File spritesPath, Class entityType) {

        if (entityType == Fighter.class) {
            aniContainer = new FighterAnis(spritesPath);
            stats = new FighterStats(statsPath);
        }


    }

    public AniContainer getAniContainer() {
        return aniContainer;
    }

    public Stats getStats() {
        return stats;
    }
}
