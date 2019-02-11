package com.mygdx.game.Abstractions;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.game.Entities.DmgProjectile;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.Player;
import com.mygdx.game.Utilities;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

/*
Class that handles collision for all entities
 */

public class EntityMap {
    public static final int mapArrW = 32;
    public static final int mapArrH = 24;
    public ArrayList<ArrayList<ArrayList<Fighter>>> map;
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
        //Method that gets all enemies within the range of the attacking tower that are alive
        return getCellsInArea(t.getRadius()).stream().filter(Fighter::isAlive).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Fighter> getInRadius(Circle c) {
        //Method that gets all enemies within the range of a radius that are alive
        return getCellsInArea(c).stream().filter(Fighter::isAlive).collect(Collectors.toCollection(ArrayList::new));
    }


    public ArrayList<Fighter> getCellsInArea(Circle c) {
        ArrayList<Fighter> cells = new ArrayList<>();
        for (int i = -convertMapY(c.radius); i <= convertMapY(c.radius); i++) { //Loop between the y boundaries
            for (int j = -convertMapX(c.radius); j <= convertMapX(c.radius); j++) { //Loop between the x boundaries
                if (c.contains(c.x + j * screenW / mapArrW, c.y + i * screenH / mapArrH)) {

                    if (mapContains(c.x + j * screenW / mapArrW, c.y + i * screenH / mapArrH)) {
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
        //Actor is put in its respective spot in the grid

        for (Actor f : actors) {
            if (mapContains(f.getX(), f.getY())) {
                map.get(convertMapY(f.getY())).get(convertMapX(f.getX())).add((Fighter) f); //Add fighter to grid each loop iteration


            } else {
                p.loseLife(); //When an enemy leaves the game map, it means player failed to kill it
                f.remove();
            }


        }

    }

    public boolean mapContains(float x, float y) {
        //Check if a coordinate is on the map
        return 0 <= convertMapX(x) && convertMapX(x) < mapArrW && 0 <= convertMapY(y) && convertMapY(y) < mapArrH;
    }


    public int convertMapX(float x) {
        //Convert x coordinate to 2D array index
        return Math.round(x / (screenW / mapArrW));

    }

    public int convertMapY(float y) {
        //Convert y coordinate to 2D array index
        return Math.round(y / (screenH / mapArrH));
    }

    public void resetMap() {
        //Clear each map cell
        for (ArrayList<ArrayList<Fighter>> row : map) {
            for (ArrayList<Fighter> col : row) {
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

                                if (Utilities.rand.nextInt((int) ((r.height / mapArrH) - row) * 10 + 1) <= 1) {
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

    //going to do a bfs instead so that the closest will be the closest
    public void collide(float delta, EntityGroup projectiles) {
        //Damage enemies that are within range of a projectile
        projectiles.getChildren().forEach(actor -> {
            if (actor.getClass() == DmgProjectile.class) { //Make sure it isn't a coin projectile
                getInRadius(((DmgProjectile) actor).range).forEach(((DmgProjectile) actor)::damage); //Damage each enemy
            }
        });
//        for (Actor a : projectiles.getChildren()) {
//            Projectile p = (Projectile) a;
//            for (Fighter f : getInRadius(p.range)) {
//                if (p.getClass() == DmgProjectile.class) {
//                    ((DmgProjectile) p).damage(f);
//                }
//                if (p.range.radius == 1) {
//                    break;
//                }
//            }
//        }
    }
}



