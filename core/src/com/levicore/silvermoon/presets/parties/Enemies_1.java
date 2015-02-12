package com.levicore.silvermoon.presets.parties;

import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.presets.monsters.Monsters_A;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/13/2015.
 */
public class Enemies_1 {
    public static List<BattleEntity> createDummies() {
        List<BattleEntity> battleEntities = new ArrayList<>();

        battleEntities.add(Monsters_A.createDummy());
        battleEntities.add(Monsters_A.createDummy());

        return battleEntities;
    }
}
