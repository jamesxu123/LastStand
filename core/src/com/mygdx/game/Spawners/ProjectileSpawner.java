package com.mygdx.game.Spawners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Abstractions.EntityGroup;
import com.mygdx.game.Entities.DmgProjectile;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.MoneyProjectile;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.Player;
import com.mygdx.game.Utilities;

import java.awt.*;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

/*
Class that spawns projectiles for a tower
 */

public class ProjectileSpawner extends Spawner {

    private EntityGroup towers;
    private final Player player;


    public ProjectileSpawner(EntityGroup towers, Player player) {
        this.towers = towers;
        this.player = player;
    }

    @Override
    public void run(float delta) {


        for (Actor a : towers.getChildren()) {
            Tower t = (Tower) a;
            t.setLastFired(t.getLastFired() + delta);
            if (t.getLastFired() > t.data.coolDown) {
                t.setLastFired(0);
                //money will only be generated if the fighter spawner is currently spawning
                if (t.data.projectileData.type == MoneyProjectile.class && getSpawning()) {

                    spawn(Utilities.getPoint(t), new Point(Utilities.rand.nextInt(screenW), Utilities.rand.nextInt(screenH)), t);
                }
                //only sends out projectile when there is an enemy in the radius
                if (!t.getInRadius().isEmpty()) {
                    Fighter fighter = t.getClosest();
                    for (int i = 0; i < t.getNumProjectiles(); i++) {
                        Point p = Utilities.getPoint(t);
                        p.translate(t.getOffsetX(), t.getOffsetY());
                        spawn(p, fighter, t);

                    }

                }
            }
        }
        super.run(delta);
    }

    private void spawn(Point start, Fighter target, Tower t) {
        if (t.data.projectileData.type == DmgProjectile.class) {
            getGroup().addActor(new DmgProjectile(t.data.projectileData, start, target, t));
        }
    }

    private void spawn(Point start, Point end, Tower t) {
         if (t.data.projectileData.type == MoneyProjectile.class) {
            getGroup().addActor(new MoneyProjectile(t.data.projectileData, start, end, t, player));
        }
    }
}
