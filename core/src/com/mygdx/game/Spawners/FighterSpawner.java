package com.mygdx.game.Spawners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Directions;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.EntityUtilities.FighterData;
import com.mygdx.game.UIs.GameUI;
import com.mygdx.game.Utilities;

import java.util.ArrayList;

public class FighterSpawner extends Spawner {
    private int spawnX;
    private int spawnY;
    private Directions spawnDir;
    private Float spawnInterval;
    private float spawnIntervalRange;
    private int numEnemies;
    private ArrayList<FighterData> fighterDatas;
    private GameUI gameUI;
    private int wave = 0;
    private static boolean constantSpawn = false;


    public FighterSpawner(int x, int y, String direction, ArrayList<FighterData> fighterDatas) {
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

    public void setNextWave() {
        numEnemies = (int) (0.25 * wave * wave + wave + 10);
        spawnIntervalRange = ((25.f + wave) + 10) / numEnemies;
    }


    @Override
    public void run(float delta) {

        if (getSpawning()) {

            if (spawnInterval == null) {
                spawnInterval = Utilities.rand.nextFloat() * spawnIntervalRange;

            }


            if (getTotalTime() > spawnInterval) {
                int nextSpawn = Utilities.rand.nextInt(fighterDatas.size());
                int r = Utilities.rand.nextInt(20);

                int deviation = (Utilities.rand.nextBoolean()) ? r : -r;
                if (spawnDir == Directions.UP || spawnDir == Directions.DOWN) {
                    spawn(spawnX + deviation, spawnY, nextSpawn, spawnDir);

                } else {
                    spawn(spawnX, spawnY + deviation, nextSpawn, spawnDir);
                }

                Fighter latestFighter = (Fighter) getGroup().getChildren().get(getGroup().getChildren().size - 1);
                latestFighter.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        gameUI.changeInfo(latestFighter);


                        super.clicked(event, x, y);
                    }
                });
                spawnInterval = null;
                numEnemies -= 1;
                setTotalTime(0);
            }
            super.run(delta);
            if (numEnemies < 1) {
                if (!constantSpawn) {
                    setSpawning(false);
                }

                wave += 1;
                setNextWave();
            }
        }

    }
    public void spawn(int x, int y, int index, Directions directions) {
        getGroup().addActor(new Fighter(fighterDatas.get(index), x, y, directions));
    }
}
