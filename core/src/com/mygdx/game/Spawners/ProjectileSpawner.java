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
            t.setLastFired(t.getLastFired() + delta); //Keep track of time since last fire
            if (t.getLastFired() > t.data.coolDown) { //If cooled down, fire
                t.setLastFired(0); //Reset cool down variable
                //money will only be generated if the fighter spawner is currently spawning
                if (t.data.projectileData.type == MoneyProjectile.class && getSpawning()) {
                    //If it's a coin projectile, send it to a random position on screen
                    spawn(Utilities.getPoint(t), new Point(Utilities.rand.nextInt(screenW), Utilities.rand.nextInt(screenH)), t);
                }
                //only sends out projectile when there is an enemy in the radius
                if (!t.getInRadius().isEmpty()) {
                    Fighter fighter = t.getClosest();
                    for (int i = 0; i < t.getNumProjectiles(); i++) {
                        Point p = Utilities.getPoint(t);
                        p.translate(t.getOffsetX(), t.getOffsetY()); //Move the projectile to spawn location
                        spawn(p, fighter, t);

                    }

                }
            }
        }
        super.run(delta);
    }

    private void spawn(Point start, Fighter target, Tower t) {
        if (t.data.projectileData.type == DmgProjectile.class) { //Make sure type is correct
            getGroup().addActor(new DmgProjectile(t.data.projectileData, start, target, t)); //Add it to group as an Actor
        }
    }

    private void spawn(Point start, Point end, Tower t) {
         if (t.data.projectileData.type == MoneyProjectile.class) { //Make sure type is correct
            getGroup().addActor(new MoneyProjectile(t.data.projectileData, start, end, t, player)); //Add it to group as an Actor
        }
    }
}
