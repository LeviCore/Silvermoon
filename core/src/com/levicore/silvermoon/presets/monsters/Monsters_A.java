package com.levicore.silvermoon.presets.monsters;

import com.levicore.silvermoon.battle.behaviors.Normal;
import com.levicore.silvermoon.entities.battle.KadukiBattler;

/**
 * Created by user on 2/13/2015.
 */
public class Monsters_A {

    public static KadukiBattler createDummy() {
        KadukiBattler kadukiBattler = new KadukiBattler("Dummy", "$Actor63", 32, 32, 1, 1, 1, 1, 1, 1, 1, 1, false, false);
        kadukiBattler.getBehaviors().add(new Normal(null, kadukiBattler));

        return kadukiBattler;
    }

}
