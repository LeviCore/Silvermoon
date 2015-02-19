package com.levicore.silvermoon;

import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.MapEntity;
import com.levicore.silvermoon.entities.battle.BattleEntity;

/**
 * Created by Leonard on 1/17/2015.
 */
public class Character {

    // TODO equipments
    // TODO LEVELS
    private MapEntity mapEntity;
    private BattleEntity battleEntity;
    private Entity characterFace;

    public String name;

    public Character(MapEntity mapEntity, BattleEntity battleEntity, Entity characterFace, String name) {
        this.mapEntity = mapEntity;
        this.battleEntity = battleEntity;
        this.characterFace = characterFace;

        this.name = name;
    }

    public Character(MapEntity mapEntity, BattleEntity battleEntity, Entity characterFace, int level, String name) {
        this.mapEntity = mapEntity;
        this.battleEntity = battleEntity;
        this.characterFace = characterFace;

        this.name = name;
    }

    /**
     * Getters and setters
     */
    public void setName(String name) {
        this.name = name;
    }

    public MapEntity getMapEntity() {
        return mapEntity;
    }

    public void setMapEntity(MapEntity mapEntity) {
        this.mapEntity = mapEntity;
    }

    public BattleEntity getBattleEntity() {
        return battleEntity;
    }

    public void setBattleEntity(BattleEntity battleEntity) {
        this.battleEntity = battleEntity;
    }

    public Entity getCharacterFace() {
        return characterFace;
    }

    public void setCharacterFace(Entity characterFace) {
        this.characterFace = characterFace;
    }

    public String getName() {
        return name;
    }
}
