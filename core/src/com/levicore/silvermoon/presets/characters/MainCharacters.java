package com.levicore.silvermoon.presets.characters;

import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.Character;
import com.levicore.silvermoon.core.Equipment;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.MapEntity;
import com.levicore.silvermoon.entities.battle.LevelableBattler;
import com.levicore.silvermoon.presets.equipments.Sword;

/**
 * Created by user on 2/13/2015.
 */
public class MainCharacters {

    public static Character VINCE() {
        LevelableBattler battleEntity = new LevelableBattler("$Actor63", 5, true, false, 32, 32, "Vince", 100, 15, 15, 10, 5, 3, 3, 5, 1, 222, 0.15f);
        MapEntity mapEntity = new MapEntity("$Actor63", 32, 32, 100);

        battleEntity.getEquipmentsAvailable().add(Equipment.EQUIPMENT_TYPE.WEAPON_SLOT);
        battleEntity.equip(new Sword(battleEntity));

        Character character = new Character(mapEntity, battleEntity, new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Leonard Vincent");
        character.setPresetChar(0);

        return character;
    }

    public static Character RHEAJOY() {
        LevelableBattler battleEntity = new LevelableBattler("$Actor28", 5, true, false, 32, 32, "Rheajoy", 60, 50, 15, 3, 3, 3, 3, 3, 1, 223, 0.15f);
        MapEntity mapEntity = new MapEntity("$Actor28", 32, 32, 100);

        Character character = new Character(mapEntity, battleEntity, new Entity(Assets.FACES_ATLAS.findRegion("$Actor28_Normal")), "Rheajoy Luna");
        character.setPresetChar(1);

        return character;
    }

    public static Character loadMainCharacter(int i) {
        switch (i) {
            case 0 :
                return VINCE();
            case 1 :
                return RHEAJOY();
        }

        return null;
    }

}
