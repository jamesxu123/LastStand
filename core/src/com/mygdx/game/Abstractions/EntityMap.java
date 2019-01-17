package com.mygdx.game.Abstractions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.Projectile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EntityMap extends Actor {
    public ArrayList<Actor>[][] map;
    private Queue<Action> actionQueue;
    ExecutorService executorService = Executors.newFixedThreadPool(16);

    public EntityMap() {
        this.map = new ArrayList[10][10];
        this.actionQueue = new LinkedList<>();
    }

    @Override
    public void act(float delta) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j].size() > 0) {
                    final ArrayList<Actor> actors = map[i][j];
                    executorService.submit(() -> {
                        ArrayList<Fighter> f = new ArrayList<>();
                        for (Actor actor : actors) {
                            if (actor.getClass() == Fighter.class) {
                                f.add((Fighter) actor);
                            }
                        }
                        ArrayList<Projectile> p = new ArrayList<>();
                        for (Actor actor : actors) {
                            if (actor.getClass() == Projectile.class) {
                                p.add((Projectile) actor);
                            }
                        }
                        for (Projectile projectile : p) {
                            double minDist = (double) Integer.MAX_VALUE;
                            Fighter minDistFighter;
                            for (Fighter fighter : f) {
                                double x1 = projectile.getX();
                                double y1 = projectile.getY();
                                double x2 = fighter.getX();
                                double y2 = fighter.getY();
                                double distance = Math.pow(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2), 0.5);
                                minDist = distance < minDist ? minDist : distance;
                                minDistFighter = distance < minDist ? fighter : null;
                            }
                        }
                    });
                }
            }
        }
        super.act(delta);
    }
}
