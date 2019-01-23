package com.mygdx.game.Abstractions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.Directions;
import com.mygdx.game.Entities.Fighter;
import com.mygdx.game.States;
import com.mygdx.game.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class FighterSpawner extends Spawner {
    private int spawnX;
    private int spawnY;
    private Directions spawnDir;
    private Scanner waveScanner;
    private float spawnInterval;
    private Integer nextSpawn;
    private Iterator<File> waves;

    public FighterSpawner(String spritesPath, Class actorClass, int x, int y, String direction, String roundDir) {



        super(actorClass);
        waves = Arrays.asList(new File(roundDir).listFiles((d, name) -> !name.equals(".DS_Store"))).iterator();

        spawnX = x;
        spawnY = y;
        spawnDir = Directions.valueOf(direction);
        ArrayList<HashMap<States, HashMap<Directions, Animation<Texture>>>> spriteAnimations = new ArrayList<>();
        for (File p : new File(spritesPath).listFiles((d, name) -> !name.equals(".DS_Store"))) {
            spriteAnimations.add(getAnimations(p));
        }
        setAnimations(spriteAnimations);
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

    private HashMap<States, HashMap<Directions, Animation<Texture>>> getAnimations(File file) {
        HashMap<States, HashMap<Directions, Animation<Texture>>> sprites = new HashMap<>();
        File[] stateFiles = file.listFiles();
        for (File f : stateFiles) {

            if (f.getName().equals(".DS_Store")) continue;
            States state = States.valueOf(f.getName());
            File[] dirFiles = f.listFiles();

            sprites.put(state, new HashMap<>());
            for (File d : dirFiles) {

                if (d.getName().equals(".DS_Store")) continue;
                Directions direction = Directions.valueOf(d.getName());
                ArrayList<String> picList = new ArrayList<>();
                for (File p : d.listFiles()) {
                    if (p.getName().equals(".DS_Store")) continue;
                    picList.add(p.getPath());
                }
                Collections.sort(picList);
                Texture[] frames = new Texture[picList.size()];
                for (int i = 0; i < picList.size(); i++) {
                    frames[i] = new Texture(picList.get(i));

                }
                sprites.get(state).put(direction, new Animation<>(0.04f, frames));
            }
        }
        return sprites;
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
}
