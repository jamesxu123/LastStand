package com.mygdx.game.Spawners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Abstractions.EntityGroup;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.Projectile;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.EntityUtilities.ProjectileData;
import com.mygdx.game.Utilities;

import java.awt.*;

public class ProjectileSpawner extends Spawner {

    private EntityGroup towers;


    public ProjectileSpawner(EntityGroup towers) {
        this.towers = towers;

        setSpawning(true);
    }

    @Override
    public void run(float delta) {

        if (getSpawning()) {
            for (Actor a : towers.getChildren()) {
                Tower t = (Tower) a;

                if (!t.getInRadius().isEmpty()) {


                    if (t.getLastFired() > t.data.coolDown) {
                        t.setLastFired(0);


                        Fighter fighter = t.getClosest();


                        spawn(t.data.projectileData, Utilities.getPoint(t), Utilities.getPoint(fighter), t);
                    }
                    t.setLastFired(t.getLastFired() + delta);
                }

            }


        }

        super.run(delta);
    }

    public void spawn(ProjectileData data, Point start, Point end, Tower t) {

        getGroup().addActor(new Projectile(data, start, end, t));

    }

    @Override
    public void spawn(int x, int y, int index) {
    }
}
