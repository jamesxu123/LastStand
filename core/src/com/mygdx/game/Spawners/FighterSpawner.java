package com.mygdx.game.Spawners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Directions;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.EntityUtilities.FighterData;
import com.mygdx.game.Player;
import com.mygdx.game.UIs.GameUI;
import com.mygdx.game.Utilities;

import java.util.ArrayList;

public class FighterSpawner extends Spawner {
    private final Player player;
    private final int spawnX;
    private final int spawnY;
    private final Directions spawnDir;
    private Float spawnInterval;
    private float spawnIntervalRange;
    private int numEnemies;
    private final ArrayList<FighterData> fighterDatas;
    private GameUI gameUI;
    private int wave = 0;
    private static boolean constantSpawn = false;


    public FighterSpawner(int x, int y, String direction, ArrayList<FighterData> fighterDatas, Player player) {
        this.player = player;
        this.fighterDatas = fighterDatas;
        spawnX = x;
        spawnY = y;
        spawnDir = Directions.valueOf(direction);
    }

    public static void setConstantSpawn(boolean cs) {
        constantSpawn = cs;

    }


    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    @Override
    public int getWave() {
        return wave;
    }

    private void setNextWave() {
        numEnemies = (int) (0.75 * wave * wave * wave + wave + 10); //Polly no meal to scale difficulty by levels
        spawnIntervalRange = ((25.f + wave) + 10) / numEnemies; //Scale spawn time gap for increased difficulty
    }


    @Override
    public void run(float delta) {

        if (getSpawning()) {

            if (spawnInterval == null) {
                spawnInterval = Utilities.rand.nextFloat() * spawnIntervalRange; //Add randomness

            }


            if (getTotalTime() > spawnInterval) { //Only spawn if spawnInterval has passed
                int nextSpawn = Utilities.rand.nextInt(fighterDatas.size()); //Randomize which Fighter to spawn
                int r = Utilities.rand.nextInt(20); //+- location to avoid all Fighters spawning on top of each other

                int deviation = (Utilities.rand.nextBoolean()) ? r : -r; //Chance to deviate in other direction
                if (spawnDir == Directions.UP || spawnDir == Directions.DOWN) {
                    spawn(spawnX + deviation, spawnY, nextSpawn, spawnDir);

                } else {
                    spawn(spawnX, spawnY + deviation, nextSpawn, spawnDir);
                }
                //adds a listener to the fighter so that you can click them
                Fighter latestFighter = (Fighter) getGroup().getChildren().get(getGroup().getChildren().size - 1);
                latestFighter.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //if the player has gods finger they are able to kill enemy just by touching it
                        //this can be abused by pausing the game and killing every enemy on the screen lol
                        if (Player.isGodFinger()) {

                            latestFighter.damage(999999999);
                        }
                        gameUI.changeInfo(latestFighter);
                        super.clicked(event, x, y);
                    }
                });
                spawnInterval = null;
                numEnemies -= 1; //Record that one enemy has been spawned
                setTotalTime(0);
            }
            super.run(delta);
            if (numEnemies < 1) { //If all enemies for wave has been spawned, end wave
                if (!constantSpawn) { //constant spawning of enemies
                    setSpawning(false);
                }

                wave += 1;
                setNextWave();
            }
        }

    }
    private void spawn(int x, int y, int index, Directions directions) {
        //Add spawned fighter to the current group
        getGroup().addActor(new Fighter(fighterDatas.get(index), x, y, directions, player));
    }
}
