package com.mygdx.game.Abstractions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.Projectile;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EntityMap extends Actor {
    public ArrayList<Actor>[][] map;
    private ExecutorService executorService = Executors.newFixedThreadPool(16);

    public EntityMap() {
        map = new ArrayList[32][25];
    }

    @Override
    public void act(float delta) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j].size() > 0) {
                    final ArrayList<Actor> actors = map[i][j];
                    executorService.submit(() -> {
                        ArrayList<Fighter> f = new ArrayList<>();
                        ArrayList<Projectile> p = new ArrayList<>();

                        for (Actor actor : actors) {
                            if (actor.getClass() == Fighter.class) {
                                f.add((Fighter) actor);
                            } else if (actor.getClass() == Projectile.class) {
                                p.add((Projectile) actor);
                            }
                        }

                        if (f.size() > 1) {
                            for (Projectile projectile : p) {
                                double minDist = (double) Integer.MAX_VALUE;
                                Fighter minDistFighter = f.get(0);
                                for (Fighter fighter : f) {
                                    double x1 = projectile.getX();
                                    double y1 = projectile.getY();
                                    double x2 = fighter.getX();
                                    double y2 = fighter.getY();
                                    double distance = Math.hypot(x2 - x1, y2 - y1);
                                    minDist = distance < minDist ? minDist : distance;
                                    minDistFighter = distance < minDist ? fighter : null;
                                }
                                minDistFighter.damage((int) projectile.damage);
                            }
                        }
                    });
                }
            }
        }
        super.act(delta);
    }
}
