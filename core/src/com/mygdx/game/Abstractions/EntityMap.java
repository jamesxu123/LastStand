package com.mygdx.game.Abstractions;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.Projectile;
import com.mygdx.game.Player;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

public class EntityMap {
    public static final int mapArrW = 32;
    public static final int mapArrH = 32;
    public ArrayList<ArrayList<ArrayList<Actor>>> map;
    private ExecutorService executorService = Executors.newFixedThreadPool(16);

    public EntityMap() {
        map = new ArrayList<>();
        for (int row = 0; row < mapArrH; row++) {
            map.add(new ArrayList<>());
            for (int col = 0; col < mapArrW; col++) {
                map.get(row).add(new ArrayList<>());
            }
        }
    }


    public void constructMap(ArrayList<Actor> actors, Player player) {
        for (ArrayList<ArrayList<Actor>> row : map) {
            for (ArrayList<Actor> col : row) {
                col.clear();
            }
        }
        for (Actor a : actors) {
            if (!((int) a.getY() / (screenH / mapArrH) < mapArrH && a.getX() / (screenW / mapArrW) < mapArrW)) {
                a.remove();
                player.loseLife();
                continue;

            }
            map.get((int) a.getY() / (screenH / mapArrH)).get((int) a.getX() / (screenW / mapArrW)).add(a);
        }
        //System.out.println(map);
        //System.out.println(true);

    }

    public void switchDirection(Array<RectangleMapObject> nodes) {
        for (RectangleMapObject rectangleMapObject : nodes) {
            Rectangle r = rectangleMapObject.getRectangle();
            for (int row = 0; row < r.height / mapArrH; row++) {
                for (int col = 0; col < r.width / mapArrW; col++) {
                    for (Actor a : map.get((int) r.y / (screenH / mapArrH) + row).get((int) r.x / (screenW / mapArrW) + col)) {
                        if (a.getClass() == Fighter.class) {
                            Fighter f = (Fighter) a;
                            f.setDirection(rectangleMapObject.getProperties().get("Direction").toString());
                        }

                    }
                }
            }

        }

    }


    public void collide(float delta) {
        for (int row = 0; row < map.size(); row++) {
            for (int col = 0; col < map.get(0).size(); col++) {
                if (map.get(row).get(col).size() > 0) {
                    final ArrayList<Actor> actors = map.get(row).get(col);
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
                                //not good oop stuff
                                minDistFighter.damage((int) projectile.damage);
                            }
                        }
                    });
                }
            }
        }
    }
}
