package com.mygdx.game.Abstractions;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.Projectile;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.Player;
import com.mygdx.game.Utilities;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

public class EntityMap {
    public static final int mapArrW = 36;
    public static final int mapArrH = 30;
    public ArrayList<ArrayList<ArrayList<Actor>>> map;
    private ExecutorService executorService = Executors.newFixedThreadPool(16);
    private ShapeRenderer shapeRenderer;

    public EntityMap(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
        map = new ArrayList<>();
        for (int row = 0; row <= mapArrH; row++) {
            map.add(new ArrayList<>());
            for (int col = 0; col <= mapArrW; col++) {
                map.get(row).add(new ArrayList<>());
            }
        }
    }

    public ArrayList<Fighter> getInRadius(Tower t) {
        return getInRadius(t.getRadius());
    }

    public ArrayList<Fighter> getInRadius(Circle radius) {
        ArrayList<Fighter> fighters = new ArrayList<>();
        for (Actor actor : getCellsInArea(radius)) {
            if (actor.getClass() == Fighter.class) {
                fighters.add((Fighter) actor);
            }
        }
        return fighters;
    }

    //public ArrayList<int[]> getCells(Rectangle r){

    //}
    public ArrayList<Actor> getCellsInArea(Circle c) {
        //shapeRenderer.setColor(1, 0, 1, 1);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        ArrayList<Actor> cells = new ArrayList<>();
        int range = convertMapY(c.radius);
        for (int i = -range; i < range; i++) {
            for (int j = -range; j < range; j++) {
                if (c.contains(c.x + j * screenW / mapArrW, c.y + i * screenH / mapArrH)) {
                    //shapeRenderer.rect(c.x + j * screenW / mapArrW, c.y + i * screenH / mapArrH, screenW / mapArrW, screenH / mapArrH);

                    if (Utilities.inScreen(c.x + j * screenW / mapArrW, c.y + i * screenH / mapArrH)) {
                        cells.addAll(map.get(i + convertMapY(c.y)).get(j + convertMapX(c.x)));

                    }

                }
            }
        }
        //shapeRenderer.end();
        //shapeRenderer.setColor(0, 0, 0, 1);
        return cells;

    }

    public void debug() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int row = 0; row <= mapArrH; row++) {

            for (int col = 0; col <= mapArrW; col++) {


                shapeRenderer.rect(col * (screenW / mapArrW), row * (screenH / mapArrH), screenW / mapArrW, screenH / mapArrH);

            }
        }
        shapeRenderer.end();
    }

    public void constructMap(SnapshotArray<Actor> actors, Player p) {

        //actor is put in its respective spot in the grid

        for (Actor a : actors) {
            if (0 <= convertMapX(a.getX()) && convertMapX(a.getX()) < mapArrW && 0 <= convertMapY(a.getY()) && convertMapY(a.getY()) < mapArrH) {
                map.get(convertMapY(a.getY())).get(convertMapX(a.getX())).add(a);

            } else if (a.getClass() == Fighter.class) {
                p.loseLife();
                a.remove();

            } else {
                a.remove();
            }


        }

    }





    public int convertMapX(float x) {
        return (int) Math.floor(x / (screenW / mapArrW));

    }

    public int convertMapY(float y) {
        return (int) Math.floor(y / (screenH / mapArrH));
    }

    public void resetMap() {
        for (ArrayList<ArrayList<Actor>> row : map) {
            for (ArrayList<Actor> col : row) {
                col.clear();
            }
        }
    }

    //need to add randomness when switching directions with random and need to adjust tiled map thing
    public void switchDirection(Array<RectangleMapObject> nodes) {
        //loop through nodes and check in the array spots where the object is if there is anyone and switch their direction
        for (RectangleMapObject rectangleMapObject : nodes) {
            Rectangle r = rectangleMapObject.getRectangle();
            for (int row = 0; row < r.height / mapArrH; row++) {
                for (int col = 0; col < r.width / mapArrW; col++) {
                    for (Actor a : map.get(convertMapY(r.y) + row).get(convertMapX(r.x) + col)) {

                        Fighter f = (Fighter) a;
                        switch (f.getDirections()) {

                            case UP:

                                if (Utilities.rand.nextInt((int) ((r.height / mapArrH) - row) * 25 + 1) <= 1) {
                                    f.setDirection(rectangleMapObject.getProperties().get("Direction").toString());

                                }
                                break;
                            case DOWN:
                                if (Utilities.rand.nextInt(row * 25 + 1) <= 1) {
                                    f.setDirection(rectangleMapObject.getProperties().get("Direction").toString());
                                }
                                break;
                            case RIGHT:
                                if (Utilities.rand.nextInt((int) ((r.width / mapArrW) - col) * 25 + 1) <= 1) {
                                    f.setDirection(rectangleMapObject.getProperties().get("Direction").toString());

                                }
                                break;
                            case LEFT:
                                if (Utilities.rand.nextInt(col * 25 + 1) <= 1) {
                                    f.setDirection(rectangleMapObject.getProperties().get("Direction").toString());

                                }
                                break;


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
                        ArrayList<Projectile> p = new ArrayList<>();
                        ArrayList<Fighter> f = new ArrayList<>();

                        for (Actor actor : actors) {
                            if (actor.getClass() == Projectile.class) {
                                p.add((Projectile) actor);
                            }
                        }

                        for (Projectile projectile : p) {
                            f.addAll(this.getInRadius(projectile.range));
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
