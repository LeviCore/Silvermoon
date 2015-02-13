package com.levicore.silvermoon;

import com.levicore.silvermoon.entities.Entity;
import com.levicore.silvermoon.entities.MapEntity;
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

        // Test =========================================================
        KadukiBattler s = new KadukiBattler("$Actor63", 23, 23, 32, 32);
        KadukiBattler k = new KadukiBattler("$Actor31", 23, 23, 32, 32);
        // ==============================================================


        MapEntity vince = new MapEntity("$Actor63", 32, 32, 100);

        party.add(
                new Character(vince, Monsters_A.createDummy(), new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Levi")

        );

        party.add(
                new Character(vince, Monsters_A.createDummy(), new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Levi")

        );

        party.add(
                new Character(vince, Monsters_A.createDummy(), new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Levi")

        );

        party.add(
                new Character(vince, Monsters_A.createDummy(), new Entity(Assets.FACES_ATLAS.findRegion("$Actor63_Normal")), "Levi")

        );

        for(Character character : party) {
            character.getBattleEntity().setX(-400);
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
