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

    private int level;
    private float curExp = 0;
    private float toNextLevel = 100;
    private float expRate = 0.3f;

    private MapEntity mapEntity;
    private BattleEntity battleEntity;
    private Entity characterFace;

    public String name;

    public Character(MapEntity mapEntity, BattleEntity battleEntity, Entity characterFace, String name) {
        this.mapEntity = mapEntity;
        this.battleEntity = battleEntity;
        this.characterFace = characterFace;

        this.name = name;

        level = 1;
    }

    public Character(MapEntity mapEntity, BattleEntity battleEntity, Entity characterFace, int level, String name) {
        this.mapEntity = mapEntity;
        this.battleEntity = battleEntity;
        this.characterFace = characterFace;

        this.name = name;

        this.level = level;
    }

    /**
     * Level Control
     */
    public void addExp(float expToAdd) {
        curExp += expToAdd;

        if(curExp > toNextLevel) {
            level++;

            curExp = 0;
            toNextLevel += toNextLevel * expRate;
        }
    }

    /**
     * Getters and setters
     */
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
