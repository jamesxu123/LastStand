package com.mygdx.game.Spawners;

import com.mygdx.game.Directions;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.EntityUtilities.FighterData;
import com.mygdx.game.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    private Iterator<File> waves;
    private ArrayList<FighterData> fighterDatas;


    public FighterSpawner(int x, int y, String direction, String roundDir, ArrayList<FighterData> fighterDatas) {


        super();
        this.fighterDatas = fighterDatas;
        waves = Arrays.asList(Utilities.getListFiles(new File(roundDir))).iterator();

        spawnX = x;
        spawnY = y;
        spawnDir = Directions.valueOf(direction);
        switchWave();


    }

    private void switchWave() {
        try {
            File f = waves.next();
            System.out.println(f);
            waveScanner = new Scanner(new BufferedReader(new FileReader(f)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
                int r = Utilities.rand.nextInt(15);
                int deviation = (Utilities.rand.nextBoolean()) ? r : -r;
                spawn(spawnX + deviation, spawnY, nextSpawn);
                Fighter f = (Fighter) getGroup().getChildren().get(getGroup().getChildren().size - 1);
                f.setDirection(spawnDir);
                nextSpawn = null;
                setTotalTime(0);
            }
            super.run(delta);


        }

    }

    @Override
    public void spawn(int x, int y, int index) {
        getGroup().addActor(new Fighter(fighterDatas.get(index), x, y));
    }
}
