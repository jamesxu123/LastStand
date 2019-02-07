package com.mygdx.game.Spawners;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Directions;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.EntityUtilities.FighterData;
import com.mygdx.game.UIs.GameUI;
import com.mygdx.game.Utilities;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class FighterSpawner extends Spawner {
    private int spawnX;
    private int spawnY;
    private Directions spawnDir;
    private Scanner waveScanner;
    private float spawnInterval;
    private Integer nextSpawn;
    private Iterator<FileHandle> waves;
    private ArrayList<FighterData> fighterDatas;
    private GameUI gameUI;
    private Fighter latestFighter;


    public FighterSpawner(int x, int y, String direction, String roundDir, ArrayList<FighterData> fighterDatas) {
        this.fighterDatas = fighterDatas;
        waves = Arrays.asList(Utilities.listFiles(new FileHandle(roundDir))).iterator();
        spawnX = x;
        spawnY = y;
        spawnDir = Directions.valueOf(direction);
        switchWave();


    }

    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    private void switchWave() {

        FileHandle f = waves.next();
        waveScanner = new Scanner(new BufferedReader(f.reader()));

    }




    @Override
    public void run(float delta) {

        if (getSpawning()) {

            if (nextSpawn == null) {
                if (waveScanner.hasNext()) {
                    String[] wave = waveScanner.nextLine().split(" ");
                    spawnInterval = Float.parseFloat(wave[0]);
                    nextSpawn = Integer.parseInt(wave[1]);
                } else if (waves.hasNext()) {

                    switchWave();
                    setSpawning(false);
                    return;
                } else {
                    setSpawning(false);
                    return;

                }


            }
            if (getTotalTime() > spawnInterval) {
                int r = Utilities.rand.nextInt(20);

                int deviation = (Utilities.rand.nextBoolean()) ? r : -r;
                if (spawnDir == Directions.UP || spawnDir == Directions.DOWN) {
                    spawn(spawnX + deviation, spawnY, nextSpawn, spawnDir);

                } else {
                    spawn(spawnX, spawnY + deviation, nextSpawn, spawnDir);
                }

                latestFighter = (Fighter) getGroup().getChildren().get(getGroup().getChildren().size - 1);
                latestFighter.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        gameUI.changeInfo(latestFighter);


                        super.clicked(event, x, y);
                    }
                });
                nextSpawn = null;
                setTotalTime(0);
            }
            super.run(delta);


        }

    }



    public void spawn(int x, int y, int index, Directions directions) {
        getGroup().addActor(new Fighter(fighterDatas.get(index), x, y, directions));
    }
}
