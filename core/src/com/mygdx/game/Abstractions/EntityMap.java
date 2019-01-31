package com.mygdx.game.Abstractions;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.Projectile;
import com.mygdx.game.Entities.Tower;

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

    public ArrayList<Fighter> getInRadius(Tower t) {
        ArrayList<Fighter> fighters = new ArrayList<>();
        for (Actor actor : getCellsInArea(t.getRadius())) {
            fighters.add((Fighter) actor);
        }

        return fighters;
    }
    //public ArrayList<int[]> getCells(Rectangle r){

    //}
    public ArrayList<Actor> getCellsInArea(Circle c) {
        ArrayList<Actor> cells = new ArrayList<>();
        for (int i = (int) -c.radius / mapArrH; i < c.radius / mapArrH; i++) {
            for (int j = (int) -c.radius / mapArrW; j < c.radius / mapArrW; j++) {
                if (c.contains(c.x + i * mapArrH, c.y + j * mapArrW)) {
                    cells.addAll(map.get(i + convertMapY(c.y)).get(j + convertMapX(c.x)));
                }
            }
        }
        return cells;

    }

    public void constructMap(SnapshotArray<Actor> actors) {

        //actor is put in its respective spot in the grid

        for (Actor a : actors) {

            map.get(convertMapY(a.getY())).get(convertMapX(a.getX())).add(a);
        }

    }

    public int convertMapX(float x) {
        return (int) x / (screenW / mapArrW);

    }

    public int convertMapY(float y) {
        return (int) y / (screenH / mapArrH);
    }

    public void resetMap() {
        for (ArrayList<ArrayList<Actor>> row : map) {
            for (ArrayList<Actor> col : row) {
                col.clear();
            }
        }
    }

    public void switchDirection(Array<RectangleMapObject> nodes) {
        //loop through nodes and check in the array spots where the object is if there is anyone and switch their direction
        for (RectangleMapObject rectangleMapObject : nodes) {
            Rectangle r = rectangleMapObject.getRectangle();
            for (int row = 0; row < r.height / mapArrH; row++) {
                for (int col = 0; col < r.width / mapArrW; col++) {
                    for (Actor a : map.get(convertMapY(r.y) + row).get(convertMapX(r.x) + col)) {
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

                        if (f.size() >= 1) {
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
                                projectile.damage(minDistFighter);
                            }
                        }
                    });
                }
            }
        }
    }
}
