package com.levicore.silvermoon.presets.monsters;

import com.levicore.silvermoon.entities.battle.KadukiBattler;

/**
 * Created by user on 2/13/2015.
 */
public class Monsters_A {

    public static KadukiBattler createDummy() {
        KadukiBattler kadukiBattler = new KadukiBattler("$Actor63", 0, 0, 32, 32);

        kadukiBattler.name = "haha";

        kadukiBattler.maxHP = 1;
        kadukiBattler.curHP = 1;

        kadukiBattler.maxMP = 1;
        kadukiBattler.curMP = 1;

        kadukiBattler.maxMP = 1;
        kadukiBattler.curTP = 1;
        
        kadukiBattler.speed = 1;

        return kadukiBattler;
    }

}
