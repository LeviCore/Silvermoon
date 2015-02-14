package com.levicore.silvermoon;

import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.MapEntity;
import com.levicore.silvermoon.entities.battle.BattleEntity;
import com.levicore.silvermoon.entities.battle.KadukiBattler;
import com.levicore.silvermoon.inventory.ItemPage;
import com.levicore.silvermoon.inventory.ItemStorage;
import com.levicore.silvermoon.presets.monsters.Monsters_A;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 1/17/2015.
 */
public class Player {

    // TODO Inventory
    private Silvermoon game;

    /** List of all current party members **/
    private List<Character> party = new ArrayList<>();

    /** Item storage **/
    private ItemStorage itemStorage = new ItemStorage();

    public Player(Silvermoon game) {
        this.game = game;
        itemStorage.addItemPage(new ItemPage(9));

        MapEntity vince = new MapEntity("$Actor63", 32, 32, 100);

        party.add(new Character(vince, new KadukiBattler("$Actor33", 0, 0, 32, 32), new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Levi"));
        party.add(new Character(vince, new KadukiBattler("$Actor33", 0, 0, 32, 32), new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Levi"));
        party.add(new Character(vince, new KadukiBattler("$Actor33", 0, 0, 32, 32), new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Levi"));
        party.add(new Character(vince, new KadukiBattler("$Actor33", 0, 0, 32, 32), new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Levi"));


        for(Character character : party) {
            ((KadukiBattler) character.getBattleEntity()).flipBattler_void(true, false);
            character.getBattleEntity().name = "Dummy";

            character.getBattleEntity().setWeapon(new Entity("data/images/icons/sword_1.png"));

            character.getBattleEntity().maxHP = 1;
            character.getBattleEntity().curHP = 1;

            character.getBattleEntity().maxMP = 1;
            character.getBattleEntity().curMP = 1;

            character.getBattleEntity().maxMP = 1;
            character.getBattleEntity().curTP = 1;

            character.getBattleEntity().speed = 1;

            for (Character characterY : party) {
                characterY.getBattleEntity().setX(-400);
            }
        }

    }

    public void engageBattle() {

    }

    public List<Character> getParty() {
        return party;
    }

    public static Player createPlayer(Silvermoon game) {
        return new Player(game);
    }

}
