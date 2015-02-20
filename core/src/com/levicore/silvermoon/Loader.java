package com.levicore.silvermoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.levicore.silvermoon.entities.battle.LevelableBattler;
import com.levicore.silvermoon.presets.characters.MainCharacters;

/**
 * Created by user on 2/20/2015.
 */
public class Loader {

    public static Preferences PREFS = Gdx.app.getPreferences(".save");

    public static Json getJson() {
        Json json = new Json();
        json.setSerializer(Character.class, new Json.Serializer<Character>() {
            @Override
            public void write(Json json, Character object, Class knownType) {
                json.writeObjectStart();

                json.writeValue("name", object.getName());
                json.writeValue("graphic", object.getPresetChar());

                json.writeValue("x", object.getMapEntity().getX());
                json.writeValue("y", object.getMapEntity().getY());
                json.writeValue("speed", object.getMapEntity().getSpeed());

                json.writeValue("level", object.getBattleEntity().getLevel());
                if(object.getBattleEntity() instanceof LevelableBattler) {
                    json.writeValue("curExp", ((LevelableBattler)object.getBattleEntity()).getCurExp());
                    json.writeValue("toNextLevel", ((LevelableBattler)object.getBattleEntity()).getToNextLevel());
                    json.writeValue("expRate", ((LevelableBattler)object.getBattleEntity()).getExpRate());
                }

                json.writeValue("maxHP", object.getBattleEntity().getMaxHP());
                json.writeValue("curHP", object.getBattleEntity().getCurHP());

                json.writeValue("maxMP", object.getBattleEntity().getMaxMP());
                json.writeValue("curMP", object.getBattleEntity().getCurMP());

                json.writeValue("maxTP", object.getBattleEntity().getMaxTP());
                json.writeValue("curTP", object.getBattleEntity().getCurTP());

                json.writeValue("atk", object.getBattleEntity().getAtk());
                json.writeValue("def", object.getBattleEntity().getDef());

                json.writeValue("mAtk", object.getBattleEntity().getMAtk());
                json.writeValue("mDef", object.getBattleEntity().getMDef());

                json.writeValue("battleSpeed", object.getBattleEntity().getSpeed());

                json.writeObjectEnd();
            }

            @Override
            public Character read(Json json, JsonValue jsonData, Class type) {
                Character character = MainCharacters.loadMainCharacter(jsonData.getInt("graphic"));

                character.setName(jsonData.getString("name"));
                character.setPresetChar(jsonData.getInt("graphic"));

                character.getMapEntity().setPosition(jsonData.getFloat("x"), jsonData.getFloat("y"));
                character.getMapEntity().setSpeed(jsonData.getFloat("speed"));

                character.getBattleEntity().setLevel(jsonData.getInt("level"));
                if(character.getBattleEntity() instanceof LevelableBattler) {
                    ((LevelableBattler) character.getBattleEntity()).setCurExp(jsonData.getFloat("curExp"));
                    ((LevelableBattler)character.getBattleEntity()).setToNextLevel(jsonData.getFloat("toNextLevel"));
                    ((LevelableBattler)character.getBattleEntity()).setExpRate(jsonData.getFloat("expRate"));
                }

                character.getBattleEntity().setMaxHP(jsonData.getFloat("maxHP"));
                character.getBattleEntity().setCurHP(jsonData.getFloat("curHP"));

                character.getBattleEntity().setMaxMP(jsonData.getFloat("maxMP"));
                character.getBattleEntity().setCurMP(jsonData.getFloat("curMP"));

                character.getBattleEntity().setMaxTP(jsonData.getFloat("maxTP"));
                character.getBattleEntity().setCurTP(jsonData.getFloat("curTP"));

                character.getBattleEntity().setAtk(jsonData.getFloat("atk"));
                character.getBattleEntity().setDef(jsonData.getFloat("def"));

                character.getBattleEntity().setmAtk(jsonData.getFloat("mAtk"));
                character.getBattleEntity().setmDef(jsonData.getFloat("mDef"));

                character.getBattleEntity().setSpeed(jsonData.getFloat("battleSpeed"));

                return character;
            }
        });

        return json;
    }

    public static Player loadPlayer() {
        if(PREFS.contains("save")) {
            String text = PREFS.getString("save");
            Player player = getJson().fromJson(Player.class, text);

            return player;
        }
        return null;
    }

    public static void savePlayer(Player player) {
        PREFS.clear();
        PREFS.putInteger("gold", player.getGold());

        PREFS.putString("save", Loader.getJson().toJson(player));
        PREFS.flush();
    }

}
