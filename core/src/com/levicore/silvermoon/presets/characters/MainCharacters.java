package com.levicore.silvermoon.presets.characters;

import com.levicore.silvermoon.*;
import com.levicore.silvermoon.Character;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.presets.mapEntities.MainCharacterMapEntities;
import com.levicore.silvermoon.presets.monsters.Monsters_A;

/**
 * Created by user on 2/13/2015.
 */
public class MainCharacters {

    public static Character VINCE() {
       return new Character(MainCharacterMapEntities.VINCE(), Monsters_A.createDummy(), new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Levi");
    }

}
