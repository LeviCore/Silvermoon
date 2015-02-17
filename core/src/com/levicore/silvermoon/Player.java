package com.levicore.silvermoon;

import com.levicore.silvermoon.core.Equipment;
import com.levicore.silvermoon.inventory.ItemPage;
import com.levicore.silvermoon.inventory.ItemStorage;
import com.levicore.silvermoon.presets.characters.MainCharacters;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonard on 1/17/2015.
 */
public class Player {

    private Silvermoon game;

    /** List of all current party members **/
    private List<Character> party;

    /** Item storage **/
    private ItemStorage itemStorage;

    public Player(Silvermoon game) throws Exception {
        this.game = game;

        party = new ArrayList<>();

        itemStorage = new ItemStorage();
        itemStorage.addItemPage(new ItemPage(9));

        party.add(MainCharacters.VINCE());
        party.add(MainCharacters.RHEAJOY());
    }

    public List<Character> getParty() {
        return party;
    }

    public static Player createPlayer(Silvermoon game) throws Exception {
        return new Player(game);
    }

}
