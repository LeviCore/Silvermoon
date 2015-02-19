package com.levicore.silvermoon.presets.monsters;

import com.levicore.silvermoon.battle.behaviors.Normal;
import com.levicore.silvermoon.entities.battle.BattleEntity;

/**
 * Created by user on 2/13/2015.
 */
public class Monsters_A {

    public static BattleEntity createDummy() {
        BattleEntity battleEntity = new BattleEntity("$Actor63", 0, false, false, 32, 32, "Dummy", 1, 1, 1, 1, 1, 1, 1, 1);
        battleEntity.getBehaviors().add(new Normal(null, battleEntity));

        battleEntity.setExpReward(100);

        return battleEntity;
    }

}
