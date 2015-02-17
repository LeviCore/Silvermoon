package com.levicore.silvermoon.presets.characters;

import com.levicore.silvermoon.Assets;
import com.levicore.silvermoon.Character;
import com.levicore.silvermoon.core.Equipment;
import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.MapEntity;
import com.levicore.silvermoon.entities.battle.KadukiBattler;
import com.levicore.silvermoon.presets.equipments.Sword;

/**
 * Created by user on 2/13/2015.
 */
public class MainCharacters {

    public static Character VINCE() throws Exception {
        KadukiBattler battleEntity = new KadukiBattler("Vince", "$Actor63", 32, 32, 100, 15, 15, 10, 5, 3, 3, 5, true, false);
        MapEntity mapEntity = new MapEntity("$Actor63", 32, 32, 100);

        battleEntity.getEquipmentsAvailable().add(Equipment.EQUIPMENT_TYPE.WEAPON_SLOT);
        battleEntity.equip(new Sword(battleEntity));

        return new Character(mapEntity, battleEntity, new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Leonard Vincent");
    }

    public static Character RHEAJOY() {
        KadukiBattler battleEntity = new KadukiBattler("Rheajoy", "$Actor28", 32, 32, 60, 50, 15, 3, 3, 3, 3, 3, true, false);
        MapEntity mapEntity = new MapEntity("$Actor28", 32, 32, 100);

        return new Character(mapEntity, battleEntity, new Entity(Assets.FACES_ATLAS.findRegion("$Actor28_Normal")), "Rheajoy Luna");
    }

}
