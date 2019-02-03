package com.mygdx.game.Spawners;

import com.mygdx.game.Entities.MoneyTower;
import com.mygdx.game.Entities.Tower;
import com.mygdx.game.EntityUtilities.TowerData;

import java.util.ArrayList;

public class TowerSpawner extends Spawner {
    private ArrayList<TowerData> towerDatas;

    public TowerSpawner(ArrayList<TowerData> towerDatas) {
        super();
        this.towerDatas = towerDatas;


    }

    @Override
    public void run(float delta) {
        super.run(delta);

    }

    @Override
    public void spawn(int x, int y, int index) {
        if (towerDatas.get(index).towerType == MoneyTower.class) {
            //getGroup().addActor(new MoneyTower());
        } else {
            getGroup().addActor(new Tower(x, y, towerDatas.get(index)));

        }


    }
}
