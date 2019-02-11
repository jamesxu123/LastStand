package com.mygdx.game.Spawners;

import com.mygdx.game.Entities.Tower;
import com.mygdx.game.EntityUtilities.TowerData;

import java.util.ArrayList;

//little class so that the towers have a spawner which places towers in the entity group
public class TowerSpawner extends Spawner {
    private final ArrayList<TowerData> towerDatas;
    public TowerSpawner(ArrayList<TowerData> towerDatas) {
        this.towerDatas = towerDatas;
    }
    @Override
    public void run(float delta) {
        super.run(delta);
    }
    public void spawn(int x, int y, int index) {
            getGroup().addActor(new Tower(x, y, towerDatas.get(index)));
    }
}
